package com.github.cla9.excel.reader.worker;


import com.github.cla9.excel.reader.entity.ExcelMetaModel;
import com.github.cla9.excel.reader.entity.ExceptionRow;
import com.github.cla9.excel.reader.exception.ExcelReaderFileException;
import com.github.cla9.excel.reader.entity.ExcelRowException;
import com.github.cla9.excel.reader.exception.SAXStopParseException;
import com.github.cla9.excel.reader.row.SAXRowHandler;
import com.github.cla9.excel.reader.sheet.MergedArea;
import com.github.cla9.excel.reader.sheet.SAXSheetExcelColumnHandler;
import com.github.cla9.excel.reader.sheet.SAXSheetHandler;
import com.github.cla9.excel.reader.sheet.SAXSheetMergeHeaderHandler;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.util.XMLHelper;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.model.StylesTable;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;

/**
 * The type Sax reader.
 *
 * @param <T> the type parameter
 */
public class SAXReader<T> extends ExcelReader<T> {

    /**
     * Instantiates a new Sax reader.
     *
     * @param tClass the t class
     */
    public SAXReader(Class<T> tClass) {
        super(tClass);
    }

    /**
     * Instantiates a new Sax reader.
     *
     * @param tClass the t class
     */
    public SAXReader(Class<T> tClass, String sheetName) {
        super(tClass, sheetName);
    }

    /**
     * Instantiates a new Sax reader.
     *
     * @param tClass         the t class
     * @param excelMetaModel the excel meta model
     */
    public SAXReader(Class<T> tClass, ExcelMetaModel excelMetaModel) {
        super(tClass, excelMetaModel);
    }

    /**
     * Instantiates a new Sax reader.
     *
     * @param tClass         the t class
     * @param excelMetaModel the excel meta model
     */
    public SAXReader(Class<T> tClass, String sheetName, ExcelMetaModel excelMetaModel) {
        super(tClass, sheetName, excelMetaModel);
    }

    @Override
    public ExcelResultSet<T> createResultSet(final MultipartFile multipartFile, final ExcelMetaModel excelMetaModel, boolean mergedHeaderCheck){
        ExcelResultSet<T> excelResultSet = new ExcelResultSet<>();
        final List<T> rows = new ArrayList<>();
        BiFunction<SAXRowHandler, SAXSheetHandler, Consumer<List<String>>> rowHandler = (saxRowHandler, saxSheetHandler)
                -> (Consumer<List<String>>) e -> {
            saxRowHandler.setRow(e);
            try {
                rows.add(injectValue(saxRowHandler,  saxSheetHandler.getHeaderNames()));
            } catch (ExcelRowException ex) {
                final ExceptionRow<T> exceptionRow = createExceptionRow(ex);
                excelResultSet.pushInvalidatedList(exceptionRow);
            }
        };
        doStart(multipartFile, excelMetaModel,rowHandler, mergedHeaderCheck);
        excelResultSet.pushAllValidatedList(rows);
        return excelResultSet;
    }


    @Override
    public void parseFile(final  MultipartFile multipartFile,
                          final ExcelMetaModel excelMetaModel,
                          Consumer<T> onSuccessConsumer,
                          Consumer<ExcelRowException> onErrorConsumer, boolean mergedHeaderCheck){
        BiFunction<SAXRowHandler, SAXSheetHandler, Consumer<List<String>>> rowHandler = (saxRowHandler, saxSheetHandler)
                -> (Consumer<List<String>>) e -> {
            saxRowHandler.setRow(e);
            try {
                T t = injectValue(saxRowHandler, saxSheetHandler.getHeaderNames());
                onSuccessConsumer.accept(t);
            } catch (ExcelRowException ex) {
                onErrorConsumer.accept(ex);
            }
        };
        doStart(multipartFile, excelMetaModel,rowHandler, mergedHeaderCheck);
    }


    private void doStart(final MultipartFile multipartFile,
                         final ExcelMetaModel excelMetaModel,
                         final BiFunction<SAXRowHandler, SAXSheetHandler, Consumer<List<String>>> rowConsumer, boolean mergedHeaderCheck) {

        try {
            OPCPackage pkg = OPCPackage.open(multipartFile.getInputStream());
            XSSFReader r = new XSSFReader(pkg);
            SharedStringsTable sst = r.getSharedStringsTable();
            StylesTable styles = r.getStylesTable();
            XMLReader parser = XMLHelper.newXMLReader();

            List<MergedArea> mergedAreas = null;
            if( (excelMetaModel.hasMergeHeader() && !excelMetaModel.hasAllColumnOrder()) || mergedHeaderCheck){
                final SAXSheetMergeHeaderHandler mergedContentHandler = new SAXSheetMergeHeaderHandler();
                parser.setContentHandler(mergedContentHandler);
                requestParse(r, parser);
                mergedAreas = mergedContentHandler.getMergedAreas();
            }

            SAXSheetHandler sheetHandler = getSheetHandler(excelMetaModel, mergedAreas);
            ContentHandler handler = new XSSFSheetXMLHandler(styles, sst, sheetHandler, false);
            SAXRowHandler rowHandler = new SAXRowHandler();

            sheetHandler.setRowGenerationSuccessCallback(rowConsumer.apply(rowHandler, sheetHandler));

            parser.setContentHandler(handler);
            requestParse(r, parser);
        }
        catch (SAXStopParseException ignored){}
        catch (Exception e) {
            throw new ExcelReaderFileException(e.getMessage(), e);
        }

    }

    private void requestParse(final XSSFReader r, final XMLReader parser) throws IOException, SAXException, InvalidFormatException {

        // if not given worksheet name.
        if (sheetName.isEmpty()) {
            try (InputStream sheet = r.getSheetsData().next()) {
                parser.parse(new InputSource(sheet));
            }
        }

        // if given worksheet name.
        if (sheetName.isPresent()) {
            try (InputStream sheet = r.getSheet(sheetName.get())) {
                parser.parse(new InputSource(sheet));
            }
        }
    }

    private SAXSheetHandler getSheetHandler(final ExcelMetaModel excelMetaModel, final List<MergedArea> mergedAreas){
        SAXSheetHandler sheetHandler;
        if(!excelMetaModel.isPartialParseOperation()){
            sheetHandler = new SAXSheetHandler(excelMetaModel, mergedAreas);
        }
        else{
            sheetHandler = new SAXSheetExcelColumnHandler(excelMetaModel, mergedAreas);
        }

        return sheetHandler;
    }

}

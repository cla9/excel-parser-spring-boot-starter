package com.github.cla9.excel.reader.worker;

import com.github.cla9.excel.reader.entity.ExcelMetaModel;
import com.github.cla9.excel.reader.entity.ExcelRowException;
import com.github.cla9.excel.reader.entity.ExceptionRow;
import com.github.cla9.excel.reader.exception.ExcelReaderFileException;
import com.github.cla9.excel.reader.row.RowHandler;
import com.github.cla9.excel.reader.row.WorkBookExcelColumnRowHandler;
import com.github.cla9.excel.reader.row.WorkBookRowHandler;
import com.github.cla9.excel.reader.sheet.WorkBookSheetHandler;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;


public class WorkBookReader<T> extends ExcelReader<T> {

    public WorkBookReader(Class<T> tClass) {
        super(tClass);
    }

    public WorkBookReader(Class<T> tClass, ExcelMetaModel excelMetaModel) {
        super(tClass, excelMetaModel);
    }

    @Override
    public ExcelResultSet<T> createResultSet(final MultipartFile multipartFile, final ExcelMetaModel excelMetaModel, boolean mergedHeaderCheck) {
        ExcelResultSet<T> excelResultSet = new ExcelResultSet<>();

        final List<T> rows = new ArrayList<>();

        BiFunction<RowHandler<Row>, WorkBookSheetHandler, Consumer<Row>> workBookRowHandler = (rowHandler, sheetHandler) ->
                (Consumer<Row>) e -> {
                    rowHandler.setRow(e);
                    try {
                        rows.add(injectValue(rowHandler, sheetHandler.getHeaderNames()));
                    } catch (ExcelRowException ex) {
                        final ExceptionRow<T> exceptionRow = createExceptionRow(ex);
                        excelResultSet.pushInvalidatedList(exceptionRow);
                    }
                };

        doStart(multipartFile, excelMetaModel,workBookRowHandler);

        excelResultSet.pushAllValidatedList(rows);
        return excelResultSet;
    }

    @Override
    public void parseFile(final MultipartFile multipartFile,
                          final ExcelMetaModel excelMetaModel,
                          final Consumer<T> onSuccessConsumer,
                          final Consumer<ExcelRowException> onErrorConsumer, boolean mergedHeaderCheck) {
        BiFunction<RowHandler<Row>, WorkBookSheetHandler, Consumer<Row>> workBookRowHandler = (rowHandler, sheetHandler) ->
                (Consumer<Row>) e -> {
                    rowHandler.setRow(e);
                    try {
                        T t = injectValue(rowHandler,  sheetHandler.getHeaderNames());
                        onSuccessConsumer.accept(t);
                    } catch (ExcelRowException ex) {
                        onErrorConsumer.accept(ex);
                    }
                };
        doStart(multipartFile, excelMetaModel,workBookRowHandler);
    }


    private void doStart(final MultipartFile multipartFile,
                         final ExcelMetaModel excelMetaModel,
                         final BiFunction<RowHandler<Row>, WorkBookSheetHandler, Consumer<Row>> rowConsumer) {

        Workbook workbook = getWorkbook(multipartFile);
        WorkBookSheetHandler sheetHandler = new WorkBookSheetHandler(workbook, excelMetaModel);
        RowHandler<Row> rowHandler = getRowHandler(sheetHandler, excelMetaModel);

        sheetHandler.setRowGenerationSuccessCallback(rowConsumer.apply(rowHandler, sheetHandler));
        sheetHandler.parse();
    }

    private Workbook getWorkbook(final MultipartFile multipartFile) {
        Workbook workbook;
        try {
            workbook = WorkbookFactory.create(multipartFile.getInputStream());
        } catch (IOException e) {
            throw new ExcelReaderFileException(e.getMessage(), e);
        }
        return workbook;
    }

    private RowHandler<Row> getRowHandler(final WorkBookSheetHandler sheetHandler, ExcelMetaModel excelMetaModel) {
        RowHandler<Row> rowHandler;
        if (!excelMetaModel.isPartialParseOperation()) {
            rowHandler = new WorkBookRowHandler();
        } else {
            rowHandler = new WorkBookExcelColumnRowHandler(sheetHandler.getOrder());
        }
        return rowHandler;
    }
}
package com.github.cla9.excel.reader.sheet;

import com.github.cla9.excel.reader.entity.ExcelMetaModel;
import com.github.cla9.excel.reader.exception.SAXStopParseException;
import com.github.cla9.excel.reader.row.Range;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.usermodel.XSSFComment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.IntStream;

import static com.github.cla9.excel.reader.util.ExcelConstant.UNDECIDED;

/**
 * The type Sax sheet handler.
 */
public class SAXSheetHandler extends AbstractSheetHandler implements SheetHandler, XSSFSheetXMLHandler.SheetContentsHandler {
    /**
     * The Current row.
     */
    protected int currentRow;
    /**
     * The Merged areas.
     */
    protected List<MergedArea> mergedAreas;
    /**
     * The Row.
     */
    protected List<String> row;
    /**
     * The Current col.
     */
    protected int currentCol = -1;
    /**
     * The Data range.
     */
    protected Range dataRange;
    /**
     * The Header range.
     */
    protected Range headerRange;

    /**
     * The Row generation success callback.
     */
    protected Consumer<List<String>> rowGenerationSuccessCallback;


    /**
     * Instantiates a new Sax sheet handler.
     *
     * @param excelMetaModel the excel meta model
     * @param mergedAreas    the merged areas
     */
    public SAXSheetHandler(ExcelMetaModel excelMetaModel, List<MergedArea> mergedAreas) {
        super(excelMetaModel);

        this.headerRange = excelMetaModel.getHeaderRange();
        this.dataRange = excelMetaModel.getDataRange();
        this.row = new ArrayList<>();
        this.currentRow = 0;
        this.mergedAreas = mergedAreas;
    }

    /**
     * Sets row generation success callback.
     *
     * @param rowGenerationSuccessCallback the row generation success callback
     */
    public void setRowGenerationSuccessCallback(Consumer<List<String>> rowGenerationSuccessCallback) {
        this.rowGenerationSuccessCallback = rowGenerationSuccessCallback;
    }

    @Override
    public void startRow(int rowNum) {
        currentCol = -1;
        currentRow = rowNum;
    }

    @Override
    public void endRow(int rowNum) {
        if (rowNum == headerRange.getEnd()) {
            createOrder();
        }
        else if(rowNum >= dataRange.getStart() && isPass()){
            createEmptyCell(headerNames.size() - row.size());
            rowGenerationSuccessCallback.accept(row);

            final int end = dataRange.getEnd();
            if(end != UNDECIDED && end == rowNum){
                throw new SAXStopParseException();
            }
        }

        row.clear();
    }


    @Override
    public void cell(String cellReference, String formattedValue, XSSFComment comment) {
        int currCol = (new CellReference(cellReference)).getCol();

        if (isHeaderRange()) {
            IntStream.rangeClosed( currentCol + 1, currCol)
                    .forEach(i -> setHeaderSet(formattedValue,i,i < currCol));
        }
        else{
            int emptyGenerationCount = currCol - currentCol - 1;
            createEmptyCell(emptyGenerationCount);
            row.add(formattedValue);
        }
        currentCol = currCol;
    }

    private boolean isHeaderRange() {
        return currentRow >= headerRange.getStart() && currentRow <= headerRange.getEnd();
    }

    private void setHeaderSet(String formattedValue, int currCol,  boolean hasEmptyCol) {
        if(mergedAreas != null){
            setMergedCell(formattedValue, currCol,hasEmptyCol);
        }
        else{
            createHeaderNames(currCol);
            headerNames.set(currCol,formattedValue);
        }
    }

    private void createHeaderNames(int currCol) {
        if (headerNames.size() <= currCol) {
            IntStream.range(0, currCol - headerNames.size() + 1)
                    .forEach(i -> headerNames.add(null));
        }
    }

    private void setMergedCell(String formattedValue, int currCol, boolean hasEmptyCol) {
        createHeaderNames(currCol);
        boolean isMerged = false;
        final String headerName = headerNames.get(currCol);
        for (final MergedArea mergedArea : mergedAreas) {
            if (mergedArea.isContain(currCol, currentRow)) {
                if (mergedArea.isStartPoint(currCol, currentRow)) {
                    if(hasEmptyCol){
                        formattedValue = null;
                    }
                    mergedArea.setHeader(formattedValue);
                    headerNames.set(currCol,!Objects.isNull(headerName) ? headerName + "." : formattedValue);
                }
                else if(mergedArea.getLeftTopY() == currentRow){
                    headerNames.set(currCol, !Objects.isNull(headerName) ? headerName + "." +  mergedArea.getHeader():  mergedArea.getHeader());
                }
                isMerged = true;
                break;
            }
        }
        if(!isMerged){
            if(hasEmptyCol){
                formattedValue = null;
            }
            headerNames.set(currCol, !Objects.isNull(headerName) ? headerName + "." + formattedValue: formattedValue);
        }
    }

    /**
     * Create empty cell.
     *
     * @param gap the gap
     */
    protected void createEmptyCell(int gap) {
        for (int i = 0; i < gap; i++) row.add(null);
    }

    @Override
    public List<String> getHeaderNames() {
        return this.headerNames;
    }

    @Override
    public int[] getOrder() {
        return order;
    }

    /**
     * Is pass boolean.
     *
     * @return the boolean
     */
    protected boolean isPass() {
        return row.stream().anyMatch(s -> !"".equals(s));
    }
}

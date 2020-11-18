package com.github.cla9.excel.reader.sheet;

import com.github.cla9.excel.reader.entity.ExcelMetaModel;
import com.github.cla9.excel.reader.exception.SAXStopParseException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.cla9.excel.reader.util.ExcelConstant.UNDECIDED;

/**
 * The type Sax sheet excel column handler.
 */
public final class SAXSheetExcelColumnHandler extends SAXSheetHandler {
    private int maxIndex;

    /**
     * Instantiates a new Sax sheet excel column handler.
     *
     * @param excelMetaModel the excel meta model
     * @param mergedAreas    the merged areas
     */
    public SAXSheetExcelColumnHandler(final ExcelMetaModel excelMetaModel, final List<MergedArea> mergedAreas) {
        super(excelMetaModel, mergedAreas);
    }

    @Override
    public void endRow(int rowNum) {
        if (rowNum == headerRange.getEnd()) {
            createOrder();
            validateOrder();
            maxIndex = headerNames.size();
            reOrderHeaderName();
            validateHeader();
        }
        else if(rowNum >= dataRange.getStart() && isPass()){
            final int end = dataRange.getEnd();
            if(end != UNDECIDED && end == rowNum){
                throw new SAXStopParseException();
            }
            createEmptyCell(calcGap());
            sortRow();
            rowGenerationSuccessCallback.accept(row);
        }
        row.clear();
    }

    private int calcGap() {
        return maxIndex - row.size() + 1;
    }

    private void sortRow() {
        row = Arrays.stream(order)
                .mapToObj(row::get)
                .collect(Collectors.toList());
    }

}

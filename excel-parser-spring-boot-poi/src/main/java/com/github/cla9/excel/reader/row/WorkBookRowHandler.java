package com.github.cla9.excel.reader.row;


import org.apache.poi.ss.formula.eval.ErrorEval;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;

import java.math.BigDecimal;
import java.util.Objects;


/**
 * The type Work book row handler.
 */
public class WorkBookRowHandler implements RowHandler<Row> {
    /**
     * The Row.
     */
    protected Row row;

    @Override
    public String getValue(int i) {
        return getValue(row.getCell(i));
    }

    /**
     * Gets value.
     *
     * @param cell the cell
     * @return the value
     */
    protected String getValue(final Cell cell) {
        if(Objects.isNull(cell)) return null;

        String value;
        switch (cell.getCellType()) {
            case STRING:
                value = cell.getRichStringCellValue().getString();
                break;
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    value = cell.getLocalDateTimeCellValue().toString();
                }
                else {
                    value = BigDecimal.valueOf(cell.getNumericCellValue()).toPlainString();
                }
                if (value.endsWith(".0"))
                    value = value.substring(0, value.length() - 2);
                break;
            case BOOLEAN:
                value = String.valueOf(cell.getBooleanCellValue());
                break;
            case FORMULA:
                value = String.valueOf(cell.getCellFormula());
                break;
            case ERROR:
                value = ErrorEval.getText(cell.getErrorCellValue());
                break;
            case BLANK:
            case _NONE:
            default:
                value = "";
        }
        return value;
    }

    /**
     * Gets row.
     *
     * @return the row
     */
    public Row getRow() {
        return this.row;
    }
    public void setRow(final Row row) {
        this.row = row;
    }
}

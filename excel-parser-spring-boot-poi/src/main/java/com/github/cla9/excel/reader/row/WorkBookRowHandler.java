package com.github.cla9.excel.reader.row;


import org.apache.poi.ss.formula.eval.ErrorEval;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;

import java.util.Objects;


public class WorkBookRowHandler implements RowHandler<Row> {
    protected Row row;

    @Override
    public String getValue(int i) {
        return getValue(row.getCell(i));
    }

    protected String getValue(final Cell cell) {
        if(Objects.isNull(cell)) return null;

        String value;
        switch (cell.getCellType()) {
            case STRING:
                value = cell.getRichStringCellValue().getString();
                break;
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell))
                    value = cell.getLocalDateTimeCellValue().toString();
                else
                    value = String.valueOf(cell.getNumericCellValue());
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

    public Row getRow() {
        return this.row;
    }
    public void setRow(final Row row) {
        this.row = row;
    }
}

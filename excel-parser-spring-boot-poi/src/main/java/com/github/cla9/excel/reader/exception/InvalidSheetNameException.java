package com.github.cla9.excel.reader.exception;

public class InvalidSheetNameException extends RuntimeException {
    public InvalidSheetNameException(String sheetName) {
        super(String.format("Sheet name was not found within excel file. sheet name : %s", sheetName));
    }
}

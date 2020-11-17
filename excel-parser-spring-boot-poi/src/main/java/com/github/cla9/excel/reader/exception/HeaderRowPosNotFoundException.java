package com.github.cla9.excel.reader.exception;

public class HeaderRowPosNotFoundException extends RuntimeException{
    public HeaderRowPosNotFoundException() { this("Either headerRowPos or headerRange field must be entered.");}
    public HeaderRowPosNotFoundException(String message) {
        super(message);
    }
    public HeaderRowPosNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

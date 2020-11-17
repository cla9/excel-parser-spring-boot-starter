package com.github.cla9.excel.reader.entity;

import java.util.List;


public class ErrorInfo {
    private String type;
    private String field;
    private String fieldHeader;
    private String value;
    private List<String> errorMessages;

    public ErrorInfo() {}
    public ErrorInfo(String type, String field, String fieldHeader, String value, List<String> errorMessages) {
        this.type = type;
        this.field = field;
        this.fieldHeader = fieldHeader;
        this.value = value;
        this.errorMessages = errorMessages;
    }

    public static ErrorInfo.ErrorInfoBuilder builder() {
        return new ErrorInfo.ErrorInfoBuilder();
    }

    public String getType() {
        return this.type;
    }
    public String getField() {
        return this.field;
    }
    public String getFieldHeader() {
        return this.fieldHeader;
    }
    public String getValue() {
        return this.value;
    }
    public List<String> getExceptionMessages() {
        return this.errorMessages;
    }

    public static class ErrorInfoBuilder {
        private String type;
        private String field;
        private String fieldHeader;
        private String value;
        private List<String> errorMessages;

        ErrorInfoBuilder() {}

        public ErrorInfo.ErrorInfoBuilder type(final String type) {
            this.type = type;
            return this;
        }

        public ErrorInfo.ErrorInfoBuilder field(final String field) {
            this.field = field;
            return this;
        }

        public ErrorInfo.ErrorInfoBuilder fieldHeader(final String fieldHeader) {
            this.fieldHeader = fieldHeader;
            return this;
        }

        public ErrorInfo.ErrorInfoBuilder value(final String value) {
            this.value = value;
            return this;
        }



        public ErrorInfo.ErrorInfoBuilder errorMessages(final List<String> errorMessages) {
            this.errorMessages = errorMessages;
            return this;
        }

        public ErrorInfo build() {
            return new ErrorInfo(this.type, this.field, this.fieldHeader, this.value,  this.errorMessages);
        }
    }
}
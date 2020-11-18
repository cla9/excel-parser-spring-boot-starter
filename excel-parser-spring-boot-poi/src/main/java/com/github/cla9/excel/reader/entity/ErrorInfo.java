package com.github.cla9.excel.reader.entity;

import java.util.List;


/**
 * The type Error info.
 */
public class ErrorInfo {
    private String type;
    private String field;
    private String fieldHeader;
    private String value;
    private List<String> errorMessages;

    /**
     * Instantiates a new Error info.
     */
    public ErrorInfo() {}

    /**
     * Instantiates a new Error info.
     *
     * @param type          the type
     * @param field         the field
     * @param fieldHeader   the field header
     * @param value         the value
     * @param errorMessages the error messages
     */
    public ErrorInfo(String type, String field, String fieldHeader, String value, List<String> errorMessages) {
        this.type = type;
        this.field = field;
        this.fieldHeader = fieldHeader;
        this.value = value;
        this.errorMessages = errorMessages;
    }

    /**
     * Builder error info . error info builder.
     *
     * @return the error info . error info builder
     */
    public static ErrorInfo.ErrorInfoBuilder builder() {
        return new ErrorInfo.ErrorInfoBuilder();
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public String getType() {
        return this.type;
    }

    /**
     * Gets field.
     *
     * @return the field
     */
    public String getField() {
        return this.field;
    }

    /**
     * Gets field header.
     *
     * @return the field header
     */
    public String getFieldHeader() {
        return this.fieldHeader;
    }

    /**
     * Gets value.
     *
     * @return the value
     */
    public String getValue() {
        return this.value;
    }

    /**
     * Gets exception messages.
     *
     * @return the exception messages
     */
    public List<String> getExceptionMessages() {
        return this.errorMessages;
    }

    /**
     * The type Error info builder.
     */
    public static class ErrorInfoBuilder {
        private String type;
        private String field;
        private String fieldHeader;
        private String value;
        private List<String> errorMessages;

        /**
         * Instantiates a new Error info builder.
         */
        ErrorInfoBuilder() {}

        /**
         * Type error info . error info builder.
         *
         * @param type the type
         * @return the error info . error info builder
         */
        public ErrorInfo.ErrorInfoBuilder type(final String type) {
            this.type = type;
            return this;
        }

        /**
         * Field error info . error info builder.
         *
         * @param field the field
         * @return the error info . error info builder
         */
        public ErrorInfo.ErrorInfoBuilder field(final String field) {
            this.field = field;
            return this;
        }

        /**
         * Field header error info . error info builder.
         *
         * @param fieldHeader the field header
         * @return the error info . error info builder
         */
        public ErrorInfo.ErrorInfoBuilder fieldHeader(final String fieldHeader) {
            this.fieldHeader = fieldHeader;
            return this;
        }

        /**
         * Value error info . error info builder.
         *
         * @param value the value
         * @return the error info . error info builder
         */
        public ErrorInfo.ErrorInfoBuilder value(final String value) {
            this.value = value;
            return this;
        }


        /**
         * Error messages error info . error info builder.
         *
         * @param errorMessages the error messages
         * @return the error info . error info builder
         */
        public ErrorInfo.ErrorInfoBuilder errorMessages(final List<String> errorMessages) {
            this.errorMessages = errorMessages;
            return this;
        }

        /**
         * Build error info.
         *
         * @return the error info
         */
        public ErrorInfo build() {
            return new ErrorInfo(this.type, this.field, this.fieldHeader, this.value,  this.errorMessages);
        }
    }
}
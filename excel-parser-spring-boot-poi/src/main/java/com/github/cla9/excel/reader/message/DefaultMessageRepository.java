package com.github.cla9.excel.reader.message;

public class DefaultMessageRepository implements ExcelMessageRepository {
    private DefaultMessageRepository() {}
    @Override
    public String getMessage(String code) { return code;}
}

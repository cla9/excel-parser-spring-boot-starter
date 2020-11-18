package com.github.cla9.excel.reader.message;

/**
 * The type Default message repository.
 */
public class DefaultMessageRepository implements ExcelMessageRepository {
    private DefaultMessageRepository() {}
    @Override
    public String getMessage(String code) { return code;}
}

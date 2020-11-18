package com.github.cla9.excel.reader.message;

/**
 * The interface Excel message repository.
 */
public interface ExcelMessageRepository {
    /**
     * Gets message.
     *
     * @param code the code
     * @return the message
     */
    String getMessage(String code);
}

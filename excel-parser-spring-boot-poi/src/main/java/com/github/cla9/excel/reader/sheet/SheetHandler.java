package com.github.cla9.excel.reader.sheet;

import java.util.List;

/**
 * The interface Sheet handler.
 */
public interface SheetHandler{
    /**
     * Gets header names.
     *
     * @return the header names
     */
    List<String> getHeaderNames();

    /**
     * Get order int [ ].
     *
     * @return the int [ ]
     */
    int[] getOrder();
}

package com.github.cla9.excel.reader.row;

/**
 * The type Work book excel column row handler.
 */
public class WorkBookExcelColumnRowHandler extends WorkBookRowHandler{
    private final int[] order;

    /**
     * Instantiates a new Work book excel column row handler.
     *
     * @param order the order
     */
    public WorkBookExcelColumnRowHandler(final int[] order) {
        this.order = order;
    }

    @Override
    public String getValue(int i) {
        return super.getValue(order[i]);
    }
}

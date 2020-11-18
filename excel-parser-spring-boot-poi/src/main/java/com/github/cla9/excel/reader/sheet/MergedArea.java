package com.github.cla9.excel.reader.sheet;

/**
 * The type Merged area.
 */
public class MergedArea {
    private final int leftTopX;
    private final int leftTopY;
    private final int rightBottomX;
    private final int rightBottomY;
    private String header;

    /**
     * Instantiates a new Merged area.
     *
     * @param leftTopX     the left top x
     * @param leftTopY     the left top y
     * @param rightBottomX the right bottom x
     * @param rightBottomY the right bottom y
     */
    public MergedArea(final int leftTopX, final int leftTopY, final int rightBottomX, final int rightBottomY) {
        this.leftTopX = leftTopX;
        this.leftTopY = leftTopY;
        this.rightBottomX = rightBottomX;
        this.rightBottomY = rightBottomY;
    }

    /**
     * Is contain boolean.
     *
     * @param x the x
     * @param y the y
     * @return the boolean
     */
    boolean isContain(int x, int y) {
        return this.leftTopX <= x && x <= this.rightBottomX && this.leftTopY <= y && y <= this.rightBottomY;
    }

    /**
     * Is start point boolean.
     *
     * @param x the x
     * @param y the y
     * @return the boolean
     */
    boolean isStartPoint(int x, int y) {
        return this.leftTopX == x && this.leftTopY == y;
    }

    /**
     * Gets left top x.
     *
     * @return the left top x
     */
    public int getLeftTopX() {
        return this.leftTopX;
    }

    /**
     * Gets left top y.
     *
     * @return the left top y
     */
    public int getLeftTopY() {
        return this.leftTopY;
    }

    /**
     * Gets right bottom x.
     *
     * @return the right bottom x
     */
    public int getRightBottomX() {
        return this.rightBottomX;
    }

    /**
     * Gets right bottom y.
     *
     * @return the right bottom y
     */
    public int getRightBottomY() {
        return this.rightBottomY;
    }

    /**
     * Gets header.
     *
     * @return the header
     */
    public String getHeader() {
        return this.header;
    }

    /**
     * Sets header.
     *
     * @param header the header
     */
    public void setHeader(final String header) {
        this.header = header;
    }
}
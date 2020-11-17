package com.github.cla9.excel.reader.sheet;

public class MergedArea {
    private final int leftTopX;
    private final int leftTopY;
    private final int rightBottomX;
    private final int rightBottomY;
    private String header;

    public MergedArea(final int leftTopX, final int leftTopY, final int rightBottomX, final int rightBottomY) {
        this.leftTopX = leftTopX;
        this.leftTopY = leftTopY;
        this.rightBottomX = rightBottomX;
        this.rightBottomY = rightBottomY;
    }

    boolean isContain(int x, int y) {
        return this.leftTopX <= x && x <= this.rightBottomX && this.leftTopY <= y && y <= this.rightBottomY;
    }
    boolean isStartPoint(int x, int y) {
        return this.leftTopX == x && this.leftTopY == y;
    }

    public int getLeftTopX() {
        return this.leftTopX;
    }
    public int getLeftTopY() {
        return this.leftTopY;
    }
    public int getRightBottomX() {
        return this.rightBottomX;
    }
    public int getRightBottomY() {
        return this.rightBottomY;
    }
    public String getHeader() {
        return this.header;
    }

    public void setHeader(final String header) {
        this.header = header;
    }
}
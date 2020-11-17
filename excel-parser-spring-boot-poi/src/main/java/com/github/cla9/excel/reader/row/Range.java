package com.github.cla9.excel.reader.row;

public class Range {
    private int start;
    private int end;

    public static Range of(final Range range) {
        return builder().start(range.start).end(range.end).build();
    }

    Range(final int start, final int end) {
        this.start = start;
        this.end = end;
    }

    public static Range.RangeBuilder builder() {
        return new Range.RangeBuilder();
    }
    public int getStart() {
        return this.start;
    }
    public int getEnd() {
        return this.end;
    }

    public static class RangeBuilder {
        private int start;
        private int end;

        public Range.RangeBuilder start(final int start) {
            this.start = start;
            return this;
        }
        public Range.RangeBuilder end(final int end) {
            this.end = end;
            return this;
        }
        public Range build() {
            return new Range(this.start, this.end);
        }
    }
}

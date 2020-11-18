package com.github.cla9.excel.reader.row;

/**
 * The type Range.
 */
public class Range {
    private int start;
    private int end;

    /**
     * Of range.
     *
     * @param range the range
     * @return the range
     */
    public static Range of(final Range range) {
        return builder().start(range.start).end(range.end).build();
    }

    /**
     * Instantiates a new Range.
     *
     * @param start the start
     * @param end   the end
     */
    Range(final int start, final int end) {
        this.start = start;
        this.end = end;
    }

    /**
     * Builder range . range builder.
     *
     * @return the range . range builder
     */
    public static Range.RangeBuilder builder() {
        return new Range.RangeBuilder();
    }

    /**
     * Gets start.
     *
     * @return the start
     */
    public int getStart() {
        return this.start;
    }

    /**
     * Gets end.
     *
     * @return the end
     */
    public int getEnd() {
        return this.end;
    }

    /**
     * The type Range builder.
     */
    public static class RangeBuilder {
        private int start;
        private int end;

        /**
         * Start range . range builder.
         *
         * @param start the start
         * @return the range . range builder
         */
        public Range.RangeBuilder start(final int start) {
            this.start = start;
            return this;
        }

        /**
         * End range . range builder.
         *
         * @param end the end
         * @return the range . range builder
         */
        public Range.RangeBuilder end(final int end) {
            this.end = end;
            return this;
        }

        /**
         * Build range.
         *
         * @return the range
         */
        public Range build() {
            return new Range(this.start, this.end);
        }
    }
}

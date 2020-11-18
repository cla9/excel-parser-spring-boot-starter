package com.github.cla9.excel.reader.entity;

import com.github.cla9.excel.reader.row.Range;
import com.github.cla9.excel.reader.worker.EntityInstantiatorSource;

import java.util.List;

/**
 * The type Excel meta model.
 */
public class ExcelMetaModel {
    private final List<String> headers;
    private final int[] order;
    private final Range headerRange;
    private final Range dataRange;
    private final boolean isPartialParseOperation;
    private final boolean mergedHeader;
    private final boolean hasOrder;
    private final boolean allColumnOrder;
    private final EntityInstantiatorSource instantiatorSource;

    /**
     * Instantiates a new Excel meta model.
     *
     * @param headers                 the headers
     * @param headerRange             the header range
     * @param dataRange               the data range
     * @param isPartialParseOperation the is partial parse operation
     * @param mergedHeader            the merged header
     * @param hasOrder                the has order
     * @param allColumnOrder          the all column order
     * @param order                   the order
     * @param instantiatorSource      the instantiator source
     */
    public ExcelMetaModel(List<String> headers, Range headerRange, Range dataRange, boolean isPartialParseOperation, boolean mergedHeader, boolean hasOrder, boolean allColumnOrder, int[] order, EntityInstantiatorSource instantiatorSource) {
        this.headers = headers;
        this.headerRange = headerRange;
        this.dataRange = dataRange;
        this.isPartialParseOperation = isPartialParseOperation;
        this.mergedHeader = mergedHeader;
        this.hasOrder = hasOrder;
        this.allColumnOrder = allColumnOrder;
        this.order = order;
        this.instantiatorSource = instantiatorSource;
    }

    /**
     * Has merge header boolean.
     *
     * @return the boolean
     */
    public boolean hasMergeHeader() {
        return this.mergedHeader;
    }

    /**
     * Has all column order boolean.
     *
     * @return the boolean
     */
    public boolean hasAllColumnOrder() {
        return this.allColumnOrder;
    }

    /**
     * Has order boolean.
     *
     * @return the boolean
     */
    public boolean hasOrder() {
        return this.hasOrder;
    }

    /**
     * Builder excel meta model . excel meta model builder.
     *
     * @return the excel meta model . excel meta model builder
     */
    public static ExcelMetaModel.ExcelMetaModelBuilder builder() {
        return new ExcelMetaModel.ExcelMetaModelBuilder();
    }


    /**
     * Gets headers.
     *
     * @return the headers
     */
    public List<String> getHeaders() {
        return this.headers;
    }

    /**
     * Get order int [ ].
     *
     * @return the int [ ]
     */
    public int[] getOrder() {
        return this.order;
    }

    /**
     * Gets header range.
     *
     * @return the header range
     */
    public Range getHeaderRange() {
        return this.headerRange;
    }

    /**
     * Gets data range.
     *
     * @return the data range
     */
    public Range getDataRange() {
        return this.dataRange;
    }

    /**
     * Is partial parse operation boolean.
     *
     * @return the boolean
     */
    public boolean isPartialParseOperation() {
        return this.isPartialParseOperation;
    }

    /**
     * Gets instantiator source.
     *
     * @return the instantiator source
     */
    public EntityInstantiatorSource getInstantiatorSource() {
        return this.instantiatorSource;
    }

    /**
     * The type Excel meta model builder.
     */
    public static class ExcelMetaModelBuilder {
        private List<String> headers;
        private Range headerRange;
        private Range dataRange;
        private boolean isPartialParseOperation;
        private boolean mergedHeader;
        private boolean hasOrder;
        private boolean allColumnOrder;
        private int[] order;
        private EntityInstantiatorSource instantiatorSource;

        /**
         * Headers excel meta model . excel meta model builder.
         *
         * @param headers the headers
         * @return the excel meta model . excel meta model builder
         */
        public ExcelMetaModel.ExcelMetaModelBuilder headers(final List<String> headers) {
            this.headers = headers;
            return this;
        }

        /**
         * Header range excel meta model . excel meta model builder.
         *
         * @param headerRange the header range
         * @return the excel meta model . excel meta model builder
         */
        public ExcelMetaModel.ExcelMetaModelBuilder headerRange(final Range headerRange) {
            this.headerRange = headerRange;
            return this;
        }

        /**
         * Data range excel meta model . excel meta model builder.
         *
         * @param dataRange the data range
         * @return the excel meta model . excel meta model builder
         */
        public ExcelMetaModel.ExcelMetaModelBuilder dataRange(final Range dataRange) {
            this.dataRange = dataRange;
            return this;
        }

        /**
         * Is partial parse operation excel meta model . excel meta model builder.
         *
         * @param isPartialParseOperation the is partial parse operation
         * @return the excel meta model . excel meta model builder
         */
        public ExcelMetaModel.ExcelMetaModelBuilder isPartialParseOperation(final boolean isPartialParseOperation) {
            this.isPartialParseOperation = isPartialParseOperation;
            return this;
        }

        /**
         * Merged header excel meta model . excel meta model builder.
         *
         * @param mergedHeader the merged header
         * @return the excel meta model . excel meta model builder
         */
        public ExcelMetaModel.ExcelMetaModelBuilder mergedHeader(final boolean mergedHeader) {
            this.mergedHeader = mergedHeader;
            return this;
        }

        /**
         * Has order excel meta model . excel meta model builder.
         *
         * @param hasOrder the has order
         * @return the excel meta model . excel meta model builder
         */
        public ExcelMetaModel.ExcelMetaModelBuilder hasOrder(final boolean hasOrder) {
            this.hasOrder = hasOrder;
            return this;
        }

        /**
         * All column order excel meta model . excel meta model builder.
         *
         * @param allColumnOrder the all column order
         * @return the excel meta model . excel meta model builder
         */
        public ExcelMetaModel.ExcelMetaModelBuilder allColumnOrder(final boolean allColumnOrder) {
            this.allColumnOrder = allColumnOrder;
            return this;
        }

        /**
         * Order excel meta model . excel meta model builder.
         *
         * @param order the order
         * @return the excel meta model . excel meta model builder
         */
        public ExcelMetaModel.ExcelMetaModelBuilder order(final int[] order) {
            this.order = order;
            return this;
        }

        /**
         * Instantiator source excel meta model . excel meta model builder.
         *
         * @param instantiatorSource the instantiator source
         * @return the excel meta model . excel meta model builder
         */
        public ExcelMetaModel.ExcelMetaModelBuilder instantiatorSource(final EntityInstantiatorSource instantiatorSource) {
            this.instantiatorSource = instantiatorSource;
            return this;
        }

        /**
         * Build excel meta model.
         *
         * @return the excel meta model
         */
        public ExcelMetaModel build() {
            return new ExcelMetaModel(this.headers, this.headerRange, this.dataRange, this.isPartialParseOperation, this.mergedHeader, this.hasOrder, this.allColumnOrder, this.order, this.instantiatorSource);
        }
    }
}

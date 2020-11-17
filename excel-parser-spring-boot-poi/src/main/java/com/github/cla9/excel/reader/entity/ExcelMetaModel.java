package com.github.cla9.excel.reader.entity;

import com.github.cla9.excel.reader.row.Range;
import com.github.cla9.excel.reader.worker.EntityInstantiatorSource;

import java.util.List;

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

    public boolean hasMergeHeader() {
        return this.mergedHeader;
    }
    public boolean hasAllColumnOrder() {
        return this.allColumnOrder;
    }
    public boolean hasOrder() {
        return this.hasOrder;
    }
    public static ExcelMetaModel.ExcelMetaModelBuilder builder() {
        return new ExcelMetaModel.ExcelMetaModelBuilder();
    }


    public List<String> getHeaders() {
        return this.headers;
    }
    public int[] getOrder() {
        return this.order;
    }
    public Range getHeaderRange() {
        return this.headerRange;
    }
    public Range getDataRange() {
        return this.dataRange;
    }
    public boolean isPartialParseOperation() {
        return this.isPartialParseOperation;
    }
    public EntityInstantiatorSource getInstantiatorSource() {
        return this.instantiatorSource;
    }

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

        public ExcelMetaModel.ExcelMetaModelBuilder headers(final List<String> headers) {
            this.headers = headers;
            return this;
        }
        public ExcelMetaModel.ExcelMetaModelBuilder headerRange(final Range headerRange) {
            this.headerRange = headerRange;
            return this;
        }
        public ExcelMetaModel.ExcelMetaModelBuilder dataRange(final Range dataRange) {
            this.dataRange = dataRange;
            return this;
        }
        public ExcelMetaModel.ExcelMetaModelBuilder isPartialParseOperation(final boolean isPartialParseOperation) {
            this.isPartialParseOperation = isPartialParseOperation;
            return this;
        }
        public ExcelMetaModel.ExcelMetaModelBuilder mergedHeader(final boolean mergedHeader) {
            this.mergedHeader = mergedHeader;
            return this;
        }
        public ExcelMetaModel.ExcelMetaModelBuilder hasOrder(final boolean hasOrder) {
            this.hasOrder = hasOrder;
            return this;
        }
        public ExcelMetaModel.ExcelMetaModelBuilder allColumnOrder(final boolean allColumnOrder) {
            this.allColumnOrder = allColumnOrder;
            return this;
        }
        public ExcelMetaModel.ExcelMetaModelBuilder order(final int[] order) {
            this.order = order;
            return this;
        }
        public ExcelMetaModel.ExcelMetaModelBuilder instantiatorSource(final EntityInstantiatorSource instantiatorSource) {
            this.instantiatorSource = instantiatorSource;
            return this;
        }
        public ExcelMetaModel build() {
            return new ExcelMetaModel(this.headers, this.headerRange, this.dataRange, this.isPartialParseOperation, this.mergedHeader, this.hasOrder, this.allColumnOrder, this.order, this.instantiatorSource);
        }
    }
}

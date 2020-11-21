package com.github.cla9.excel.reader.entity;

import com.github.cla9.excel.reader.row.Range;
import com.github.cla9.excel.reader.worker.EntityInstantiatorSource;
import lombok.Builder;

import java.util.List;

/**
 * The type Excel meta model.
 */
public class ExcelMetaModel {
    private final Class<?> entityType;
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
     * @param entityType              the entity type
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
    @Builder
    public ExcelMetaModel(Class<?> entityType, List<String> headers, Range headerRange, Range dataRange, boolean isPartialParseOperation, boolean mergedHeader, boolean hasOrder, boolean allColumnOrder, int[] order, EntityInstantiatorSource instantiatorSource) {
        this.entityType = entityType;
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
     * Gets entity type.
     *
     * @return the entity type
     */
    public Class<?> getEntityType() {
        return entityType;
    }
}

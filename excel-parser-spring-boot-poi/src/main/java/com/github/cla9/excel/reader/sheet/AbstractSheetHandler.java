package com.github.cla9.excel.reader.sheet;

import com.github.cla9.excel.reader.entity.ExcelMetaModel;
import com.github.cla9.excel.reader.exception.InvalidHeaderException;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.github.cla9.excel.reader.util.ExcelConstant.UNDECIDED;

/**
 * The type Abstract sheet handler.
 */
public abstract class AbstractSheetHandler implements SheetHandler {
    /**
     * The Excel meta model.
     */
    protected final ExcelMetaModel excelMetaModel;
    /**
     * The Header names.
     */
    protected List<String> headerNames;
    /**
     * The Order.
     */
    protected int[] order;

    /**
     * Instantiates a new Abstract sheet handler.
     *
     * @param metadata the metadata
     */
    protected AbstractSheetHandler(ExcelMetaModel metadata) {
        this.excelMetaModel = metadata;
        this.headerNames = new ArrayList<>();
    }


    /**
     * Create order.
     */
    protected void createOrder(){
        if(!excelMetaModel.isPartialParseOperation()){
            this.order = IntStream.range(0, headerNames.size()).toArray();
        } else if (excelMetaModel.hasAllColumnOrder()) {
            this.order = excelMetaModel.getOrder();
        } else {
            Map<String, List<Integer>> headerMap = getHeaderIndexedMap();

            final List<String> metadataHeaders = this.excelMetaModel.getHeaders();
            this.order = IntStream.range(0, metadataHeaders.size())
                    .map(i -> {
                        final String headerName = metadataHeaders.get(i);
                        final List<Integer> indices = headerMap.get(headerName);
                        final String entityName = excelMetaModel.getEntityType().getName();
                        if (Objects.isNull(indices)) {
                            throw new InvalidHeaderException(String.format("There is no matched headerName. Entity : %s HeaderName : %s", entityName, headerName));
                        }
                        if (indices.size() == 1) {
                            return indices.get(0);
                        } else {
                            final int columnOrder = excelMetaModel.getOrder()[i];
                            if (indices.size() > 1 && columnOrder != UNDECIDED) {
                                if(indices.indexOf(columnOrder) == -1)
                                    throw new InvalidHeaderException(String.format("There is no matched header Index. Entity : %s headerName : %s index : %d",entityName, headerName, columnOrder));
                                return columnOrder;
                            }
                        }
                        return -1;
                    })
                    .filter(i -> i != -1)
                    .toArray();
        }
    }

    /**
     * Validate order.
     */
    protected void validateOrder(){
        final int[] metaDataOrder = excelMetaModel.getOrder();
        final String entityName = excelMetaModel.getEntityType().getName();
        if(metaDataOrder.length != order.length) {
            throw new InvalidHeaderException(String.format("Actual file headerName and ExcelColumn information doesn't matched. Entity : %s ",entityName));
        }
        IntStream.range(0, metaDataOrder.length)
                .filter(i -> (order[i] != metaDataOrder[i] && metaDataOrder[i] != UNDECIDED) || order[i] >= headerNames.size())
                .forEach(i -> {
                    throw new InvalidHeaderException(String.format("Index doesn't matched with actual excel file column order. Entity : %s Index : %d" , entityName, metaDataOrder[i]+1));
                });
    }

    /**
     * Validate header.
     */
    protected void validateHeader(){
        if (!excelMetaModel.hasAllColumnOrder() && excelMetaModel.getHeaders().size() != headerNames.size())
            throw new InvalidHeaderException(String.format("There is mismatched header name. Entity : %s", excelMetaModel.getEntityType().getName()));
    }

    /**
     * Re order header name.
     */
    protected void reOrderHeaderName(){
        headerNames = Arrays.stream(order).mapToObj(item -> headerNames.get(item))
                .collect(Collectors.toList());
    }

    private Map<String, List<Integer>> getHeaderIndexedMap() {
        Map<String, List<Integer>> headerMap = new HashMap<>();
        for(int i = 0; i < headerNames.size(); i++){
            final String headerName = headerNames.get(i);
            List<Integer> indices = headerMap.get(headerName);
            if(Objects.isNull(indices)){
                indices = new ArrayList<>();
            }
            indices.add(i);
            headerMap.put(headerName, indices);
        }
        return headerMap;
    }

    @Override
    public List<String> getHeaderNames() {
        if (!excelMetaModel.hasAllColumnOrder() || !excelMetaModel.isPartialParseOperation()) {
            return headerNames;
        }
        else {
            return excelMetaModel.getHeaders();
        }
    }

    @Override
    public int[] getOrder() {
        return order.clone();
    }
}

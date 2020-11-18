package com.github.cla9.excel.reader.sheet;

import org.apache.poi.ss.util.CellRangeAddress;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Sax sheet merge header handler.
 */
public class SAXSheetMergeHeaderHandler extends DefaultHandler {

    private List<MergedArea> mergedAreas;

    /**
     * Instantiates a new Sax sheet merge header handler.
     */
    public SAXSheetMergeHeaderHandler() {
        this.mergedAreas = new ArrayList<>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if ("mergeCell".equals(qName) && attributes.getValue("ref") != null){
            final CellRangeAddress region = CellRangeAddress.valueOf(attributes.getValue("ref"));
            mergedAreas.add(new MergedArea(region.getFirstColumn(), region.getFirstRow(), region.getLastColumn(), region.getLastRow()));
        }
    }

    /**
     * Gets merged areas.
     *
     * @return the merged areas
     */
    public List<MergedArea> getMergedAreas() {
        return mergedAreas;
    }

}

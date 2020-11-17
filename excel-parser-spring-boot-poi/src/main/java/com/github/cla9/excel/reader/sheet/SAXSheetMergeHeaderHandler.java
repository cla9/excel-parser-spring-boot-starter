package com.github.cla9.excel.reader.sheet;

import org.apache.poi.ss.util.CellRangeAddress;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class SAXSheetMergeHeaderHandler extends DefaultHandler {

    private List<MergedArea> mergedAreas;

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

    public List<MergedArea> getMergedAreas() {
        return mergedAreas;
    }

}

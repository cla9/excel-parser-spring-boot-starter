package com.github.cla9.excel.reader.sheet;

import com.github.cla9.excel.reader.entity.ExcelMetaModel;
import com.github.cla9.excel.reader.exception.InvalidSheetNameException;
import com.github.cla9.excel.reader.row.Range;
import com.github.cla9.excel.reader.row.WorkBookRowHandler;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.IntStream;

import static com.github.cla9.excel.reader.util.ExcelConstant.UNDECIDED;


/**
 * The type Work book sheet handler.
 */
public final class WorkBookSheetHandler extends AbstractSheetHandler   {
    private final Sheet sheet;
    private final Range dataRange;
    private final Range headerRange;
    private final ExcelMetaModel metadata;
    private final WorkBookRowHandler rowHandler;
    private final List<MergedArea> mergedAreas;
    /**
     * The Row generation success callback.
     */
    protected Consumer<Row> rowGenerationSuccessCallback;

    /**
     * Instantiates a new Work book sheet handler.
     *
     * @param workbook       the workbook
     * @param excelMetaModel the excel meta model
     */
    public WorkBookSheetHandler(final Workbook workbook, final ExcelMetaModel excelMetaModel) {
        super(excelMetaModel);
        this.sheet = this.sheetName.isPresent()
            ? workbook.getSheet(this.sheetName.get()) : workbook.getSheetAt(0);

        validateSheet();

        this.dataRange = excelMetaModel.getDataRange();
        this.headerRange = excelMetaModel.getHeaderRange();
        rowHandler = new WorkBookRowHandler();
        mergedAreas = new ArrayList<>();
        metadata = excelMetaModel;
        init(excelMetaModel);
    }

    /**
     * Instantiates a new Work book sheet handler.
     *
     * @param sheetName      the worksheet name
     * @param workbook       the workbook
     * @param excelMetaModel the excel meta model
     */
    public WorkBookSheetHandler(final String sheetName, final Workbook workbook, final ExcelMetaModel excelMetaModel) {
        super(sheetName, excelMetaModel);
        this.sheet = this.sheetName.isPresent()
            ? workbook.getSheet(this.sheetName.get()) : workbook.getSheetAt(0);

        validateSheet();

        this.dataRange = excelMetaModel.getDataRange();
        this.headerRange = excelMetaModel.getHeaderRange();
        rowHandler = new WorkBookRowHandler();
        mergedAreas = new ArrayList<>();
        metadata = excelMetaModel;
        init(excelMetaModel);
    }

    private void validateSheet() {
        if (Objects.isNull(this.sheet)) {
            throw new InvalidSheetNameException(this.sheetName.get());
        }
    }

    private void init(ExcelMetaModel excelMetaModel) {
        createHeader();
        createOrder();
        if (excelMetaModel.isPartialParseOperation()) {
            validateOrder();
            reOrderHeaderName();
            validateHeader();
        }
    }

    /**
     * Create header.
     */
    protected void createHeader() {
        createMergedArea();
        boolean isMerged;
        createHeaderSet();
        for (int y = headerRange.getStart(); y <= headerRange.getEnd(); y++) {
            Row row = sheet.getRow(y);
            for (int x = 0; x <= row.getPhysicalNumberOfCells(); x++) {
                isMerged = false;
                for (final MergedArea mergedArea : mergedAreas) {
                    if (mergedArea.isContain(x, y)) {
                        if (mergedArea.getLeftTopY() == y) {
                            final String headerName = headerNames.get(x);
                            headerNames.set(x, !Objects.isNull(headerName) ? headerName + "." + mergedArea.getHeader() : mergedArea.getHeader());
                        }
                        isMerged = true;
                        break;
                    }
                }
                if (!isMerged) {
                    final Cell cell = row.getCell(x);
                    final String cellValue = !Objects.isNull(cell) ? cell.getStringCellValue() : null;
                    final String headerName = headerNames.get(x);
                    String fullHeaderName = !Objects.isNull(headerName) ? headerName : "";
                    if(!Objects.isNull(cellValue)){
                        fullHeaderName += (!"".equals(fullHeaderName) ? "." : "") + cellValue;
                    }
                    headerNames.set(x, fullHeaderName);
                }
            }
        }
    }

    private void createHeaderSet() {
        final int maxHeaderCount = IntStream.rangeClosed(headerRange.getStart(), headerRange.getEnd())
                .map(i -> sheet.getRow(i).getPhysicalNumberOfCells())
                .max().getAsInt();

        IntStream.range(0, maxHeaderCount + 1)
                .forEach(i -> headerNames.add(null));
    }

    /**
     * Parse.
     */
    public void parse() {
        final int rowCount = sheet.getPhysicalNumberOfRows();
        final int endRow = dataRange.getEnd() == UNDECIDED ? rowCount : dataRange.getEnd();

        IntStream.rangeClosed(dataRange.getStart(), endRow)
                .mapToObj(sheet::getRow)
                .filter(this::isPass)
                .forEach(rowGenerationSuccessCallback);
    }


    private void createMergedArea() {
        for (int i = 0; i < sheet.getNumMergedRegions(); i++) {
            final CellRangeAddress region = sheet.getMergedRegion(i);
            final MergedArea mergedArea = new MergedArea(region.getFirstColumn(), region.getFirstRow(), region.getLastColumn(), region.getLastRow());
            final String cellValue = sheet.getRow(region.getFirstRow()).getCell(region.getFirstColumn()).getStringCellValue();
            mergedArea.setHeader(cellValue);
            mergedAreas.add(mergedArea);
        }
    }

    private boolean isPass(Row row) {
        if (Objects.isNull(row)) return false;
        rowHandler.setRow(row);
        return IntStream.range(0, row.getPhysicalNumberOfCells())
                .mapToObj(rowHandler::getValue)
                .anyMatch(StringUtils::isNotEmpty);
    }

    /**
     * Sets row generation success callback.
     *
     * @param rowGenerationSuccessCallback the row generation success callback
     */
    public void setRowGenerationSuccessCallback(Consumer<Row> rowGenerationSuccessCallback) {
        this.rowGenerationSuccessCallback = rowGenerationSuccessCallback;
    }
}

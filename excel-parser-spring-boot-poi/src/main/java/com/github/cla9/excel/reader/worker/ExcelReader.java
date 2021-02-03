package com.github.cla9.excel.reader.worker;

import com.github.cla9.excel.reader.entity.ExcelMetaModel;
import com.github.cla9.excel.reader.entity.ExcelRowException;
import com.github.cla9.excel.reader.entity.ExceptionRow;
import com.github.cla9.excel.reader.row.RowHandler;
import java.util.Optional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;


/**
 * The type Excel reader.
 *
 * @param <T> the type parameter
 */
public abstract class ExcelReader<T> implements Reader<T> {
    /**
     * The T class.
     */
    protected final Class<T> tClass;
    /**
     * The Entity parser.
     */
    protected final EntityParser entityParser;
    private final EntityInstantiator<T> entityGenerator;
    private ExcelMetaModel excelMetaModel;

    /**
     * Target sheet name of excel file.
     */

    protected final Optional<String> sheetName;

    /**
     * Instantiates a new Excel reader.
     *
     * @param tClass          the t class
     * @param entityParser    the entity parser
     * @param entityGenerator the entity generator
     */
    public ExcelReader(Class<T> tClass, EntityParser entityParser, EntityInstantiator<T> entityGenerator) {
        this.tClass = tClass;
        this.entityParser = entityParser;
        this.entityGenerator = entityGenerator;
        this.entityParser.parse(tClass);
        this.excelMetaModel = entityParser.getEntityMetadata();
        this.sheetName = Optional.empty();
    }

    /**
     * Instantiates a new Excel reader.
     *
     * @param tClass the t class
     */
    protected ExcelReader(Class<T> tClass) {
        this.tClass = tClass;
        entityParser = new ExcelEntityParser();
        entityGenerator = new EntityInstantiator<>();
        entityParser.parse(tClass);
        excelMetaModel = entityParser.getEntityMetadata();
        this.sheetName = Optional.empty();
    }

    /**
     * Instantiates a new Excel reader.
     *
     * @param tClass the t class
     */
    protected ExcelReader(Class<T> tClass, String sheetName) {
        this.tClass = tClass;
        entityParser = new ExcelEntityParser();
        entityGenerator = new EntityInstantiator<>();
        entityParser.parse(tClass);
        excelMetaModel = entityParser.getEntityMetadata();
        this.sheetName = Optional.of(sheetName);
    }

    /**
     * Instantiates a new Excel reader.
     *
     * @param tClass         the t class
     * @param excelMetaModel the excel meta model
     */
    public ExcelReader(Class<T> tClass, ExcelMetaModel excelMetaModel) {
        this.tClass = tClass;
        this.entityParser = null;
        this.entityGenerator = new EntityInstantiator<>();
        this.excelMetaModel = excelMetaModel;
        this.sheetName = Optional.empty();
    }

    /**
     * Instantiates a new Excel reader.
     *
     * @param tClass         the t class
     * @param sheetName      the worksheet name
     * @param excelMetaModel the excel meta model
     */
    public ExcelReader(Class<T> tClass, String sheetName, ExcelMetaModel excelMetaModel) {
        this.tClass = tClass;
        this.entityParser = null;
        this.entityGenerator = new EntityInstantiator<>();
        this.excelMetaModel = excelMetaModel;
        this.sheetName = Optional.of(sheetName);
    }

    @Override
    public ExcelResultSet<T> createResultSet(final MultipartFile multipartFile) {
        return createResultSet(multipartFile, excelMetaModel, false);
    }

    @Override
    public ExcelResultSet<T> createResultSet(MultipartFile multipartFile, boolean forceMergedHeaderRefresh) {
        return createResultSet(multipartFile, excelMetaModel, forceMergedHeaderRefresh);
    }

    @Override
    public void parse(final MultipartFile multipartFile, final Consumer<T> onSuccessConsumer, final Consumer<ExcelRowException> onErrorConsumer) {
        parseFile(multipartFile, excelMetaModel,  onSuccessConsumer, onErrorConsumer, false);
    }


    @Override
    public void parse(MultipartFile multipartFile, Consumer<T> onSuccessConsumer, Consumer<ExcelRowException> onErrorConsumer, boolean forceMergedHeaderRefresh) {
        parseFile(multipartFile, excelMetaModel,  onSuccessConsumer, onErrorConsumer, forceMergedHeaderRefresh);
    }

    /**
     * Create result set excel result set.
     *
     * @param multipartFile            the multipart file
     * @param metadata                 the metadata
     * @param forceMergedHeaderRefresh the force merged header refresh
     * @return the excel result set
     */
    abstract protected ExcelResultSet<T> createResultSet(final MultipartFile multipartFile, final ExcelMetaModel metadata, boolean forceMergedHeaderRefresh);

    /**
     * Parse file.
     *
     * @param multipartFile            the multipart file
     * @param excelMetaModel           the excel meta model
     * @param onSuccessConsumer        the on success consumer
     * @param onErrorConsumer          the on error consumer
     * @param forceMergedHeaderRefresh the force merged header refresh
     */
    abstract protected void parseFile(final MultipartFile multipartFile, final ExcelMetaModel excelMetaModel, final Consumer<T> onSuccessConsumer, final Consumer<ExcelRowException> onErrorConsumer, boolean forceMergedHeaderRefresh);


    /**
     * Inject value t.
     *
     * @param <R>                  the type parameter
     * @param rowHandler           the row handler
     * @param excelFileHeaderNames the excel file header names
     * @return the t
     * @throws ExcelRowException the excel row exception
     */
    protected <R> T injectValue(RowHandler<R> rowHandler, List<String> excelFileHeaderNames) throws ExcelRowException {
        EntityInjectionResult<T> result = entityGenerator.createInstance(tClass, excelFileHeaderNames, excelMetaModel, rowHandler);
        if (Objects.isNull(result.getObject()))
            return null;

        if (!result.getExceptions().isEmpty())
            throw new ExcelRowException(result.getObject(), result.getExceptions());

        return result.getObject();
    }

    /**
     * Create exception row exception row.
     *
     * @param ex the ex
     * @return the exception row
     */
    @SuppressWarnings("unchecked")
    protected ExceptionRow<T> createExceptionRow(ExcelRowException ex) {
        return (ExceptionRow<T>) ExceptionRow.builder()
                .row(ex.getRow())
                .errors(ex.getExcelReaderErrorField())
                .build();
    }
}

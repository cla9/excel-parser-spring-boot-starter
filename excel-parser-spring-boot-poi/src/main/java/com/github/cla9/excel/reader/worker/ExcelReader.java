package com.github.cla9.excel.reader.worker;

import com.github.cla9.excel.reader.entity.ExcelMetaModel;
import com.github.cla9.excel.reader.entity.ExcelRowException;
import com.github.cla9.excel.reader.entity.ExceptionRow;
import com.github.cla9.excel.reader.row.RowHandler;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;


public abstract class ExcelReader<T> implements Reader<T> {
    protected final Class<T> tClass;
    protected final EntityParser entityParser;
    private final EntityInstantiator<T> entityGenerator;
    private ExcelMetaModel excelMetaModel;

    public ExcelReader(Class<T> tClass, EntityParser entityParser, EntityInstantiator<T> entityGenerator) {
        this.tClass = tClass;
        this.entityParser = entityParser;
        this.entityGenerator = entityGenerator;
        this.entityParser.parse(tClass);
        this.excelMetaModel = entityParser.getEntityMetadata();
    }

    protected ExcelReader(Class<T> tClass) {
        this.tClass = tClass;
        entityParser = new ExcelEntityParser();
        entityGenerator = new EntityInstantiator<>();
        entityParser.parse(tClass);
        excelMetaModel = entityParser.getEntityMetadata();
    }

    public ExcelReader(Class<T> tClass, ExcelMetaModel excelMetaModel) {
        this.tClass = tClass;
        this.entityParser = null;
        this.entityGenerator = new EntityInstantiator<>();
        this.excelMetaModel = excelMetaModel;
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

    abstract protected ExcelResultSet<T> createResultSet(final MultipartFile multipartFile, final ExcelMetaModel metadata, boolean forceMergedHeaderRefresh);
    abstract protected void parseFile(final MultipartFile multipartFile, final ExcelMetaModel excelMetaModel, final Consumer<T> onSuccessConsumer, final Consumer<ExcelRowException> onErrorConsumer, boolean forceMergedHeaderRefresh);


    protected <R> T injectValue(RowHandler<R> rowHandler, List<String> excelFileHeaderNames) throws ExcelRowException {
        EntityInjectionResult<T> result = entityGenerator.createInstance(tClass, excelFileHeaderNames, excelMetaModel, rowHandler);
        if (Objects.isNull(result.getObject()))
            return null;

        if (!result.getExceptions().isEmpty())
            throw new ExcelRowException(result.getObject(), result.getExceptions());

        return result.getObject();
    }

    @SuppressWarnings("unchecked")
    protected ExceptionRow<T> createExceptionRow(ExcelRowException ex) {
        return (ExceptionRow<T>) ExceptionRow.builder()
                .row(ex.getRow())
                .errors(ex.getExcelReaderErrorField())
                .build();
    }
}

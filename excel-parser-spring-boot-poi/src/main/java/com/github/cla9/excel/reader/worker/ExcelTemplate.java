package com.github.cla9.excel.reader.worker;

import com.github.cla9.excel.reader.entity.ExcelRowException;
import com.github.cla9.excel.reader.factory.ReaderFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.function.Consumer;

/**
 * The type Excel template.
 */
public class ExcelTemplate{
    private final ReaderFactory readerFactory;

    /**
     * Instantiates a new Excel template.
     *
     * @param readerFactory the reader factory
     */
    public ExcelTemplate(ReaderFactory readerFactory) {
        this.readerFactory = readerFactory;
    }

    /**
     * Create result set excel result set.
     *
     * @param <T>           the type parameter
     * @param clazz         the clazz
     * @param multipartFile the multipart file
     * @return the excel result set
     */
    public <T> ExcelResultSet<T> createResultSet(final Class<T> clazz, final MultipartFile multipartFile){
        return readerFactory.createInstance(clazz).createResultSet(multipartFile);
    }

    /**
     * Create result set excel result set.
     *
     * @param <T>                      the type parameter
     * @param clazz                    the clazz
     * @param multipartFile            the multipart file
     * @param forceMergedHeaderRefresh the force merged header refresh
     * @return the excel result set
     */
    public <T> ExcelResultSet<T> createResultSet(final Class<T> clazz,final MultipartFile multipartFile, final boolean forceMergedHeaderRefresh){
        return readerFactory.createInstance(clazz).createResultSet(multipartFile, forceMergedHeaderRefresh);
    }

    /**
     * Parse.
     *
     * @param <T>               the type parameter
     * @param clazz             the clazz
     * @param multipartFile     the multipart file
     * @param onSuccessConsumer the on success consumer
     * @param onErrorConsumer   the on error consumer
     */
    public <T> void parse(final Class<T> clazz, final  MultipartFile multipartFile,
               final Consumer<T> onSuccessConsumer,
               final Consumer<ExcelRowException> onErrorConsumer){
        readerFactory.createInstance(clazz).parse(multipartFile, onSuccessConsumer, onErrorConsumer);
    }

    /**
     * Parse.
     *
     * @param <T>                      the type parameter
     * @param clazz                    the clazz
     * @param multipartFile            the multipart file
     * @param onSuccessConsumer        the on success consumer
     * @param onErrorConsumer          the on error consumer
     * @param forceMergedHeaderRefresh the force merged header refresh
     */
    public <T> void parse(final Class<T> clazz, final  MultipartFile multipartFile,
               final Consumer<T> onSuccessConsumer,
               final Consumer<ExcelRowException> onErrorConsumer, final boolean forceMergedHeaderRefresh){
        readerFactory.createInstance(clazz).parse(multipartFile, onSuccessConsumer, onErrorConsumer, forceMergedHeaderRefresh);
    }
}

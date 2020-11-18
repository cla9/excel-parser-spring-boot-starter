package com.github.cla9.excel.reader.worker;

import com.github.cla9.excel.reader.entity.ExcelRowException;
import org.springframework.web.multipart.MultipartFile;

import java.util.function.Consumer;

/**
 * The interface Reader.
 *
 * @param <T> the type parameter
 */
public interface Reader<T> {
    /**
     * Create result set excel result set.
     *
     * @param multipartFile the multipart file
     * @return the excel result set
     */
    ExcelResultSet<T> createResultSet(final MultipartFile multipartFile);

    /**
     * Create result set excel result set.
     *
     * @param multipartFile            the multipart file
     * @param forceMergedHeaderRefresh the force merged header refresh
     * @return the excel result set
     */
    ExcelResultSet<T> createResultSet(final MultipartFile multipartFile, final boolean forceMergedHeaderRefresh);

    /**
     * Parse.
     *
     * @param multipartFile     the multipart file
     * @param onSuccessConsumer the on success consumer
     * @param onErrorConsumer   the on error consumer
     */
    void parse(final  MultipartFile multipartFile,
                      final Consumer<T> onSuccessConsumer,
                      final Consumer<ExcelRowException> onErrorConsumer);

    /**
     * Parse.
     *
     * @param multipartFile            the multipart file
     * @param onSuccessConsumer        the on success consumer
     * @param onErrorConsumer          the on error consumer
     * @param forceMergedHeaderRefresh the force merged header refresh
     */
    void parse(final  MultipartFile multipartFile,
               final Consumer<T> onSuccessConsumer,
               final Consumer<ExcelRowException> onErrorConsumer, final boolean forceMergedHeaderRefresh);
}

package com.github.cla9.excel.reader.worker;

import com.github.cla9.excel.reader.entity.ExcelRowException;
import org.springframework.web.multipart.MultipartFile;

import java.util.function.Consumer;

public interface Reader<T> {
    ExcelResultSet<T> createResultSet(final MultipartFile multipartFile);
    ExcelResultSet<T> createResultSet(final MultipartFile multipartFile, final boolean forceMergedHeaderRefresh);
    void parse(final  MultipartFile multipartFile,
                      final Consumer<T> onSuccessConsumer,
                      final Consumer<ExcelRowException> onErrorConsumer);
    void parse(final  MultipartFile multipartFile,
               final Consumer<T> onSuccessConsumer,
               final Consumer<ExcelRowException> onErrorConsumer, final boolean forceMergedHeaderRefresh);
}

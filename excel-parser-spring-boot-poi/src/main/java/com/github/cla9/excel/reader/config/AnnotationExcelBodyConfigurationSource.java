package com.github.cla9.excel.reader.config;

import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public class AnnotationExcelBodyConfigurationSource implements ExcelBodyConfigurationSource {
    private static final String BASE_PACKAGES = "basePackages";
    private static final String BASE_PACKAGES_CLASSES = "basePackageClasses";

    private final AnnotationMetadata configMetadata;
    private final AnnotationAttributes attributes;

    public AnnotationExcelBodyConfigurationSource(AnnotationMetadata metadata, Class<? extends Annotation> annotation) {

        Map<String, Object> annotationAttributes = metadata.getAnnotationAttributes(annotation.getName());

        this.configMetadata = metadata;
        assert annotationAttributes != null;
        this.attributes = new AnnotationAttributes(annotationAttributes);
    }

    @Override
    public Object getSource() {
        return configMetadata;
    }

    @Override
    public Stream<String> getBasePackages() {
        String[] value = attributes.getStringArray("value");
        String[] basePackages = attributes.getStringArray(BASE_PACKAGES);
        Class<?>[] basePackageClasses = attributes.getClassArray(BASE_PACKAGES_CLASSES);

        if(value.length == 0 && basePackages.length == 0 && basePackageClasses.length == 0){
            String className = configMetadata.getClassName();
            return Stream.of(ClassUtils.getPackageName(className));
        }

        Set<String> packages = new HashSet<>();
        packages.addAll(Arrays.asList(value));
        packages.addAll(Arrays.asList(basePackages));

        Arrays.stream(basePackageClasses)
                .map(ClassUtils::getPackageName)
                .forEach(packages::add);

        return packages.stream();
    }


}

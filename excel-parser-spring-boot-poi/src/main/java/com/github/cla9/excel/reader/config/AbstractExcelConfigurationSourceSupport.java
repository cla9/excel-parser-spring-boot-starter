package com.github.cla9.excel.reader.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;

import java.lang.annotation.Annotation;
import java.util.stream.Stream;


public abstract class AbstractExcelConfigurationSourceSupport implements
        BeanFactoryAware, ImportBeanDefinitionRegistrar, ResourceLoaderAware, EnvironmentAware {
    private ResourceLoader resourceLoader;
    private BeanFactory beanFactory;
    private Environment environment;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {

        ExcelConfigurationDelegate.builder()
                .configurationSource(getConfigurationSource())
                .resourceLoader(resourceLoader)
                .registry(registry)
                .beanFactory(beanFactory)
                .environment(environment)
                .build().registerExcelEntitiesIn();
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }


    private AnnotationExcelBodyConfigurationSource getConfigurationSource() {
        final AnnotationMetadata metadata = AnnotationMetadata.introspect(getConfiguration());
        return new AnnotationExcelBodyConfigurationSource(metadata, getAnnotation()) {
            @Override
            public Stream<String> getBasePackages() {
                return AbstractExcelConfigurationSourceSupport.this.getBasePackages();
            }
        };
    }

    protected Stream<String> getBasePackages() {
        return AutoConfigurationPackages.get(beanFactory).stream();
    }
    protected abstract Class<? extends Annotation> getAnnotation();
    protected abstract Class<?> getConfiguration();
}

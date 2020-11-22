package com.github.cla9.excel.reader.config;

import com.github.cla9.excel.reader.annotation.EnableExcelEntityScan;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;

import java.lang.annotation.Annotation;
import java.util.Objects;

public class ExcelEntityScanRegistrar implements BeanFactoryAware,ImportBeanDefinitionRegistrar, ResourceLoaderAware, EnvironmentAware {
    private ResourceLoader resourceLoader;
    private Environment environment;
    private BeanFactory beanFactory;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {

        if(Objects.isNull(metadata.getAnnotationAttributes(getAnnotation().getName()))){
            return;
        }

        final String metaModelMappingContextName = "excelMetaModelMappingContext";
        if (!registry.containsBeanDefinition(metaModelMappingContextName)) {
            registry.registerBeanDefinition(metaModelMappingContextName, BeanDefinitionBuilder.genericBeanDefinition(ExcelMetaModelMappingContext.class).getBeanDefinition());
        }

        AnnotationExcelBodyConfigurationSource configurationSource = new AnnotationExcelBodyConfigurationSource(metadata, getAnnotation());

        ExcelConfigurationDelegate.builder()
                .configurationSource(configurationSource)
                .resourceLoader(resourceLoader)
                .beanFactory(beanFactory)
                .registry(registry)
                .environment(environment)
                .build().registerExcelEntitiesIn();
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    protected Class<? extends Annotation> getAnnotation(){ return EnableExcelEntityScan.class;}

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}

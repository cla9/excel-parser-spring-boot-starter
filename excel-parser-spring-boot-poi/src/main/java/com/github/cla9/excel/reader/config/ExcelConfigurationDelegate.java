package com.github.cla9.excel.reader.config;

import com.github.cla9.excel.reader.annotation.ExcelBody;
import com.github.cla9.excel.reader.annotation.ExcelMetaCachePut;
import com.github.cla9.excel.reader.util.ExcelBeanUtil;
import com.github.cla9.excel.reader.worker.ExcelEntityParser;
import lombok.Builder;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.lang.reflect.Field;
import java.util.Set;

public class ExcelConfigurationDelegate {
    private final ExcelBodyConfigurationSource configurationSource;
    private final ResourceLoader resourceLoader;
    private final Environment environment;
    private final BeanDefinitionRegistry registry;
    private final BeanFactory beanFactory;

    @Builder
    public ExcelConfigurationDelegate(BeanDefinitionRegistry registry, BeanFactory beanFactory, ExcelBodyConfigurationSource configurationSource, ResourceLoader resourceLoader, Environment environment) {
        this.configurationSource = configurationSource;
        this.resourceLoader = resourceLoader;
        this.environment = environment;
        this.registry = registry;
        this.beanFactory = beanFactory;
    }

    public void createExtension(){
        final Class<?> extensionClass = ExcelEntityConfigurationExtension.class;
        final String beanName = extensionClass.getName().concat("#0");
        if (registry.containsBeanDefinition(beanName)) {
            return;
        }

        RootBeanDefinition definition = new RootBeanDefinition(extensionClass);
        definition.setSource(configurationSource);
        definition.setRole(AbstractBeanDefinition.ROLE_INFRASTRUCTURE);
        registry.registerBeanDefinition(beanName, definition);
    }

    public void registerExcelEntitiesIn(){
        createExtension();
        setExcelBeanUtil();

        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);

        provider.setEnvironment(environment);
        provider.setResourceLoader(resourceLoader);
        provider.addIncludeFilter(new AnnotationTypeFilter(ExcelBody.class));

        final ExcelEntityParser entityParser = new ExcelEntityParser();
        final ExcelMetaModelMappingContext metaModelContext = beanFactory.getBean(ExcelMetaModelMappingContext.class);

        configurationSource.getBasePackages()
                .map(provider::findCandidateComponents)
                .flatMap(Set::stream)
                .map(BeanDefinition::getBeanClassName)
                .forEach(candidate -> {
                    try {
                        final Class<?> clazz = Class.forName(candidate);
                        entityParser.parse(clazz);
                        if(clazz.isAnnotationPresent(ExcelMetaCachePut.class)){
                            metaModelContext.putMetaModel(clazz, entityParser.getEntityMetadata());
                        }
                    } catch (ClassNotFoundException e) {
                        throw new IllegalStateException("Unable to find a entity class.", e);
                    }
                });
    }

    private void setExcelBeanUtil() {
        try {
            Field factory = ExcelBeanUtil.class.getDeclaredField("factory");
            factory.setAccessible(true);
            factory.set(null, beanFactory);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new InternalError("Unable find ExcelBeanUtil");
        }
    }
}

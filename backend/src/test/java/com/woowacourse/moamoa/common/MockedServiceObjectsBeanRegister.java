package com.woowacourse.moamoa.common;

import static org.mockito.Mockito.mock;
import static org.springframework.beans.factory.support.BeanDefinitionBuilder.rootBeanDefinition;

import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Service;

public class MockedServiceObjectsBeanRegister implements BeanDefinitionRegistryPostProcessor {

    @Override
    public void postProcessBeanDefinitionRegistry(final BeanDefinitionRegistry registry) throws BeansException {
        for (String className : getServiceLayerObjectClassNames()) {
            try {
                Class<?> cls = Class.forName(className);
                registry.registerBeanDefinition(beanName(cls), beanDefinition(cls));
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private Set<String> getServiceLayerObjectClassNames() {
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new AnnotationTypeFilter(Service.class));
        return provider.findCandidateComponents("com.woowacourse.moamoa")
                .stream()
                .map(BeanDefinition::getBeanClassName)
                .collect(Collectors.toSet());
    }

    private String beanName(final Class<?> cls) {
        final String clsName = cls.getSimpleName();
        return clsName.toLowerCase().charAt(0) + clsName.substring(1);
    }

    private <T> AbstractBeanDefinition beanDefinition(final Class<T> cls) {
        return rootBeanDefinition(cls, () -> mock(cls)).getBeanDefinition();
    }

    @Override
    public void postProcessBeanFactory(final ConfigurableListableBeanFactory beanFactory) throws BeansException {
    }
}

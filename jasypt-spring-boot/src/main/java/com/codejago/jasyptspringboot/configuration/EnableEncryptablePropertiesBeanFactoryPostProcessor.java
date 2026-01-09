package com.codejago.jasyptspringboot.configuration;

import com.codejago.jasyptspringboot.core.EncryptablePropertySourceConverter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;

/**
 * Converts every {@link org.springframework.core.env.PropertySource} in the {@link ConfigurableEnvironment}
 * into a decrypting variant right before the rest of the container starts using them.
 */
public final class EnableEncryptablePropertiesBeanFactoryPostProcessor implements BeanFactoryPostProcessor, Ordered {

    private final ConfigurableEnvironment environment;
    private final EncryptablePropertySourceConverter converter;

    public EnableEncryptablePropertiesBeanFactoryPostProcessor(
            ConfigurableEnvironment environment,
            EncryptablePropertySourceConverter converter
    ) {
        this.environment = environment;
        this.converter = converter;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        MutablePropertySources propertySources = environment.getPropertySources();
        converter.convertPropertySources(propertySources);
        org.springframework.boot.context.properties.source.ConfigurationPropertySources.attach(environment);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}

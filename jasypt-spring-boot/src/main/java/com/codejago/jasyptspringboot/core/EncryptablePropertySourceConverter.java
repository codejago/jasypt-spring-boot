package com.codejago.jasyptspringboot.core;

import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.SystemEnvironmentPropertySource;

import java.util.ArrayList;
import java.util.List;

/**
 * Turns every {@link PropertySource} in the environment into a decrypting version.
 */
public final class EncryptablePropertySourceConverter {

    private final EncryptablePropertyResolver resolver;

    public EncryptablePropertySourceConverter(EncryptablePropertyResolver resolver) {
        this.resolver = resolver;
    }

    public void convertPropertySources(MutablePropertySources propertySources) {
        List<PropertySource<?>> currentSources = new ArrayList<>();
        propertySources.forEach(currentSources::add);

        for (PropertySource<?> propertySource : currentSources) {
            PropertySource<?> encryptable = makeEncryptable(propertySource);
            if (encryptable != propertySource) {
                propertySources.replace(propertySource.getName(), encryptable);
            }
        }
    }

    private PropertySource<?> makeEncryptable(PropertySource<?> propertySource) {
        if (propertySource instanceof DecryptingPropertySource
                || propertySource instanceof DecryptingEnumerablePropertySource
                || propertySource instanceof DecryptingSystemEnvironmentPropertySource) {
            return propertySource;
        }

        if (isConfigurationPropertySourcesPropertySource(propertySource)) {
            return propertySource;
        }

        if (propertySource instanceof SystemEnvironmentPropertySource systemEnv) {
            return new DecryptingSystemEnvironmentPropertySource(systemEnv, resolver);
        }

        if (propertySource instanceof EnumerablePropertySource<?> enumerable) {
            return new DecryptingEnumerablePropertySource<>(enumerable, resolver);
        }

        return new DecryptingPropertySource<>(propertySource, resolver);
    }
    private boolean isConfigurationPropertySourcesPropertySource(PropertySource<?> propertySource) {
        String className = propertySource.getClass().getName();
        return "org.springframework.boot.context.properties.source.ConfigurationPropertySourcesPropertySource"
                .equals(className) || "org.springframework.boot.context.properties.source.ConfigurationPropertySources".equals(className);
    }
}

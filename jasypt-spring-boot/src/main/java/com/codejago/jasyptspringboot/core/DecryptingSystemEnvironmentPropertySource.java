package com.codejago.jasyptspringboot.core;

import org.springframework.core.env.SystemEnvironmentPropertySource;

import java.util.Map;
import java.util.Objects;

/**
 * Specialized {@link SystemEnvironmentPropertySource} that decrypts values.
 */
public class DecryptingSystemEnvironmentPropertySource extends SystemEnvironmentPropertySource {

    private final EncryptablePropertyResolver resolver;
    private final SystemEnvironmentPropertySource delegate;

    @SuppressWarnings("unchecked")
    public DecryptingSystemEnvironmentPropertySource(SystemEnvironmentPropertySource delegate, EncryptablePropertyResolver resolver) {
        super(delegate.getName(), (Map<String, Object>) delegate.getSource());
        this.resolver = Objects.requireNonNull(resolver, "resolver must not be null");
        this.delegate = delegate;
    }

    @Override
    public Object getProperty(String name) {
        Object value = delegate.getProperty(name);
        if (value instanceof String stringValue) {
            return resolver.resolvePropertyValue(stringValue);
        }
        return value;
    }
}

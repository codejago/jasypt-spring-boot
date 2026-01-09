package com.codejago.jasyptspringboot.core;

import org.springframework.core.env.PropertySource;

import java.util.Objects;

/**
 * Decorates an arbitrary {@link PropertySource} so that {@link #getProperty(String)} runs through the resolver.
 */
final class DecryptingPropertySource<T> extends PropertySource<T> {

    private final PropertySource<T> delegate;
    private final EncryptablePropertyResolver resolver;

    DecryptingPropertySource(PropertySource<T> delegate, EncryptablePropertyResolver resolver) {
        super(delegate.getName(), delegate.getSource());
        this.delegate = Objects.requireNonNull(delegate, "delegate must not be null");
        this.resolver = Objects.requireNonNull(resolver, "resolver must not be null");
    }

    @Override
    public Object getProperty(String name) {
        Object value = delegate.getProperty(name);
        return decryptValue(value);
    }

    private Object decryptValue(Object value) {
        if (value instanceof String stringValue) {
            return resolver.resolvePropertyValue(stringValue);
        }
        return value;
    }
}

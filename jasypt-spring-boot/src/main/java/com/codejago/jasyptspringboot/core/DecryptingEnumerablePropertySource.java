package com.codejago.jasyptspringboot.core;

import org.springframework.core.env.EnumerablePropertySource;

import java.util.Objects;

/**
 * Variant of {@link DecryptingPropertySource} that keeps the {@link EnumerablePropertySource} contract.
 */
final class DecryptingEnumerablePropertySource<T> extends EnumerablePropertySource<T> {

    private final EnumerablePropertySource<T> delegate;
    private final EncryptablePropertyResolver resolver;

    DecryptingEnumerablePropertySource(EnumerablePropertySource<T> delegate, EncryptablePropertyResolver resolver) {
        super(delegate.getName(), delegate.getSource());
        this.delegate = Objects.requireNonNull(delegate, "delegate must not be null");
        this.resolver = Objects.requireNonNull(resolver, "resolver must not be null");
    }

    @Override
    public String[] getPropertyNames() {
        return delegate.getPropertyNames();
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

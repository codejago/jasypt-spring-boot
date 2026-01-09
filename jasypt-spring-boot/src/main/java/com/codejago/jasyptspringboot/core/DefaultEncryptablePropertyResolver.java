package com.codejago.jasyptspringboot.core;

import org.jasypt.encryption.StringEncryptor;

import java.util.Objects;

/**
 * Straightforward implementation that looks for values wrapped with a prefix/suffix and decrypts the contents.
 */
public final class DefaultEncryptablePropertyResolver implements EncryptablePropertyResolver {

    private final StringEncryptor encryptor;
    private final String prefix;
    private final String suffix;

    public DefaultEncryptablePropertyResolver(StringEncryptor encryptor, String prefix, String suffix) {
        this.encryptor = Objects.requireNonNull(encryptor, "encryptor must not be null");
        this.prefix = Objects.requireNonNull(prefix, "prefix must not be null");
        this.suffix = Objects.requireNonNull(suffix, "suffix must not be null");
    }

    @Override
    public String resolvePropertyValue(String value) {
        if (value == null) {
            return null;
        }
        String trimmedValue = value.trim();
        if (!trimmedValue.startsWith(prefix) || !trimmedValue.endsWith(suffix)) {
            return value;
        }
        String encryptedPortion = trimmedValue.substring(prefix.length(), trimmedValue.length() - suffix.length());
        return encryptor.decrypt(encryptedPortion);
    }
}

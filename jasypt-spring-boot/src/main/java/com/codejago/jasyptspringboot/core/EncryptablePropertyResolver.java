package com.codejago.jasyptspringboot.core;

/**
 * Resolves a potential encrypted property value into its decrypted form.
 */
public interface EncryptablePropertyResolver {

    /**
     * @param value the original property value (may be {@code null})
     * @return {@code null} when {@code value} is {@code null}, the decrypted value when the input looks encrypted,
     * or the original value unchanged otherwise
     */
    String resolvePropertyValue(String value);
}

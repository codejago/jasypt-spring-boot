package com.codejago.jasyptspringboot.core;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;

/**
 * Builds the default {@link StringEncryptor} used by the {@code @EnableEncryptableProperties} path.
 */
public final class StringEncryptorFactory {

    private StringEncryptorFactory() {
    }

    public static StringEncryptor create(EncryptionSettings settings) {
        if (!settings.hasPassword()) {
            throw new IllegalStateException("Missing encryption password. Provide jasypt.encryptor.password or a custom StringEncryptor bean.");
        }

        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(settings.getPassword());
        config.setAlgorithm(settings.getAlgorithm());
        config.setKeyObtentionIterations(Integer.toString(settings.getKeyObtentionIterations()));
        config.setPoolSize(Integer.toString(settings.getPoolSize()));
        config.setStringOutputType(settings.getStringOutputType());
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setIvGeneratorClassName("org.jasypt.iv.RandomIvGenerator");

        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        encryptor.setConfig(config);
        return encryptor;
    }
}

package com.codejago.jasyptspringboot.core;

import org.springframework.core.env.ConfigurableEnvironment;

/**
 * Centralized view of the configuration flags understood by this simplified integration.
 */
public final class EncryptionSettings {

    private final String password;
    private final String algorithm;
    private final int keyObtentionIterations;
    private final int poolSize;
    private final String stringOutputType;
    private final String propertyPrefix;
    private final String propertySuffix;

    private EncryptionSettings(
            String password,
            String algorithm,
            int keyObtentionIterations,
            int poolSize,
            String stringOutputType,
            String propertyPrefix,
            String propertySuffix
    ) {
        this.password = password;
        this.algorithm = algorithm;
        this.keyObtentionIterations = keyObtentionIterations;
        this.poolSize = poolSize;
        this.stringOutputType = stringOutputType;
        this.propertyPrefix = propertyPrefix;
        this.propertySuffix = propertySuffix;
    }

    public static EncryptionSettings from(ConfigurableEnvironment environment) {
        String password = environment.getProperty("jasypt.encryptor.password");
        String algorithm = environment.getProperty("jasypt.encryptor.algorithm", "PBEWITHHMACSHA512ANDAES_256");
        int iterations = environment.getProperty("jasypt.encryptor.key-obtention-iterations", Integer.class, 1000);
        int poolSize = environment.getProperty("jasypt.encryptor.pool-size", Integer.class, 1);
        String outputType = environment.getProperty("jasypt.encryptor.string-output-type", "base64");
        String prefix = environment.getProperty("jasypt.encryptor.property.prefix", "ENC(");
        String suffix = environment.getProperty("jasypt.encryptor.property.suffix", ")");

        return new EncryptionSettings(password, algorithm, iterations, poolSize, outputType, prefix, suffix);
    }

    public boolean hasPassword() {
        return password != null && !password.isBlank();
    }

    public String getPassword() {
        return password;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public int getKeyObtentionIterations() {
        return keyObtentionIterations;
    }

    public int getPoolSize() {
        return poolSize;
    }

    public String getStringOutputType() {
        return stringOutputType;
    }

    public String getPropertyPrefix() {
        return propertyPrefix;
    }

    public String getPropertySuffix() {
        return propertySuffix;
    }
}

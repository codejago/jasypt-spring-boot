package com.codejago.jasyptspringboot.configuration;

import com.codejago.jasyptspringboot.core.DefaultEncryptablePropertyResolver;
import com.codejago.jasyptspringboot.core.EncryptablePropertyResolver;
import com.codejago.jasyptspringboot.core.EncryptablePropertySourceConverter;
import com.codejago.jasyptspringboot.core.EncryptionSettings;
import com.codejago.jasyptspringboot.core.StringEncryptorFactory;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * Wires everything required by {@link com.codejago.jasyptspringboot.annotation.EnableEncryptableProperties}.
 */
@Configuration
public class EnableEncryptablePropertiesConfiguration {

    @Bean
    public EncryptionSettings encryptionSettings(ConfigurableEnvironment environment) {
        return EncryptionSettings.from(environment);
    }

    @Bean
    public EncryptablePropertyResolver encryptablePropertyResolver(
            ObjectProvider<StringEncryptor> encryptorProvider,
            EncryptionSettings settings
    ) {
        StringEncryptor encryptor = encryptorProvider.getIfAvailable(() -> StringEncryptorFactory.create(settings));
        return new DefaultEncryptablePropertyResolver(encryptor, settings.getPropertyPrefix(), settings.getPropertySuffix());
    }

    @Bean
    public EncryptablePropertySourceConverter encryptablePropertySourceConverter(EncryptablePropertyResolver propertyResolver) {
        return new EncryptablePropertySourceConverter(propertyResolver);
    }

    @Bean
    public static EnableEncryptablePropertiesBeanFactoryPostProcessor enableEncryptablePropertySourcesPostProcessor(
            ConfigurableEnvironment environment,
            EncryptablePropertySourceConverter converter
    ) {
        return new EnableEncryptablePropertiesBeanFactoryPostProcessor(environment, converter);
    }
}

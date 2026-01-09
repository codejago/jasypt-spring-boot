package com.codejago.jasyptspringboot.core;

import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.SystemEnvironmentPropertySource;
import org.springframework.mock.env.MockEnvironment;

import java.util.Collections;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CoreClassesCoverageTest {

    @Test
    void stringEncryptorFactory_throwsWhenNoPassword() {
        EncryptionSettings settings = EncryptionSettings.from(new MockEnvironment());
        assertThatThrownBy(() -> StringEncryptorFactory.create(settings))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Missing encryption password");
    }

    @Test
    void defaultEncryptablePropertyResolver_edgeCases() {
        StringEncryptor encryptor = mock(StringEncryptor.class);
        DefaultEncryptablePropertyResolver resolver = new DefaultEncryptablePropertyResolver(encryptor, "ENC(", ")");

        assertThat(resolver.resolvePropertyValue(null)).isNull();
        assertThat(resolver.resolvePropertyValue("not-encrypted")).isEqualTo("not-encrypted");
        assertThat(resolver.resolvePropertyValue("ENC(too-short")).isEqualTo("ENC(too-short");
        assertThat(resolver.resolvePropertyValue("no-prefix)")).isEqualTo("no-prefix)");
        
        // Trimmed check
        when(encryptor.decrypt("secret")).thenReturn("decrypted");
        assertThat(resolver.resolvePropertyValue("  ENC(secret)  ")).isEqualTo("decrypted");
    }

    @Test
    void decryptingPropertySource_nonStringValue() {
        PropertySource<Map<String, Object>> delegate = new MapPropertySource("test", Collections.singletonMap("key", 123));
        EncryptablePropertyResolver resolver = mock(EncryptablePropertyResolver.class);
        DecryptingPropertySource<Map<String, Object>> source = new DecryptingPropertySource<>(delegate, resolver);

        assertThat(source.getProperty("key")).isEqualTo(123);
    }

    @Test
    void decryptingSystemEnvironmentPropertySource_nonStringValue() {
        SystemEnvironmentPropertySource delegate = new SystemEnvironmentPropertySource("test", Collections.singletonMap("key", 123));
        EncryptablePropertyResolver resolver = mock(EncryptablePropertyResolver.class);
        DecryptingSystemEnvironmentPropertySource source = new DecryptingSystemEnvironmentPropertySource(delegate, resolver);

        assertThat(source.getProperty("key")).isEqualTo(123);
    }

    @Test
    void encryptionSettings_defaultValues() {
        ConfigurableEnvironment env = new MockEnvironment();
        EncryptionSettings settings = EncryptionSettings.from(env);

        assertThat(settings.hasPassword()).isFalse();
        assertThat(settings.getAlgorithm()).isEqualTo("PBEWITHHMACSHA512ANDAES_256");
        assertThat(settings.getKeyObtentionIterations()).isEqualTo(1000);
        assertThat(settings.getPoolSize()).isEqualTo(1);
        assertThat(settings.getStringOutputType()).isEqualTo("base64");
        assertThat(settings.getPropertyPrefix()).isEqualTo("ENC(");
        assertThat(settings.getPropertySuffix()).isEqualTo(")");
    }
    
    @Test
    void decryptingEnumerablePropertySource_nonStringValue() {
        EnumerablePropertySource<Map<String, Object>> delegate = new MapPropertySource("test", Collections.singletonMap("key", 123));
        EncryptablePropertyResolver resolver = mock(EncryptablePropertyResolver.class);
        DecryptingEnumerablePropertySource<Map<String, Object>> source = new DecryptingEnumerablePropertySource<>(delegate, resolver);

        assertThat(source.getProperty("key")).isEqualTo(123);
        assertThat(source.getPropertyNames()).containsExactly("key");
    }

    @Test
    void encryptablePropertySourceConverter_variousSources() {
        EncryptablePropertyResolver resolver = mock(EncryptablePropertyResolver.class);
        EncryptablePropertySourceConverter converter = new EncryptablePropertySourceConverter(resolver);
        MutablePropertySources propertySources = new MutablePropertySources();

        PropertySource<?> mapSource = new MapPropertySource("map", Collections.singletonMap("k1", "v1"));
        SystemEnvironmentPropertySource sysEnvSource = new SystemEnvironmentPropertySource("sysEnv", Collections.singletonMap("k2", "v2"));
        EnumerablePropertySource<?> enumerableSource = new MapPropertySource("enumerable", Collections.singletonMap("k3", "v3"));
        PropertySource<?> alreadyEncryptable = new DecryptingPropertySource<>(mapSource, resolver);

        propertySources.addLast(mapSource);
        propertySources.addLast(sysEnvSource);
        propertySources.addLast(enumerableSource);
        propertySources.addLast(alreadyEncryptable);

        converter.convertPropertySources(propertySources);

        assertThat(propertySources.contains("map")).isTrue();
        assertThat(propertySources.get("map")).isInstanceOf(DecryptingPropertySource.class);
    }
}

package com.codejago.jasyptspringboot;

import com.codejago.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
        classes = CustomEncryptorOverrideTests.TestApplication.class,
        properties = "demo.secret=ENC(terces)"
)
class CustomEncryptorOverrideTests {

    @Autowired
    private Environment environment;

    @Test
    void usesCustomEncryptorWhenPresent() {
        assertThat(environment.getProperty("demo.secret")).isEqualTo("secret");
    }

    @SpringBootApplication
    @EnableEncryptableProperties
    static class TestApplication {
        @Bean
        StringEncryptor reversingEncryptor() {
            return new StringEncryptor() {
                @Override
                public String encrypt(String message) {
                    return new StringBuilder(message).reverse().toString();
                }

                @Override
                public String decrypt(String encryptedMessage) {
                    return new StringBuilder(encryptedMessage).reverse().toString();
                }
            };
        }
    }
}

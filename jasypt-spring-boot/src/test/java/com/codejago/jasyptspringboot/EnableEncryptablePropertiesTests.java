package com.codejago.jasyptspringboot;

import com.codejago.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = EnableEncryptablePropertiesTests.TestApplication.class)
class EnableEncryptablePropertiesTests {

    private static final String PASSWORD = "unit-test-password";
    private static final String ENCRYPTED_SECRET = "ANKa2uOXcq0RaJiNbRRRC0tlWBgzHicreSpDg0aBzPU9OOOIPrANqdbbvIgsQfT7";

    @DynamicPropertySource
    static void applicationProperties(DynamicPropertyRegistry registry) {
        registry.add("jasypt.encryptor.password", () -> PASSWORD);
        registry.add("demo.secret", () -> "ENC(" + ENCRYPTED_SECRET + ")");
    }

    @Autowired
    private Environment environment;

    @Value("${demo.secret}")
    private String secretFromValue;

    @Test
    void decryptsEnvironmentValues() {
        assertThat(environment.getProperty("demo.secret")).isEqualTo("super-secret");
        assertThat(secretFromValue).isEqualTo("super-secret");
    }

    @SpringBootApplication
    @EnableEncryptableProperties
    static class TestApplication {
    }
}

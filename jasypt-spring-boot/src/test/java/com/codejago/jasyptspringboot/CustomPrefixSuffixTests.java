package com.codejago.jasyptspringboot;

import com.codejago.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
        classes = CustomPrefixSuffixTests.TestApplication.class,
        properties = {
                "jasypt.encryptor.password=" + CustomPrefixSuffixTests.PASSWORD,
                "jasypt.encryptor.property.prefix=SECRET(",
                "jasypt.encryptor.property.suffix=)"
        }
)
class CustomPrefixSuffixTests {

    static final String PASSWORD = "custom-prefix-pass";
    private static final String ENCRYPTED_VALUE = "2YFfbWDyMbnstj9wUBLV9vjBb0QeQxmzuf0xxY3f4zXCK7hxyMRhr+DLOCPsMIfT";

    @DynamicPropertySource
    static void applicationProperties(DynamicPropertyRegistry registry) {
        registry.add("demo.secret", () -> "SECRET(" + ENCRYPTED_VALUE + ")");
    }

    @Autowired
    private Environment environment;

    @Test
    void respectsCustomMarkers() {
        assertThat(environment.getProperty("demo.secret")).isEqualTo("prefixed-value");
    }

    @SpringBootApplication
    @EnableEncryptableProperties
    static class TestApplication {
    }
}

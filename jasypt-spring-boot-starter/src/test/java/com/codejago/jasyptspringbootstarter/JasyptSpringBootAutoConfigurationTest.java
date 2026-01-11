package com.codejago.jasyptspringbootstarter;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;

class JasyptSpringBootAutoConfigurationTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(JasyptSpringBootAutoConfiguration.class))
            .withPropertyValues("jasypt.encryptor.password=test");

    @Test
    void autoConfigurationIsApplied() {
        contextRunner.run(context -> {
            assertThat(context).hasSingleBean(JasyptSpringBootAutoConfiguration.class);
        });
    }
}

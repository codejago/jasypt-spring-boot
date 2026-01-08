package com.codejago.jasyptspringbootstarter;

import com.codejago.jasyptspringboot.configuration.EnableEncryptablePropertiesBeanFactoryPostProcessor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Fahim Farook
 */
public class BootstrappingJasyptConfigurationTest {

    private ConfigurableApplicationContext context;

    @AfterEach
    public void after() {
        if (this.context != null) {
            this.context.close();
        }
    }

    @Test
    public void issue_notDecryptedDuringBoostrapPhase() {
        startWith(new BaseBootstrappingTestListener() {

            @Override
            public void onApplicationEvent(final ApplicationStartedEvent event) {
                assertEquals("mypassword", event.getApplicationContext().getEnvironment().getProperty("spring.config.server.svn.password"), "ENC() value is not decrypted during bootstrap phase");
            }
        }, "--jasypt.encryptor.bootstrap=false");

        assertNotNull(this.context.getBean(EnableEncryptablePropertiesBeanFactoryPostProcessor.class));
    }

    @Test
    public void fix_decryptedDuringBoostrapPhase() {
        startWith(new BaseBootstrappingTestListener() {

            @Override
            public void onApplicationEvent(final ApplicationStartedEvent event) {
                assertEquals("mypassword", event.getApplicationContext().getEnvironment().getProperty("spring.config.server.svn.password"), "ENC() value is decrypted during bootstrap phase");
            }
        }, "--jasypt.encryptor.bootstrap=true");

        assertNotNull(this.context.getBean(EnableEncryptablePropertiesBeanFactoryPostProcessor.class));
    }

    @SuppressWarnings("rawtypes")
    private void startWith(final ApplicationListener listener, final String... args) {
        try {
            final SpringApplicationBuilder builder = new SpringApplicationBuilder(BootstrapConfig.class)
                    .profiles("subversion")
                    .properties("server.port=0")
                    .web(WebApplicationType.SERVLET);

            if (listener != null) {
                builder.listeners(listener);
            }

            this.context = builder.run(args);
        } catch (final Exception e) {
            throw new IllegalStateException(e);
        }
    }


    @Configuration
    @EnableAutoConfiguration
    static class BootstrapConfig {

    }

    static abstract class BaseBootstrappingTestListener
            implements ApplicationListener<ApplicationStartedEvent>, Ordered {

        @Override
        public int getOrder() {
            return Ordered.HIGHEST_PRECEDENCE + 100;
        }
    }

}

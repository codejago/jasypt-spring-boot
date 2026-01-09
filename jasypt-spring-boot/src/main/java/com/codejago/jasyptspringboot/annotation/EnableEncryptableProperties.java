package com.codejago.jasyptspringboot.annotation;

import com.codejago.jasyptspringboot.configuration.EnableEncryptablePropertiesConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Entry point for enabling decryptable {@link org.springframework.core.env.PropertySource}
 * instances. Add this annotation to any {@link org.springframework.context.annotation.Configuration}
 * class (typically the main {@code @SpringBootApplication}) and the library will decorate every
 * property source in the {@link org.springframework.core.env.Environment} so that encrypted values
 * (those wrapped with {@code ENC(...)} by default) are transparently decrypted before the rest of
 * Spring consumes them.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(EnableEncryptablePropertiesConfiguration.class)
public @interface EnableEncryptableProperties {
}

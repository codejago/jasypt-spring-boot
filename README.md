# Jasypt Spring Boot

**[Jasypt](http://www.jasypt.org)** integration for Spring Boot 4.x.

[![Java CI](https://github.com/codejago/jasypt-spring-boot/actions/workflows/maven.yml/badge.svg)](https://github.com/codejago/jasypt-spring-boot/actions/workflows/maven.yml)
[![Maven Central](https://img.shields.io/maven-central/v/com.codejago/jasypt-spring-boot-parent.svg)](https://central.sonatype.com/artifact/com.codejago/jasypt-spring-boot-parent)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

## Overview

This library provides seamless encryption support for Spring Boot property sources. It allows you to wrap sensitive 
values in your configuration files with `ENC(...)` and have them transparently decrypted during application bootstrap.

**Key Features:**
- **Simplified Scope:** Focused on the core `@EnableEncryptableProperties` workflow for maximum performance and maintainability.
- **Java 21+ & Spring Boot 4.x:** Optimized for modern Java ecosystems.
- **Transparent Decryption:** Automatically decorates Spring `PropertySource` objects to handle encrypted values.

---

## Requirements

- **Java:** 21 or higher
- **Spring Boot:** 4.0.1 or higher

---

## Getting Started

### 1. Add the Dependency

If you are using Spring Boot Auto-Configuration (standard for most apps), add the starter:

```xml
<dependency>
    <groupId>com.codejago</groupId>
    <artifactId>jasypt-spring-boot-starter</artifactId>
    <version>5.0.1-SNAPSHOT</version>
</dependency>
```

Alternatively, for manual control, use the core library:

```xml
<dependency>
    <groupId>com.codejago</groupId>
    <artifactId>jasypt-spring-boot</artifactId>
    <version>5.0.1-SNAPSHOT</version>
</dependency>
```

### 2. Enable Encryption

If using the starter, encryption is enabled automatically. If using the core library, add `@EnableEncryptableProperties` to your configuration:

```java
@Configuration
@EnableEncryptableProperties
public class MyConfig {
}
```

### 3. Encrypt Your Properties

Wrap your secret values in `ENC(...)` within your `application.properties` or `application.yml`:

```properties
db.password=ENC(nrmZtkF7T0kjG/VodDvBw93Ct8EgjCA+)
```

You can then access the decrypted value normally via `@Value("${db.password}")` or the `Environment` API.

---

## Configuration

The following properties can be used to configure the default `StringEncryptor`. These can be set via system properties, environment variables, or standard Spring Boot configuration.

| Property | Default Value | Description |
| :--- | :--- | :--- |
| `jasypt.encryptor.password` | **Required** | The master password used for decryption. |
| `jasypt.encryptor.algorithm` | `PBEWITHHMACSHA512ANDAES_256` | The encryption algorithm to use. |
| `jasypt.encryptor.key-obtention-iterations` | `1000` | Number of iterations for key derivation. |
| `jasypt.encryptor.pool-size` | `1` | Size of the encryptor pool. |
| `jasypt.encryptor.string-output-type` | `base64` | Output type (`base64` or `hexadecimal`). |
| `jasypt.encryptor.property.prefix` | `ENC(` | Prefix used to identify encrypted values. |
| `jasypt.encryptor.property.suffix` | `)` | Suffix used to identify encrypted values. |

> **Security Tip:** Never store `jasypt.encryptor.password` in your version-controlled configuration files. Pass it as an environment variable or system property at runtime.

---

## Customization

To use a custom `StringEncryptor`, simply define a bean in your context. The library will automatically detect it and use it instead of the default one:

```java
@Bean
public StringEncryptor myEncryptor() {
    PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
    // ... configure your encryptor
    return encryptor;
}
```

---

## Modules

- **`jasypt-spring-boot`**: The core library containing the decoration logic and `@EnableEncryptableProperties`.
- **`jasypt-spring-boot-starter`**: Auto-configuration module for easy integration.

---

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

## [1.0.2] - 2026-08-06

### Security
- Added explicit dependency management overrides to remediate known vulnerable transitive dependencies:
  - `ch.qos.logback:logback-classic` -> `1.6.1`
  - `ch.qos.logback:logback-core` -> `1.6.1`
  - `org.apache.tomcat.embed:tomcat-embed-core` -> `11.0.24`
  - `org.apache.tomcat.embed:tomcat-embed-el` -> `11.0.24`
  - `org.apache.tomcat.embed:tomcat-embed-websocket` -> `11.0.24`
  - `tools.jackson.core:jackson-databind` -> `3.2.1`
  - `org.bouncycastle:bcprov-jdk18on` -> `1.84`

### Changed
- Replaced hardcoded dependency override versions in the parent POM with centralized properties:
  - `spring.security.rsa.version`
  - `logback.version`
  - `tomcat.embed.version`
  - `jackson.databind.version`
  - `bcprov.version`
- Updated `.gitignore` to ignore Maven wrapper files/directories in this repository:
  - `mvnw.cmd`
  - `.mvn/`

### Development
- Added VS Code workspace Java settings in `.vscode/settings.json`:
  - `java.compile.nullAnalysis.mode = automatic`
  - `java.configuration.updateBuildConfiguration = automatic`

[Unreleased]: https://github.com/codejago/jasypt-spring-boot/compare/v1.0.2...HEAD
[1.0.2]: https://github.com/codejago/jasypt-spring-boot/releases/tag/v1.0.2

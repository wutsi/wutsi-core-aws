![](https://github.com/wutsi/wutsi-core-aws/workflows/build/badge.svg)
[![](https://img.shields.io/codecov/c/github/wutsi/wutsi-core-aws/master.svg)](https://codecov.io/gh/wutsi/wutsi-core-aws)
![JDK](https://img.shields.io/badge/jdk-1.8-brightgreen.svg)
![](https://img.shields.io/badge/language-kotlin-blue.svg)

# About
This is a library is an extension of [wutsi-core](https://github.com/wutsi/wutsi-core) that provide solutions specific to Amazon AWS.

# Features
Here are the problems this library solve:
- Springboot AWS health checks:
  - [S3 Health check](https://github.com/wutsi/wutsi-core-aws/blob/master/src/main/kotlin/com/wutsi/core/aws/health/S3HealthIndicator.kt)
- How to manage file from S3. See [AwsStorageService](https://github.com/wutsi/wutsi-core-aws/blob/master/src/main/kotlin/com/wutsi/core/aws/service/AwsStorageService.kt)
 
# Dependencies
- com.amazonawsaws-java-sdk
- com.wutsi.wusti-core (scope: provided)
- org.springframework.boot.spring-boot-starter-actuator (scope: provided)
- org.springframework.spring-context (scope: provided)
- org.slf4j.slf4j-api (scope: provided)

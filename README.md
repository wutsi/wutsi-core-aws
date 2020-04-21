![](https://github.com/wutsi/wutsi-core-aws/workflows/build/badge.svg)
[![](https://img.shields.io/codecov/c/github/wutsi/wutsi-core-aws/master.svg)](https://codecov.io/gh/wutsi/wutsi-core-aws)
![JDK](https://img.shields.io/badge/jdk-1.8-brightgreen.svg)
![](https://img.shields.io/badge/language-kotlin-blue.svg)

# About
This is a library is an extension of [wutsi-core](https://github.com/wutsi/wutsi-core) that provide solutions specific to Amazon AWS.

# Features
Here are the problems this library solve:
- How to integrate Springboot health checks for AWS services?
  - [S3 Health check](https://github.com/wutsi/wutsi-core-aws/blob/master/src/main/kotlin/com/wutsi/core/aws/health/S3HealthIndicator.kt)
- How to manage file from S3. See [S3StorageService](https://github.com/wutsi/wutsi-core-aws/blob/master/src/main/kotlin/com/wutsi/core/aws/service/AwsStorageService.kt)
 
# Usage
```xml
<dependency>
    <groupId>com.wutsi</groupId>
    <artifactId>wutsi-core-aws</artifactId>
    <version>[LATEST VERSION]</version>
</dependency>
```

See package details [here](https://github.com/wutsi/wutsi-core-aws/packages)

# Dependencies
- com.amazonawsaws-java-sdk-s3
- com.wutsi.wusti-core (scope: provided)
- org.springframework.boot.spring-boot-starter-actuator (scope: provided)
- org.springframework.spring-context (scope: provided)
- org.slf4j.slf4j-api (scope: provided)

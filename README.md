`wutsi-core-aws` is an extension of [wutsi-core](https://github.com/wutsi/wutsi-core) for Amazon AWS.

![](https://github.com/wutsi/wutsi-core-aws/workflows/build/badge.svg)
![JDK](https://img.shields.io/badge/jdk-1.8-brightgreen.svg)
![](https://img.shields.io/badge/language-kotlin-blue.svg)

# Prerequisites
- JDK 1.8
- Maven 3.6+ 

# Usage
```xml
<dependency>
    <groupId>com.wutsi</groupId>
    <artifactId>wutsi-core-aws</artifactId>
    <version>[LATEST VERSION]</version>
</dependency>
```

Package available [here](https://github.com/wutsi/wutsi-core-aws/packages)

# Features
## Storage
- [S3StorageService](https://github.com/wutsi/wutsi-core-aws/blob/master/src/test/kotlin/com/wutsi/core/aws/service/S3StorageService.kt)
implementation of [StorageService](https://github.com/wutsi/wutsi-core/blob/master/src/main/kotlin/com/wutsi/core/storage/StorageService.kt)
that uses [Amazon S3](https://aws.amazon.com/s3/) for storage.
- [S3HealthIndicator](https://github.com/wutsi/wutsi-core-aws/blob/master/src/test/kotlin/com/wutsi/core/aws/health/S3HealthIndicator.kt)
is a Spring Healthcheck Indicator to check access to Amazon S3.
- AWS policy required:
  - `arn:aws:iam::aws:policy/AmazonS3FullAccess`

## Caching
- [MemcacheService](https://github.com/wutsi/wutsi-core-aws/blob/master/src/test/kotlin/com/wutsi/core/aws/service/MemcacheService.kt)
implementation of [CacheService](https://github.com/wutsi/wutsi-core/blob/master/src/main/kotlin/com/wutsi/core/cache/CacheService.kt)
that uses Memcached for caching.
- [MemcacheHealthIndicator](https://github.com/wutsi/wutsi-core-aws/blob/master/src/test/kotlin/com/wutsi/core/aws/health/MemcacheHealthIndicator.kt)
is a Spring Healthcheck Indicator to check access to Memcached.
- AWS policy required:
  - `arn:aws:iam::aws:policy/AmazonElastiCacheFullAccess`

## Translation
- [AwsTranslateService](https://github.com/wutsi/wutsi-core-aws/blob/master/src/test/kotlin/com/wutsi/core/aws/service/AwsTranslateServiceTest.kt)
Implementation of [TranslateService](https://github.com/wutsi/wutsi-core/blob/master/src/main/kotlin/com/wutsi/core/translate/TranslateService.kt)
based on [Amazon Translate](https://aws.amazon.com/translate/)
- [AmazonTranslateHealthIndicatorTest](https://github.com/wutsi/wutsi-core-aws/blob/master/src/test/kotlin/com/wutsi/core/aws/health/AmazonTranslateHealthIndicatorTest.kt)
is a Spring Healthcheck Indicator to check access to Amazon Translate.
- AWS policy required:
  - `arn:aws:iam::aws:policy/TranslateFullAccess`

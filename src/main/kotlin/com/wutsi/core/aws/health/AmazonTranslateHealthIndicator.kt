package com.wutsi.core.aws.health

import com.amazonaws.services.translate.AmazonTranslate
import com.amazonaws.services.translate.model.TranslateTextRequest
import org.springframework.boot.actuate.health.Health
import org.springframework.boot.actuate.health.HealthIndicator

open class AmazonTranslateHealthIndicator(
        private val translate: AmazonTranslate
) : HealthIndicator {

    override fun health(): Health {
        val start = System.currentTimeMillis()
        try {
            translate.translateText(createRequest())
            return Health.up()
                    .withDetail("latency", System.currentTimeMillis() - start)
                    .build()
        } catch (ex: Exception){
            return Health.down()
                    .withDetail("latency", System.currentTimeMillis() - start)
                    .withException(ex)
                    .build()
        }
    }

    fun createRequest() = TranslateTextRequest()
            .withText("Exemple de texte a traduire")
            .withTargetLanguageCode("en")
            .withSourceLanguageCode("fr")
}

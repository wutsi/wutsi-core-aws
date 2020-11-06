package com.wutsi.core.aws.service

import com.amazonaws.services.translate.AmazonTranslate
import com.amazonaws.services.translate.model.TranslateTextRequest
import com.wutsi.core.translate.TranslateService


open class AwsTranslateService(
        private val translate: AmazonTranslate
) : TranslateService {

    override fun translate(fromLanguage: String?, toLanguage: String, text: String): String {
        if (text.trim().isEmpty()) {
            return ""
        }

        val request = TranslateTextRequest()
                .withText(text)
                .withSourceLanguageCode(fromLanguage ?: "auto")
                .withTargetLanguageCode(toLanguage)
        val result = translate.translateText(request)
        return result.translatedText
    }
}

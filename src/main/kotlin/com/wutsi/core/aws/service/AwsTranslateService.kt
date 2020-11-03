package com.wutsi.core.aws.service

import com.amazonaws.services.translate.AmazonTranslate
import com.amazonaws.services.translate.model.TranslateTextRequest
import com.wutsi.core.translate.TranslateService


open class AwsTranslateService(
        private val translate: AmazonTranslate
) : TranslateService {

    override fun translate(fromLanguage: String?, toLanguage: String, text: String): String {
        val request = TranslateTextRequest()
                .withText(text)
                .withSourceLanguageCode(fromLanguage)
                .withTargetLanguageCode(toLanguage)
        val result = translate.translateText(request)
        return result.translatedText
    }
}

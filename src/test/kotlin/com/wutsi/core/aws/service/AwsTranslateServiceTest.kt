package com.wutsi.core.aws.service

import com.amazonaws.services.s3.model.PutObjectRequest
import com.amazonaws.services.translate.AmazonTranslate
import com.amazonaws.services.translate.model.TranslateTextRequest
import com.amazonaws.services.translate.model.TranslateTextResult
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AwsTranslateServiceTest {
    @Mock lateinit var aws: AmazonTranslate
    @Mock lateinit var translation: TranslateTextResult
    @InjectMocks lateinit var service: AwsTranslateService

    @Test
    fun translate () {
        `when`(translation.translatedText).thenReturn("Foo bar")
        `when`(aws.translateText(
                TranslateTextRequest()
                        .withSourceLanguageCode("en")
                        .withTargetLanguageCode("fr")
                        .withText("Yo man")
        )).thenReturn(translation)

        val result = service.translate("en", "fr", "Yo man")

        val request: ArgumentCaptor<TranslateTextRequest> = ArgumentCaptor.forClass(TranslateTextRequest::class.java)
        verify(aws).translateText(request.capture())

        assertEquals("en", request.value.sourceLanguageCode)
        assertEquals("fr", request.value.targetLanguageCode)
        assertEquals("Yo man", request.value.text)

        assertEquals("Foo bar", result)
    }

    @Test
    fun translateAutoDetect () {
        `when`(translation.translatedText).thenReturn("Foo bar")
        `when`(aws.translateText(
                TranslateTextRequest()
                        .withSourceLanguageCode("auto")
                        .withTargetLanguageCode("fr")
                        .withText("Yo man")
        )).thenReturn(translation)

        val result = service.translate(null, "fr", "Yo man")

        val request: ArgumentCaptor<TranslateTextRequest> = ArgumentCaptor.forClass(TranslateTextRequest::class.java)
        verify(aws).translateText(request.capture())

        assertEquals("auto", request.value.sourceLanguageCode)
        assertEquals("fr", request.value.targetLanguageCode)
        assertEquals("Yo man", request.value.text)

        assertEquals("Foo bar", result)
    }

    @Test
    fun translateEmpty () {
        val result = service.translate(null, "fr", "  ")

        assertEquals("", result)
    }
}

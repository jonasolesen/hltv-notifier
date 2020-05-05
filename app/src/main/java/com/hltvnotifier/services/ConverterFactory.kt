package com.hltvnotifier.services

import com.hltvnotifier.models.JsoupResource
import okhttp3.ResponseBody
import org.jsoup.Jsoup
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

class JsoupConverterFactory<T : JsoupResource>(private val resource: KClass<T>) :
    Converter.Factory() {

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, T>? {
        if (type.hashCode() != resource.hashCode()) return null

        return JsoupResourceConverter(resource)
    }

    class JsoupResourceConverter<T : JsoupResource>(private val resource: KClass<T>) :
        Converter<ResponseBody, T> {
        override fun convert(response: ResponseBody): T {
            val document = Jsoup.parse(response.string())

            return resource.primaryConstructor?.call(document) as T
        }
    }
}
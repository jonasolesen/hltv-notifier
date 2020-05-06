package com.hltvnotifier.services

import com.hltvnotifier.parsers.ResourceParser
import okhttp3.ResponseBody
import org.jsoup.Jsoup
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class JsoupConverterFactory<T>(private val parser: ResourceParser<T>) :
    Converter.Factory() {

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, T>? {
        if (type.hashCode() != parser.clazz.hashCode()) return null

        return JsoupResourceConverter(parser)
    }

    class JsoupResourceConverter<T>(private val parser: ResourceParser<T>) :
        Converter<ResponseBody, T> {
        override fun convert(response: ResponseBody): T {
            val document = Jsoup.parse(response.string())

            return parser.fromDocument(document)
        }
    }
}
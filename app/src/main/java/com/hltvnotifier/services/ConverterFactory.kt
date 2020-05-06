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
        var parseMultiple = false
        if (!type.toString().contains(parser.clazz.name)) return null
        if (type.toString().contains("java.util.List")) parseMultiple = true

        return JsoupResourceConverter(parser, parseMultiple)
    }

    class JsoupResourceConverter<T>(
        private val parser: ResourceParser<T>,
        private val parseMultiple: Boolean = false
    ) : Converter<ResponseBody, T> {
        override fun convert(response: ResponseBody): T {
            val document = Jsoup.parse(response.string())
            if (parseMultiple) return parser.parseMultiple(document) as T

            return parser.parse(document)
        }
    }
}
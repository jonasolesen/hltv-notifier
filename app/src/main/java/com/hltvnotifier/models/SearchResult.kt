package com.hltvnotifier.models

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.TypeAdapter
import com.google.gson.TypeAdapterFactory
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter

object SearchResult {
    data class Team(
        @SerializedName("id")
        val id: Int?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("flagUrl")
        val flagUrl: String?,
        @SerializedName("teamLogo")
        val logoUrl: String?,
        @SerializedName("location")
        val location: String?
    ) {
        object Deserializer : TypeAdapterFactory {
            override fun <T> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T>? {
                val delegate = gson.getDelegateAdapter(this, type)
                val elementAdapter = gson.getAdapter(JsonElement::class.java)

                return object : TypeAdapter<T>() {
                    override fun read(input: JsonReader): T {
                        var json = elementAdapter.read(input)

                        if (json.isJsonArray) {
                            val result = json.asJsonArray.get(0).asJsonObject
                            json = result.get("teams")
                        }

                        return delegate.fromJsonTree(json)
                    }

                    override fun write(out: JsonWriter, value: T) {
                        delegate.write(out, value)
                    }
                }
            }
        }
    }
}
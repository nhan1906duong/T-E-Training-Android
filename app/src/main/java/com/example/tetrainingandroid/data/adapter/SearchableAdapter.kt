package com.example.tetrainingandroid.data.adapter

import com.example.tetrainingandroid.data.model.Movie
import com.example.tetrainingandroid.data.model.People
import com.example.tetrainingandroid.data.model.Searchable
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchableAdapter @Inject constructor(
    private val moshi: Moshi
): JsonAdapter<Searchable>() {
    @Synchronized
    override fun  fromJson(reader: JsonReader): Searchable? {
        when(reader.peek()) {
            JsonReader.Token.BEGIN_OBJECT -> {
                val map = reader.readJsonValue() as? Map<String, Any?>
                if (map.isNullOrEmpty()) return null
                return if (map.containsKey("media_type") && map["media_type"] is String) {
                    when(map["media_type"]) {
                        "person" -> moshi.adapter(People::class.java).fromJsonValue(map)
                        "movie" -> moshi.adapter(Movie::class.java).fromJsonValue(map)
                        else -> null
                    }
                } else {
                    null
                }
            }
            else -> throw IOException("Should return object")
        }
    }

    @Synchronized
    override fun toJson(writer: JsonWriter, value: Searchable?) {

    }
}
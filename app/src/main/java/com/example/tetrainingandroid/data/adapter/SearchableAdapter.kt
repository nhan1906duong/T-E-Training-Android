package com.example.tetrainingandroid.data.adapter

import com.example.tetrainingandroid.data.model.Searchable
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchableAdapter @Inject constructor(): JsonAdapter<Searchable>() {
    override fun fromJson(reader: JsonReader): Searchable? {
        TODO("Not yet implemented")
    }

    override fun toJson(writer: JsonWriter, value: Searchable?) {
        TODO("Not yet implemented")
    }
}
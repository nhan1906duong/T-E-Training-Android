package com.example.tetrainingandroid.data.adapter

import com.squareup.moshi.*
import java.text.SimpleDateFormat
import java.util.*

class DateTimeAdapter: JsonAdapter<Date>() {
    companion object {
        const val ISO_DATE_TIME_FORMAT: String = "yyyy-MM-dd HH:mm:ss"
    }

    private val formatter: SimpleDateFormat = SimpleDateFormat(ISO_DATE_TIME_FORMAT, Locale.getDefault()).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }

    override fun fromJson(reader: JsonReader): Date? {
        try {
            val dateAsString = reader.nextString()
            synchronized(formatter) {
                return formatter.parse(dateAsString)
            }
        } catch (e: Exception) {
            return null
        }
    }

    override fun toJson(writer: JsonWriter, value: Date?) {
        if (value == null) {
            writer.nullValue()
        } else {
            synchronized(formatter) {
                val formatter = formatter.format(value)
                writer.value(formatter)
            }
        }
    }

}
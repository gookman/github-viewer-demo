package dk.bluebox.demo.githubviewer.common.network.json

import com.squareup.moshi.*
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

class LocalDateTimeAdapter : JsonAdapter<LocalDateTime>() {
    @FromJson
    override fun fromJson(reader: JsonReader): LocalDateTime? {
        //DateTimeFormatter.ofPattern("yyyy-MM-dd´T´HH:mm:ss")
        val dateValue = reader.nextString()

        return LocalDateTime.parse(dateValue, DateTimeFormatter.ISO_DATE_TIME)
    }

    @ToJson
    override fun toJson(writer: JsonWriter, value: LocalDateTime?) {
        val stringValue = value?.format(DateTimeFormatter.ISO_DATE_TIME)
        if (stringValue == null) {
            writer.nullValue()
        } else {
            writer.value(stringValue)
        }
    }
}
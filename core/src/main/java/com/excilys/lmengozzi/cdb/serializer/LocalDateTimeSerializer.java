package com.excilys.lmengozzi.cdb.serializer;

import java.time.format.DateTimeFormatter;

import java.io.IOException;
import java.time.LocalDateTime;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * This classcan be used with Jackson to serialize a LocalDateTime in json.
 * An attribute can be annotated with
 * {@literal @}JsonSerialize(using = LocalDateTimeSerializer.class)
 * This will make Jackson use this serializer to serialize the attribute.
 */
public class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

	@Override
	public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider provider)
			throws IOException {
		gen.writeString(value.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
	}
}

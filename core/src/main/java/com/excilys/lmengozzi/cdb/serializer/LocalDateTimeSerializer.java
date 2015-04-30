package com.excilys.lmengozzi.cdb.serializer;

import java.time.format.DateTimeFormatter;

import java.io.IOException;
import java.time.LocalDateTime;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

	@Override
	public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider unusedArg)
			throws IOException {
		gen.writeString(value.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
	}
}

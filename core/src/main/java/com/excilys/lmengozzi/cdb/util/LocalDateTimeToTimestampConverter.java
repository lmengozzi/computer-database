package com.excilys.lmengozzi.cdb.util;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Converter(autoApply = true)
public class LocalDateTimeToTimestampConverter implements AttributeConverter<LocalDateTime, Timestamp> {
	@Override
	public Timestamp convertToDatabaseColumn(LocalDateTime entityAttribute) {
		if (entityAttribute == null) {
			return null;
		}
		return Timestamp.valueOf(entityAttribute);
	}
	@Override
	public LocalDateTime convertToEntityAttribute(Timestamp column) {
		if (column == null) {
			return null;
		}
		return column.toLocalDateTime();
	}
}
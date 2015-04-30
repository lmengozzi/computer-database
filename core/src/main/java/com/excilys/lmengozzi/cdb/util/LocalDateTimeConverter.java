package com.excilys.lmengozzi.cdb.util;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.persistence.Converter;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.usertype.EnhancedUserType;

/** @see http://blog.progs.be/550/java-time-hibernate */
@Deprecated
@Converter(autoApply = true)
public class LocalDateTimeConverter implements EnhancedUserType, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private static final int[] SQL_TYPES = new int[] { Types.TIMESTAMP };

	@Override
	public int[] sqlTypes() {
		return SQL_TYPES;
	}

	@Override
	public Class returnedClass() {
		return LocalDateTime.class;
	}

	@Override
	public boolean equals(Object x, Object y) throws HibernateException {
		if (x == y) {
			return true;
		}
		if (x == null || y == null) {
			return false;
		}
		final LocalDateTime dtx = (LocalDateTime) x;
		final LocalDateTime dty = (LocalDateTime) y;
		return dtx.equals(dty);
	}

	@Override
	public int hashCode(Object object) throws HibernateException {
		return object.hashCode();
	}

	@Override
	public Object nullSafeGet(ResultSet resultSet, String[] names,
							  SessionImplementor session, Object owner)
			throws HibernateException, SQLException {
		final Object timestamp = StandardBasicTypes.TIMESTAMP.nullSafeGet(
				resultSet, names, session, owner);
		if (timestamp == null) {
			return null;
		}
		final Date ts = (Date) timestamp;
		final Instant instant = Instant.ofEpochMilli(ts.getTime());
		return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
	}

	@Override
	public void nullSafeSet(PreparedStatement preparedStatement, Object value,
							int index, SessionImplementor session) throws HibernateException,
			SQLException {
		if (value == null) {
			StandardBasicTypes.TIMESTAMP.nullSafeSet(preparedStatement, null,
					index, session);
		} else {
			final LocalDateTime ldt = ((LocalDateTime) value);
			final Instant instant = ldt.atZone(ZoneId.systemDefault())
					.toInstant();
			final Date timestamp = Date.from(instant);
			StandardBasicTypes.TIMESTAMP.nullSafeSet(preparedStatement,
					timestamp, index, session);
		}
	}

	@Override
	public Object deepCopy(Object value) throws HibernateException {
		return value;
	}

	@Override
	public boolean isMutable() {
		return false;
	}

	@Override
	public Serializable disassemble(Object value) throws HibernateException {
		return (Serializable) value;
	}

	@Override
	public Object assemble(Serializable cached, Object value)
			throws HibernateException {
		return cached;
	}

	@Override
	public Object replace(Object original, Object target, Object owner)
			throws HibernateException {
		return original;
	}

	@Override
	public String objectToSQLString(Object object) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String toXMLString(Object object) {
		return object.toString();
	}

	@Override
	public Object fromXMLString(String string) {
		return LocalDateTime.parse(string);
	}

}

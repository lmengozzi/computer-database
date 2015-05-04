package com.excilys.lmengozzi.cdb.entity;

import com.excilys.lmengozzi.cdb.serializer.LocalDateTimeDeserializer;
import com.excilys.lmengozzi.cdb.serializer.LocalDateTimeSerializer;
import com.excilys.lmengozzi.cdb.util.LocalDateTimeToTimestampConverter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity(name = "computer")
public class Computer implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	protected long id;

	@Column(name = "name")
	protected String name;

	@Convert(converter = LocalDateTimeToTimestampConverter.class)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@Column(name = "introduced", nullable = true)
	protected LocalDateTime introduced;

	@Convert(converter = LocalDateTimeToTimestampConverter.class)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@Column(name = "discontinued", nullable = true)
	protected LocalDateTime discontinued;

	@ManyToOne
	@Cascade(CascadeType.DELETE)
	@JoinColumn(name = "company_id")
	protected Company company;

	protected Computer() { }

	public static class Builder {
		private Computer computer;

		public Builder() {
			computer = new Computer();
		}

		public Builder id(long id) {
			computer.id = id;
			return this;
		}

		public Builder name(String name) {
			computer.name = name;
			return this;
		}

		public Builder introduced(LocalDateTime introduced) {
			computer.introduced = introduced;
			return this;
		}

		public Builder discontinued(LocalDateTime discontinued) {
			computer.discontinued = discontinued;
			return this;
		}

		public Builder company(Company company) {
			computer.company = company;
			return this;
		}

		public Computer build() {
			return computer;
		}
	}

	public static Builder builder() {
		return new Builder();
	}

	@Override
	public String toString() {

		StringBuffer buffer = new StringBuffer();
		buffer.append("Computer name: ");
		buffer.append(name);
		if (introduced != null) {
			buffer.append(", introduced: ");
			buffer.append(introduced);
		}
		if (discontinued != null) {
			buffer.append(", discontinued: ");
			buffer.append(discontinued);
		}
		if (company != null && company.getName() != null) {
			buffer.append(", manufacturer: ");
			buffer.append(company);
		}

		return buffer.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Computer)) return false;

		Computer computer = (Computer) o;

		if (id != computer.id) return false;
		if (!name.equals(computer.name)) return false;
		if (introduced != null ? !introduced.equals(computer.introduced) : computer.introduced != null) return false;
		if (discontinued != null ? !discontinued.equals(computer.discontinued) : computer.discontinued != null)
			return false;
		return !(company != null ? !company.equals(computer.company) : computer.company != null);
	}

	@Override
	public int hashCode() {
		int result = (int) (id ^ (id >>> 32));
		result = 31 * result + name.hashCode();
		result = 31 * result + (introduced != null ? introduced.hashCode() : 0);
		result = 31 * result + (discontinued != null ? discontinued.hashCode() : 0);
		result = 31 * result + (company != null ? company.hashCode() : 0);
		return result;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public LocalDateTime getIntroduced() {
		return introduced;
	}

	public void setIntroduced(LocalDateTime introducedDate) {
		this.introduced = introducedDate;
	}

	public LocalDateTime getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(LocalDateTime discontinuedDate) {
		this.discontinued = discontinuedDate;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company manufacturer) {
		this.company = manufacturer;
	}
}

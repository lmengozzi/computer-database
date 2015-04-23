package com.excilys.lmengozzi.cdb.persistence.entity;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity(name = "computer")
public class Computer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	protected long id;

	protected String name;

	@Type(type = "com.excilys.lmengozzi.cdb.persistence.util.LocalDateTimeConverter")
	@Column(name = "introduced", nullable = true)
	protected LocalDateTime introduced;

	@Type(type = "com.excilys.lmengozzi.cdb.persistence.util.LocalDateTimeConverter")
	@Column(name = "discontinued", nullable = true)
	protected LocalDateTime discontinued;

	@ManyToOne
	@Cascade(CascadeType.DELETE)
	@JoinColumn(name = "company_id")
	protected Company company;

	public Computer() {

	}

	public Computer(String name) {
		this.name = name;
	}

	public Computer(long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Computer(String name, LocalDateTime introduced,
					LocalDateTime discontinued, Company manufacturer) {
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.company = manufacturer;
	}

	public Computer(long id, String name, LocalDateTime introduced,
					LocalDateTime discontinued, Company manufacturer) {
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.company = manufacturer;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

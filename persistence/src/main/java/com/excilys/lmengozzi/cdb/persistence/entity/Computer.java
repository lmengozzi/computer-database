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
	protected LocalDateTime introducedDate;

	@Type(type = "com.excilys.lmengozzi.cdb.persistence.util.LocalDateTimeConverter")
	@Column(name = "discontinued", nullable = true)
	protected LocalDateTime discontinuedDate;

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
	
	public Computer(String name, LocalDateTime introducedDate,
			LocalDateTime discontinuedDate, Company manufacturer) {
		this.name = name;
		this.introducedDate = introducedDate;
		this.discontinuedDate = discontinuedDate;
		this.company = manufacturer;
	}
	
	public Computer(long id, String name, LocalDateTime introducedDate,
			LocalDateTime discontinuedDate, Company manufacturer) {
		this.id = id;
		this.name = name;
		this.introducedDate = introducedDate;
		this.discontinuedDate = discontinuedDate;
		this.company = manufacturer;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) throws Exception {
		if (!name.equals(""))
			this.name = name;
		else
			throw new Exception("Computer name must not be null");
	}
	
	@Override
	public String toString() {

		StringBuffer buffer = new StringBuffer();
		buffer.append("Computer name: ");
		buffer.append(name);
		if (introducedDate != null) {
			buffer.append(", introduced: ");
			buffer.append(introducedDate);
		}
		if (discontinuedDate != null) {
			buffer.append(", discontinued: ");
			buffer.append(discontinuedDate);
		}
		if (company != null) {
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

	public LocalDateTime getIntroducedDate() {
		return introducedDate;
	}

	public void setIntroducedDate(LocalDateTime introducedDate) {
		this.introducedDate = introducedDate;
	}

	public LocalDateTime getDiscontinuedDate() {
		return discontinuedDate;
	}

	public void setDiscontinuedDate(LocalDateTime discontinuedDate) {
		this.discontinuedDate = discontinuedDate;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company manufacturer) {
		this.company = manufacturer;
	}
}

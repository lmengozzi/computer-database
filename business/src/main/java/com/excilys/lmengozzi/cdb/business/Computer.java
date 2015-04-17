package com.excilys.lmengozzi.cdb.business;

import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import java.time.LocalDateTime;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

@Entity(name = "computer")
public class Computer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	protected long id;

	@Size(min = 1, max = 256)
	protected String name;

	protected LocalDateTime introducedDate;

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

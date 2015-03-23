package com.excilys.lmengozzi.cdb.business;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

public class Computer {

	@NotNull
	protected long id;
	@NotNull
	@Size(min = 1, max = 256)
	protected String name;
	@Past
	protected LocalDateTime introducedDate;
	@Past
	protected LocalDateTime discontinuedDate;
	@Size(min = 0, max = 256)
	protected String manufacturer;
	
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
			LocalDateTime discontinuedDate, String manufacturer) {
		this.name = name;
		this.introducedDate = introducedDate;
		this.discontinuedDate = discontinuedDate;
		this.manufacturer = manufacturer;
	}
	
	public Computer(long id, String name, LocalDateTime introducedDate,
			LocalDateTime discontinuedDate, String manufacturer) {
		this.id = id;
		this.name = name;
		this.introducedDate = introducedDate;
		this.discontinuedDate = discontinuedDate;
		this.manufacturer = manufacturer;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) throws Exception {
		if (name != "")
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
		if (manufacturer != null) {
			buffer.append(", manufacturer: ");
			buffer.append(manufacturer);
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

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
}

package com.excilys.lmengozzi.cdb.webservice;

import com.excilys.lmengozzi.cdb.business.service.CompanyService;
import com.excilys.lmengozzi.cdb.entity.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import java.io.Serializable;
import java.util.List;

@Component
@Path("/company")
public class CompanyRESTController implements Serializable {

	@Autowired
	private CompanyService companyService;

	// TRACE would be more appropriate but the annotation doesn't exist in javax.ws.rs api
	@HEAD
	@Path("/ping")
	public String ping() {
		return "PING From CompanyRESTController";
	}

	@GET
	@Path("/all")
	@Produces("application/json")
	public List<Company> findAll() {
		return companyService.findAll();
	}

	@GET
	@Path("/{id}")
	@Produces("application/json")
	public Company findById(@PathParam("id") long id) {
		return companyService.findById(id);
	}

	@GET
	@Path("/byName")
	@Produces("application/json")
	public Company findByName(@QueryParam("name") String name) {
		return companyService.findByName(name);
	}

	@PUT
	@Path("/")
	@Consumes("application/json")
	@Produces("application/json")
	public long create(Company object) {
		return companyService.create(object);
	}

	@GET
	@Path("/count")
	@Produces("application/json")
	public long getCount() {
		return companyService.getCount();
	}

	@DELETE
	@Path("/")
	@Consumes("application/json")
	public void delete(long id) {
		companyService.delete(id);
	}
}

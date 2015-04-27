package com.excilys.lmengozzi.cdb.webservice;

import com.excilys.lmengozzi.cdb.persistence.entity.Computer;
import com.excilys.lmengozzi.cdb.persistence.service.ComputerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import java.util.List;

@Component
@Path("/computer")
public class ComputerRESTController {

	@Autowired
	private ComputerService computerService;

	@GET
	@Path("/ping")
	public String ping() {
		return "PING From ComputerRESTController";
	}

	@GET
	@Path("/all")
	@Produces("application/json")
	public List<Computer> findAll() {
		return computerService.findAll();
	}

	@GET
	@Path("/")
	@Produces("application/json")
	public List<Computer> findPage(@QueryParam("page") int page,
								   @QueryParam("pageSize") int pageSize,
								   @QueryParam("orderBy") String orderBy,
								   @QueryParam("ascending") boolean ascending,
								   @QueryParam("search") String search) {
		return computerService.findPage(page, pageSize, orderBy, ascending, search);
	}

	@GET
	@Path("/{id}")
	@Produces("application/json")
	public Computer findById(@PathParam("id") long id) {
		return computerService.findById(id);
	}

	@PUT
	@Path("/")
	@Consumes("application/json")
	@Produces("application/json")
	public void create(Computer object) {
		computerService.create(object);
	}

	@GET
	@Produces("application/json")
	public long getCount() {
		return computerService.getCount();
	}

	@DELETE
	@Consumes("application/json")
	public void delete(long id) {
		computerService.delete(id);
	}
}

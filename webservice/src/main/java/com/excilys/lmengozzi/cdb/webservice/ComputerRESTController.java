package com.excilys.lmengozzi.cdb.webservice;

import com.excilys.lmengozzi.cdb.business.service.ComputerService;
import com.excilys.lmengozzi.cdb.entity.Computer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@Path("/computer")
public class ComputerRESTController {

	@Autowired
	private ComputerService computerService;

	// TRACE would be more appropriate but the annotation doesn't exist in javax.ws.rs api
	@GET
	@Path("/ping")
	@Produces("application/json")
	public String[] ping() {
		List<String> list = new ArrayList<>();
		list.add("1");
		list.add("2");
		return (String[]) list.toArray();
	}

	@GET
	@Path("/all")
	@Produces("application/json")
	public List<Computer> findAll() {
		return computerService.findAll();
	}

	@GET
	@Path("/page")
	@Produces("application/json")
	public String[] findPage(@QueryParam("number") int page,
							 @QueryParam("pageSize") int pageSize,
							 @QueryParam("orderBy") String orderBy,
							 @QueryParam("ascending") boolean ascending,
							 @QueryParam("search") String search) {
		if(orderBy == null)
			orderBy = "id";
		if(search == null)
			search = "";

		return (String[]) computerService.findPage(page, pageSize, orderBy, ascending, search).toArray();
	}

	@GET
	@Path("/id={id}")
	@Produces("application/json")
	public Computer findById(@PathParam("id") long id) {
		return computerService.findById(id);
	}

	@PUT
	@Path("/")
	@Consumes("application/json")
	@Produces("application/json")
	public long create(Computer object) {
		return computerService.create(object);
	}

	@GET
	@Path("/count")
	@Produces("application/json")
	public long getCount() {
		return computerService.getCount();
	}

	@DELETE
	@Path("/")
	@Consumes("application/json")
	public void delete(long id) {
		computerService.delete(id);
	}
}

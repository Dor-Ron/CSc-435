package csc435.moocme.a6.resources;

import csc435.moocme.a6.api.Mooc;
import csc435.moocme.a6.dao.CoursesDAO;

import com.codahale.metrics.annotation.Timed;

import org.jdbi.v3.core.Jdbi;

import javax.ws.rs.GET;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import java.util.concurrent.atomic.AtomicLong;
import java.util.Optional;

@Path("/courses")
@Produces(MediaType.APPLICATION_JSON)
public class CoursesResource {

    private final CoursesDAO cDAO;

    public CoursesResource(Jdbi db) {
        cDAO = db.onDemand(CoursesDAO.class);
    }

    @GET
    @Timed
    public Response index(@QueryParam("free") Optional<String> price) {
        if (price.isPresent() && price.equals(Optional.of("true"))) 
            return Response.ok(cDAO.getFree()).build();
        return Response.ok(cDAO.getAll()).build();
    }
}
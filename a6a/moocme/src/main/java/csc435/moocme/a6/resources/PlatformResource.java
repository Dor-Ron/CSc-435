package csc435.moocme.a6.resources;

import csc435.moocme.a6.api.Mooc;
import csc435.moocme.a6.dao.CoursesDAO;

import com.codahale.metrics.annotation.Timed;

import org.jdbi.v3.core.Jdbi;


import javax.ws.rs.core.Context;
import javax.ws.rs.GET;
import java.io.IOException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.servlet.http.HttpServletRequest;


import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.*;

import java.util.concurrent.atomic.AtomicLong;
import java.util.Optional;

@Path("/courses/{platform}")
@Produces(MediaType.APPLICATION_JSON)
public class PlatformResource {

    private final CoursesDAO cDAO;

    public PlatformResource(Jdbi db) {
        cDAO = db.onDemand(CoursesDAO.class);
    }

    @GET
    @Timed
    public Response doGet(@QueryParam("free") Optional<String> price, @PathParam("platform") String plat) {
        String platforma = plat.toLowerCase();
        if (platforma.equals("coursera") || platforma.equals("edx") || platforma.equals("udacity")) {
            if (price.isPresent() && price.equals(Optional.of("true"))) 
                return Response.ok(cDAO.getPlatFreeCourses(platforma)).build();
            return Response.ok(cDAO.getPlatCourses(platforma)).build();
        } return Response.status(404).build();
    }

    @POST
    @Timed
    public Response doPost(@Context final HttpServletRequest req, 
                           @QueryParam("authenticated") Optional<String> auth, 
                           @PathParam("platform") String plat) throws IOException {
        String platforma = plat.toLowerCase();

        ObjectMapper mapper = new ObjectMapper();
        Mooc newObj = mapper.readValue(req.getInputStream(), Mooc.class);

        if (platforma.equals("coursera") || platforma.equals("edx") || platforma.equals("udacity")) {
            if (auth.isPresent() && auth.equals(Optional.of("true"))) {
                cDAO.postMooc(
                    newObj.title,
                    newObj.institution,
                    newObj.url,
                    ((Boolean) newObj.free),
                    newObj.platform
                );
                return Response.ok(201).build();
            } else 
                return Response.status(401).build();
        } return Response.status(404).build();
    }
}
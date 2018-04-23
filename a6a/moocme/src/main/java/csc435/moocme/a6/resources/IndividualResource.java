package csc435.moocme.a6.resources;

import csc435.moocme.a6.api.Mooc;
import csc435.moocme.a6.dao.CoursesDAO;

import com.codahale.metrics.annotation.Timed;

import org.jdbi.v3.core.Jdbi;

import javax.ws.rs.core.Context;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import java.io.IOException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.servlet.http.HttpServletRequest;


import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.*;

import java.util.concurrent.atomic.AtomicLong;
import java.util.Optional;

@Path("/courses/{platform}/{id}")
@Produces(MediaType.APPLICATION_JSON)
public class IndividualResource {

    private final CoursesDAO cDAO;

    // connect to db
    public IndividualResource(Jdbi db) {
        cDAO = db.onDemand(CoursesDAO.class);
    }

    @GET
    @Timed
    public Response doGet(@PathParam("platform") String plat, @PathParam("id") Integer id) {
        String platforma = plat.toLowerCase();
        if (platforma.equals("coursera") || platforma.equals("edx") || platforma.equals("udacity")) {
            if (id != null && id > 0 && id < 3468) // valid course idx & platform
                return Response.ok(cDAO.getSingleCourse(id, platforma)).build();
        } return Response.status(404).build();  
    }

    @PUT
    @Timed
    public Response doPut(@Context final HttpServletRequest req, 
                           @QueryParam("authenticated") Optional<String> auth, 
                           @PathParam("platform") String plat,
                           @PathParam("id") String id) throws IOException {
        String platforma = plat.toLowerCase();
        Boolean validIndex = ((Integer)Integer.parseInt(id) > 0 && (Integer)Integer.parseInt(id) < 3469) ? true : false;

        // Jackson parse & convert req body
        ObjectMapper mapper = new ObjectMapper();
        Mooc newObj = mapper.readValue(req.getInputStream(), Mooc.class);

        if (platforma.equals("coursera") || platforma.equals("edx") || platforma.equals("udacity")) {
            if (auth.isPresent() && auth.equals(Optional.of("true")) && validIndex) {  // auth, valid idx, & plat
                cDAO.updateMooc(
                    (Integer) Integer.parseInt(id),
                    newObj.title,
                    newObj.institution,
                    newObj.url,
                    ((Boolean) newObj.free),
                    newObj.platform
                );
                return Response.ok(202).build();   // accepted
            } else 
                return Response.status(401).build();  // unauthorized
        } return Response.status(404).build();
    }

    @DELETE
    @Timed
    public Response doDelete(@QueryParam("authenticated") Optional<String> auth, 
                             @PathParam("platform") String plat,
                             @PathParam("id") String id) throws IOException {
        String platforma = plat.toLowerCase();
        Integer m_id = (Integer) Integer.parseInt(id);


        if (platforma.equals("coursera") || platforma.equals("edx") || platforma.equals("udacity")) {
            if (auth.isPresent() && auth.equals(Optional.of("true"))) {
                cDAO.delMooc(m_id, platforma);
                return Response.ok(204).build();
            } else 
                return Response.status(401).build();
        } return Response.status(404).build();
    }
}
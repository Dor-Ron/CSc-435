package csc435.moocme.a2;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.*;

public class CourseServlet extends HttpServlet {
   @Override
   public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        res.setContentType("application/json; charset=UTF-8");

        String descrip = "Introduction to Data Science using the SciPy suite, R, and Hadoop";
        String egUrl = "http://www.cousera.com/courses/1212232323";
        String relURL = "/courses/8080";
        SearchResult sample = new SearchResult("Intro to Data Science", "Coursera", descrip, egUrl, relURL, false);
        
        // jackson
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(sample);
        
        PrintWriter out = res.getWriter();

        out.print(jsonString);
        out.flush();
   }
}
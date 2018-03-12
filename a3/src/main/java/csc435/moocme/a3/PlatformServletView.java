package csc435.moocme.a3;

import java.io.*;
import java.util.stream.Collectors;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.*;

public class PlatformServletView extends HttpServlet {
   @Override
   public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        res.setContentType("application/json; charset=UTF-8");

        HttpSession session = req.getSession();
        PrintWriter out = res.getWriter();

        if (session.getAttribute("platformCourses") != null) {
            ObjectMapper mapper = new ObjectMapper();
            String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(session.getAttribute("platformCourses"));

            out.print(jsonString);
            out.flush();
        }

        out.print("{ \"success\": false }");
        out.flush();
    }
}
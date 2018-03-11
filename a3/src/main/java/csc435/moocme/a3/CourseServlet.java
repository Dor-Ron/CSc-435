package csc435.moocme.a3;

import java.io.*;
import java.util.stream.Collectors;
import java.sql.*;
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
        ReqJsonObject sample = new ReqJsonObject("Intro to Data Science", "Coursera", descrip, egUrl, false);
        
        PrintWriter out = res.getWriter();


        Connection conn = null;
        Statement stmt = null;
        try {
           // Step 1: Allocate a database Connection object
           Class.forName("com.mysql.jdbc.Driver");  // Needed for JDK9/Tomcat9
           conn = DriverManager.getConnection(
              "jdbc:mysql://localhost:3306/moocs?useSSL=false", "root", ""); // <== Check!
              // database-URL(hostname, port, default database), username, password
   
           // Step 2: Allocate a Statement object within the Connection
           stmt = conn.createStatement();
   
           // Step 3: Execute a SQL SELECT query
           String sqlStr = "insert into courses (title, institution, uri, free, platform)" +
                           "values(\"deleteme\", \"harvard\", \"https://.com\", true, \"edx\");";
   
           // Print an HTML page as the output of the query
           out.println("<html><head><title>Query Response</title></head><body>");
           out.println("<h3>Thank you for your query.</h3>");
           out.println("<p>You query is: " + sqlStr + "</p>"); // Echo for debugging
           stmt.executeUpdate(sqlStr);  // Send the query to the server
   
       } catch (SQLException ex) {
          ex.printStackTrace();
       } catch (ClassNotFoundException ex) {
          ex.printStackTrace();
       } finally {
          out.close();  // Close the output writer
          try {
             // Step 5: Close the resources
             if (stmt != null) stmt.close();
             if (conn != null) conn.close();
          } catch (SQLException ex) {
             ex.printStackTrace();
          }
       }

        // jackson
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(sample);
        
        out = res.getWriter();

        out.print(jsonString);
        out.flush();
   }
}
package csc435.moocme.a3;

import java.io.*;
import java.util.ArrayList;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.*;

public class CourseServlet extends HttpServlet {
   @Override
   public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        res.setContentType("application/json; charset=UTF-8");
        
        PrintWriter out = res.getWriter();

        Connection conn = null;
        Statement stmt = null;
        try {
           Class.forName("com.mysql.jdbc.Driver");  
           conn = DriverManager.getConnection(
              "jdbc:mysql://localhost:3306/moocs?useSSL=false", "root", "");
   
           stmt = conn.createStatement();
   
           String sqlStr = "select * from courses;";
    
           ArrayList<ReqJsonObject> passOn = new ArrayList<ReqJsonObject>();
           ResultSet rs = stmt.executeQuery(sqlStr);
           while (rs.next()) {
                passOn.add(
                    new ReqJsonObject(rs.getInt("id"),
                                      rs.getString("title"),
                                      rs.getString("platform"),
                                      rs.getString("institution"),
                                      rs.getString("uri"),
                                      rs.getBoolean("free"))
                );
           }
           
           HttpSession session = req.getSession();
           session.setAttribute("allCourses", passOn);
           
           ServletContext context = getServletContext();
           RequestDispatcher rd = context.getRequestDispatcher("/coursesview");
           rd.forward(req,res);
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

        out.print("{ \"success\": false }");
        out.flush();
   }
}
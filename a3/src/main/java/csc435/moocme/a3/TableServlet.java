package csc435.moocme.a3;

import java.io.*;
import java.util.ArrayList;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.*;

public class TableServlet extends HttpServlet {
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
           if (req.getParameter("free").equals("true"))
                sqlStr = "select * from courses where free=true;";
    
           ArrayList<ReqJsonObject> passOn = new ArrayList<ReqJsonObject>();
           ResultSet rs = stmt.executeQuery(sqlStr);

           // make passable object to view based off query results
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
           RequestDispatcher rd = context.getRequestDispatcher("/tableview");
           rd.forward(req,res);
       } catch (SQLException ex) {
          ex.printStackTrace();
          out.print("{ \"success\": false }");
       } catch (ClassNotFoundException ex) {
          ex.printStackTrace();
          out.print("{ \"success\": false }");
       } finally {
          out.flush();
          out.close(); 
          try {
             if (stmt != null) stmt.close();
             if (conn != null) conn.close();
          } catch (SQLException ex) {
             ex.printStackTrace();
          }
       }
   }
}
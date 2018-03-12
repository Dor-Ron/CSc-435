package csc435.moocme.a3;

import java.io.*;
import java.util.ArrayList;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.*;

public class PlatformServlet extends HttpServlet {
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
   
           // get relative uri
           String relUrl = req.getRequestURI().substring(req.getContextPath().length());
           String sqlStr;
           ArrayList<ReqJsonObject> passOn = new ArrayList<ReqJsonObject>();
           ResultSet rs;
           HttpSession session = req.getSession();           
           ServletContext context = getServletContext();
           RequestDispatcher rd; 

           if (relUrl.toLowerCase().contains("coursera")) { // /courses/coursera
                sqlStr = "select * from courses where platform=\"coursera\";";
                rs = stmt.executeQuery(sqlStr);
                populateArrayList(rs, passOn);
                session.setAttribute("platformCourses", passOn);
                rd = context.getRequestDispatcher("/platformview");
                rd.forward(req,res);
           } else if (relUrl.toLowerCase().contains("edx")) { // /courses/edx
                sqlStr = "select * from courses where platform=\"edx\";";
                rs = stmt.executeQuery(sqlStr);
                populateArrayList(rs, passOn);
                session.setAttribute("platformCourses", passOn);
                rd = context.getRequestDispatcher("/platformview");
                rd.forward(req,res);
           } else { // /courses/udacity
                sqlStr = "select * from courses where platform=\"udacity\"";
                rs = stmt.executeQuery(sqlStr);
                populateArrayList(rs, passOn);
                session.setAttribute("platformCourses", passOn);
                rd = context.getRequestDispatcher("/platformview");
                rd.forward(req,res);
           }
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

   @Override
   public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        res.setContentType("application/json; charset=UTF-8");

        HttpSession session = req.getSession();
        PrintWriter out = res.getWriter();

        Connection conn = null;
        Statement stmt = null;
        try {
           Class.forName("com.mysql.jdbc.Driver");  
           conn = DriverManager.getConnection(
              "jdbc:mysql://localhost:3306/moocs?useSSL=false", "root", ""); 
   
           stmt = conn.createStatement();

           String sqlStr;
           ObjectMapper mapper = new ObjectMapper();
           ReqJsonObject newObj = mapper.readValue(req.getInputStream(), ReqJsonObject.class);

           sqlStr = "insert into courses (title, institution, uri, free, platform) values (\"" +
                    newObj.title + "\", \"" + newObj.institution + "\", \"" + newObj.url + "\"," +
                    newObj.free + ", \"" + newObj.platform + "\");";

            stmt.executeUpdate(sqlStr);

            sqlStr = "select * from courses where title=" + "\"" + newObj.title + "\"";

            ReqJsonObject ret = null;
            ResultSet rs = stmt.executeQuery(sqlStr);
            while (rs.next()) {
                ret = new ReqJsonObject(rs.getInt("id"),
                                        rs.getString("title"),
                                        rs.getString("platform"),
                                        rs.getString("institution"),
                                        rs.getString("uri"),
                                        rs.getBoolean("free"));
            }

            mapper = new ObjectMapper();
            String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(ret);

            out.print(jsonString);

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


   /** 
     * populates arraylist with RJOs
     *
     * @param rs       ResultSet type for sql query results.
     * @param passOn   ArrayList<ReqJsonObject> meant to be populated by function.
     **/
   public void populateArrayList(ResultSet rs, ArrayList<ReqJsonObject> passOn) throws SQLException {
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
   }
}
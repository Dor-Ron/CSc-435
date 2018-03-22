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
        PrintWriter out = res.getWriter();


        // get url, used to get course id for sql query
        String[] resources = req.getRequestURI().substring(req.getContextPath().length()).split("/");
        String courseId = resources[resources.length - 1];

        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");  
            conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/moocs?useSSL=false", "root", "");

            stmt = conn.createStatement();

            String sqlStr = "select * from courses where id=" + courseId + ";";

            ReqJsonObject rjo = null;
            ResultSet rs = stmt.executeQuery(sqlStr);

            // create object to add to session based off query results
            while (rs.next()) {
                rjo = new ReqJsonObject(rs.getInt("id"),
                                rs.getString("title"),
                                rs.getString("platform"),
                                rs.getString("institution"),
                                rs.getString("uri"),
                                rs.getBoolean("free"));
            }
            
            // add course object to session so its accessible to view
            HttpSession session = req.getSession();
            session.setAttribute("chosenCourse", rjo);
            
            // redirect to correspding view
            ServletContext context = getServletContext();
            RequestDispatcher rd = context.getRequestDispatcher("/courseview");
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

    @Override
    public void doPut(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        res.setContentType("application/json; charset=UTF-8");

        PrintWriter out = res.getWriter();

        Connection conn = null;
        Statement stmt = null;
        if (req.getParameter("authenticated") != null && req.getParameter("authenticated").equals("true")) { 
            try {
            Class.forName("com.mysql.jdbc.Driver");  
            conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/moocs?useSSL=false", "root", ""); 
    
            stmt = conn.createStatement();

            String sqlStr;

            // jackson, parse json in body
            ObjectMapper mapper = new ObjectMapper();
            ReqJsonObject newObj = mapper.readValue(req.getInputStream(), ReqJsonObject.class);

            // get course id from uri
            String[] resources = req.getRequestURI().substring(req.getContextPath().length()).split("/");
            String courseId = resources[resources.length - 1];

            sqlStr = "update courses set title = \"" + newObj.title + "\"," + 
                                            "institution = \"" + newObj.institution + "\"," +
                                            "uri = \"" + newObj.url + "\"," +
                                            "free = " + newObj.free + "," + 
                                            "platform = \"" + newObj.platform + "\"" +
                                            "where id=" + courseId + ";";

            stmt.executeUpdate(sqlStr);

            sqlStr = "select * from courses where id=" + courseId + ";";

            ReqJsonObject ret = null;
            ResultSet rs = stmt.executeQuery(sqlStr);

            // make query results RJO jackson can jsonify
            while (rs.next()) {
                ret = new ReqJsonObject(rs.getInt("id"),
                                        rs.getString("title"),
                                        rs.getString("platform"),
                                        rs.getString("institution"),
                                        rs.getString("uri"),
                                        rs.getBoolean("free"));
            }

            // return query results jsonified
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
        } else {
            out.print("{ \"success\": false }");
            out.flush();
            out.close();    
        }
    }

    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        res.setContentType("application/json; charset=UTF-8");

        PrintWriter out = res.getWriter();

        Connection conn = null;
        Statement stmt = null;
        if (req.getParameter("authenticated") != null && req.getParameter("authenticated").equals("true")) {
            try {
            Class.forName("com.mysql.jdbc.Driver");  
            conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/moocs?useSSL=false", "root", ""); 
    
            stmt = conn.createStatement();

            String sqlStr;

            // get course id to delete
            String[] resources = req.getRequestURI().substring(req.getContextPath().length()).split("/");
            String courseId = resources[resources.length - 1];

            sqlStr = "delete from courses where id=" + courseId + ";";

            stmt.executeUpdate(sqlStr);

            sqlStr = "select * from courses where id=" + courseId + ";";

            out.print("{ \"success\": true }");

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
        } else {
            out.print("{ \"success\": false }");
            out.flush();
            out.close();
        }
    }
}
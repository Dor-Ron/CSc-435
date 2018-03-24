package csc435.moocme.a4;

import static spark.Spark.*;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.*;
import java.sql.*;
import java.util.ArrayList;


public class App {
    public static void main(String[] args) {
        get("/courses", (req, res) -> {
            res.type("application/json; charset=UTF-8");

            // Connect to database
            Connection conn = null;
            Statement stmt = null;
            try {
                Class.forName("com.mysql.jdbc.Driver");  
                conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/moocs?useSSL=false", "root", ""); 
        
                stmt = conn.createStatement();

                // Appropriate SQL query
                String sqlStr = "select * from courses;";
                if (req.queryParams("free") != null && req.queryParams("free").equals("true")) 
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
   
                // pass derived json object to session
                req.session().attribute("allCourses", passOn);

                if (req.session().attribute("allCourses") != null) {
                    // jackson
                    ObjectMapper mapper = new ObjectMapper();
                    String jsonString = mapper.writerWithDefaultPrettyPrinter()
                                              .writeValueAsString(req.session().attribute("allCourses"));
                    return jsonString;
                } 
            } catch (SQLException ex) {
                ex.printStackTrace();
                halt("Problem with DB read attempt.");
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
                halt("Please try again in a minute");
            } finally {
                try {
                    if (stmt != null) stmt.close();
                    if (conn != null) conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            } return "{ \"success\": false\"}";
        });

        get("/courses/:platform", (req, res) -> {
            res.type("application/json; charset=UTF-8");

            // Connect to database
            Connection conn = null;
            Statement stmt = null;
            //if (req.queryParams("authenticated") != null && req.queryParams("authenticated").equals("true")) {
            try {
                Class.forName("com.mysql.jdbc.Driver");  
                conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/moocs?useSSL=false", "root", ""); 
        
                stmt = conn.createStatement();

                String sqlStr = "select * from courses where platform =";
                // match output to right platform
                if (req.params("platform").equals("coursera")) 
                    sqlStr += "\"coursera\"";       
                else if (req.params("platform").equals("edx")) 
                    sqlStr += "\"edx\"";
                else if ((req.params("platform").equals("udacity")))
                    sqlStr += "\"udacity\"";
                else sqlStr += "nonexistentcolumn";
                if (req.queryParams("free") != null && req.queryParams("free").equals("true")) 
                    sqlStr += "and free=true";
                sqlStr += ";";

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

                // pass derived json object to session
                req.session().attribute("platCourses", passOn);

                if (req.session().attribute("platCourses") != null) {
                    // jackson
                    ObjectMapper mapper = new ObjectMapper();
                    String jsonString = mapper.writerWithDefaultPrettyPrinter()
                                              .writeValueAsString(req.session().attribute("platCourses"));
                    return jsonString;
                } 
            } catch (SQLException ex) {
                ex.printStackTrace();
                halt("Problem with DB read attempt.");
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
                halt("Please try again in a minute");
            } finally {
                try {
                    if (stmt != null) stmt.close();
                    if (conn != null) conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            } return "{ \"success\": false\"}";
        });

        post("/courses/:platform", (req, res) -> {
            res.type("application/json; charset=UTF-8");

            // Connect to database
            Connection conn = null;
            Statement stmt = null;
            if (req.queryParams("authenticated") != null && req.queryParams("authenticated").equals("true")) {
                try {
                    Class.forName("com.mysql.jdbc.Driver");  
                    conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/moocs?useSSL=false", "root", ""); 
            
                    stmt = conn.createStatement();

                    // parse json from request 
                    String sqlStr;
                    ObjectMapper mapper = new ObjectMapper();
                    ReqJsonObject newObj = mapper.readValue(req.body(), ReqJsonObject.class);

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

                    return jsonString;

                } catch (SQLException ex) {
                    ex.printStackTrace();
                    halt("Problem with DB read attempt.");
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                    halt("Please try again in a minute");
                } finally {
                    try {
                        if (stmt != null) stmt.close();
                        if (conn != null) conn.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                } 
            } return "{ \"success\": false\"}";
        });

        get("/courses/:platform/:id", (req, res) -> {
            res.type("application/json; charset=UTF-8");

            // Connect to database
            Connection conn = null;
            Statement stmt = null;
            try {
                Class.forName("com.mysql.jdbc.Driver");  
                conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/moocs?useSSL=false", "root", ""); 
        
                stmt = conn.createStatement();

                // Appropriate SQL query
                String sqlStr = "select * from courses where id=" + req.params("id");

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
                req.session().attribute("chosenCourse", rjo);

                if (req.session().attribute("chosenCourse") != null) {
                    // jackson
                    ObjectMapper mapper = new ObjectMapper();
                    String jsonString = mapper.writerWithDefaultPrettyPrinter()
                                              .writeValueAsString(req.session().attribute("chosenCourse"));
                    return jsonString;
                } 
            } catch (SQLException ex) {
                ex.printStackTrace();
                halt("Problem with DB read attempt.");
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
                halt("Please try again in a minute");
            } finally {
                try {
                    if (stmt != null) stmt.close();
                    if (conn != null) conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            } return "{ \"success\": false\"}";
        });

        put("/courses/:platform/:id", (req, res) -> {
            res.type("application/json; charset=UTF-8");

            // Connect to database
            Connection conn = null;
            Statement stmt = null;
            if (req.queryParams("authenticated") != null && req.queryParams("authenticated").equals("true")) {
                try {
                    Class.forName("com.mysql.jdbc.Driver");  
                    conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/moocs?useSSL=false", "root", ""); 
            
                    stmt = conn.createStatement();

                    // parse json from request 
                    String sqlStr;
                    ObjectMapper mapper = new ObjectMapper();
                    ReqJsonObject newObj = mapper.readValue(req.body(), ReqJsonObject.class);

                    sqlStr = "update courses set title = \"" + newObj.title + "\"," + 
                                            "institution = \"" + newObj.institution + "\"," +
                                            "uri = \"" + newObj.url + "\"," +
                                            "free = " + newObj.free + "," + 
                                            "platform = \"" + newObj.platform + "\"" +
                                            "where id=" + req.params("id") + ";";

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
                    String jsonString = mapper.writerWithDefaultPrettyPrinter()
                                              .writeValueAsString(ret);
                    return jsonString;
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    halt("Problem with DB read attempt.");
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                    halt("Please try again in a minute");
                } finally {
                    try {
                        if (stmt != null) stmt.close();
                        if (conn != null) conn.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                } 
            } return "{ \"success\": false\"}";
        });

        delete("/courses/:platform/:id", (req, res) -> {
            res.type("application/json; charset=UTF-8");

            // Connect to database
            Connection conn = null;
            Statement stmt = null;
            if (req.queryParams("authenticated") != null && req.queryParams("authenticated").equals("true")) {
                try {
                    Class.forName("com.mysql.jdbc.Driver");  
                    conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/moocs?useSSL=false", "root", ""); 
            
                    stmt = conn.createStatement();

                    // parse json from request 
                    String sqlStr;
                    sqlStr = "delete from courses where id=" + req.params("id") + ";";

                    stmt.executeUpdate(sqlStr);
                    return "{ \"success\": true }";
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    halt("Problem with DB read attempt.");
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                    halt("Please try again in a minute");
                } finally {
                    try {
                        if (stmt != null) stmt.close();
                        if (conn != null) conn.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                } 
            } return "{ \"success\": false\"}";
        });
    }
}
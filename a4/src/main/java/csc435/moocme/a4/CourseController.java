package csc435.moocme.a4;

import static spark.Spark.*;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.*;
import java.sql.*;
import java.util.ArrayList;


class CourseController {
    public static View vue = new View();

    public CourseController() {
        super();
    }

    public void doGet() {
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
                    if (req.params("platform") != null && req.params("platform").equals(rs.getString("platform"))) {
                        rjo = new ReqJsonObject(rs.getInt("id"),
                                        rs.getString("title"),
                                        rs.getString("platform"),
                                        rs.getString("institution"),
                                        rs.getString("uri"),
                                        rs.getBoolean("free"));
                    }
                }
                req.session().attribute("chosenCourse", rjo);

                if (req.session().attribute("chosenCourse") != null) 
                    return vue.jsonify(req.session().attribute("chosenCourse"));
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
    }

    public void doPut() {
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

                    req.session().attribute("updatedCourse", ret);

                    if (req.session().attribute("updatedCourse") != null) 
                        return vue.jsonify(req.session().attribute("updatedCourse"));

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

    public void doDelete() {
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
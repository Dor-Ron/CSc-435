package csc435.moocme.a4;

import static spark.Spark.*;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.*;
import java.sql.*;
import java.util.ArrayList;


class TableController {
    public static View vue = new View();

    public TableController() {
        super();
    }

    public void doGet() {
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

                if (req.session().attribute("allCourses") != null) 
                    return vue.jsonify(req.session().attribute("allCourses"));
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
}
package csc435.moocme.a4;

import static spark.Spark.*;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.*;
import java.sql.*;
import java.util.ArrayList;


public class App {

    public static final CourseController cc = new CourseController();
    public static final PlatformController pc = new PlatformController();
    public static final TableController tc = new TableController();

    public static void main(String[] args) {
        runApp();
    }

    /**
    * sets up routes
    */
    public static void runApp() {
        // /courses
        tc.doGet();

        // /courses/:platform
        pc.doGet();
        pc.doPost();

        // /courses/:platform/:id
        cc.doGet();
        cc.doPut();
        cc.doDelete();
    }
}
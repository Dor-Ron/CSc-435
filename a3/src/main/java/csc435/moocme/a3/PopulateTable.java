package csc435.moocme.a3;

import java.sql.*;
import java.util.ArrayList;
import java.io.*;
import java.lang.ClassLoader;

public class PopulateTable {

    public static void main(String[] args) {
        String[] params;
        String[] files = {"edx.txt", "coursera.txt", "udacity.txt"};
	    String path = "/usr/local/apache-tomcat-7.0.84/bin/CSc-435/a3/target/classes/data/";
        Connection conn = null;
        Statement stmt = null;
        String sqlStr = "";
        int x = 0;

        try{
            Class.forName("com.mysql.jdbc.Driver");  // Needed for JDK9/Tomcat9
            conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/moocs?useSSL=false", "root", "");

            stmt = conn.createStatement();
            
            for (String file: files) {
                //Create object of FileReader
                FileReader inputFile = new FileReader(path+file);
    
                BufferedReader bufferReader = new BufferedReader(inputFile);
                String line;
    
                // Read file line by line and print on the console
                while ((line = bufferReader.readLine()) != null) {
                    params = line.split("~");
                    System.out.println(params[0]);
                    sqlStr = "insert into courses (title, institution, uri, free, platform)" +
                             "values(\"" +  params[0] + "\", \"" + params[2] + "\", \"" + 
                             params[4] + "\"," + params[3] + ","  + "\"" + params[1] + "\");";
                    stmt.executeUpdate(sqlStr);  
                }

                bufferReader.close();         
            }
         } catch (SQLException ex) {
            ex.printStackTrace();
         } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
         } catch(Exception e){
            System.out.println("Error while reading file line by line:" + e.getMessage());                      
         } finally {
            try {
               // Step 5: Close the resources
               if (stmt != null) stmt.close();
               if (conn != null) conn.close();
            } catch (SQLException ex) {
               ex.printStackTrace();
            }
         }
    }
}


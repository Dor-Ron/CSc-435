package csc435.moocme.a2;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class IndexServlet extends HttpServlet {

   @Override
   public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        // Set the response MIME type of the response message
        res.setContentType("text/html");

        // Allocate a output writer to write the response message into the network socket
        PrintWriter out = res.getWriter();

        HttpSession session = req.getSession(true);                    

        try {
            out.println("<!DOCTYPE html>" +
            "<html>" +
            "<head>" +
            "    <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/css/materialize.min.css\">" +
            "    <link href=\"https://fonts.googleapis.com/icon?family=Material+Icons\" rel=\"stylesheet\">" +
            "    <script src=\"https://unpkg.com/jquery@3.3.1/dist/jquery.js\"></script>" +
            "    <script src=\"https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js\"></script>" +
            "<style>" +
            "  form {" +
            "    margin-left: 35%;" +
            "    margin-top: 7%;" +
            "  }  " +
            "</style>" +
            "</head>" +
            "<body>" +
            "<form class=\"col s12\" action=\"search\"/>" +
            "  <div class=\"row\">" +
            "    <div class=\"input-field col s6\">" +
            "      <input placeholder=\"MOOC Subject/Title\"id=\"user_search\" type=\"text\" class=\"validate\">" +
            "      <label for=\"user_search\">MOOC Subject/Title</label>" +
            "    </div>" +
            "  </div>" +
            "  <p>" +
            "      <input type=\"checkbox\" id=\"price\" />" +
            "      <label for=\"price\">Only Free</label>" +
            "  </p>" +
            "  <p> Exclude: </p>" +
            "  <ul>" +
            "   <li>" +
            "     <input type=\"checkbox\" id=\"coursera\" />" +
            "     <label for=\"coursera\">Coursera</label>" +
            "   </li>" +
            "   <li>" +
            "     <input type=\"checkbox\" id=\"edx\" />" +
            "     <label for=\"edx\">edX</label>  " +
            "   </li>" +
            "   <li>" +
            "     <input type=\"checkbox\" id=\"udemy\" />" +
            "     <label for=\"udemy\">Udemy </label>  " +
            "   </li>" +
            "  </ul>" +
            "  <button class=\"btn waves-effect waves-light\" type=\"submit\" name=\"action\">Submit" +
            "    <i class=\"material-icons right\">send</i>" +
            "  </button>" +
            "</form> " +
            "</body>" +
            "</html>");
        } finally {
            out.close();  // Always close the output writer
        }
   }

   @Override
   public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

   }
}
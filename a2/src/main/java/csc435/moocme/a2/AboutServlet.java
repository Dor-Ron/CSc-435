package csc435.moocme.a2;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.*;

public class AboutServlet extends HttpServlet {
   @Override
   public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        res.setContentType("text/html");

        // Allocate a output writer to write the response message into the network socket
        PrintWriter out = res.getWriter();

        out.println("<html><head></head><body><div style=\"width: 50%; height: 200px;" +
                    "background-color: rgba(255, 0, 0, 0.3); padding: 1px 10px; margin: 10% auto;\">" +
                    "<p>" +
                    "For a myriad of reasons, many individuals curious about the abundance of" +
                    "knowledge universities have to offer, cannot allow themselves to claim any of it." +
                    "A relatively recent phenomenon, in 2011, Stanford published three of their courses" +
                    "online for free, and set the wave for a new means of sharing higher education." +
                    "Today there are hundreds of university courses and thousands of independently" + 
                    "taught courses, or MOOCs, offered on a multitude of platforms, such as Udacity," +
                    "Coursera, Udemy and EDx amongst others. This is where MOOCme comes in. In its essence," +
                    "MOOCme is a website hosting a MOOC search engine. This search engine will supply MOOC" +
                    "students all over the world the leisure of searching for desired courses, on multiple" +
                    "platforms, from a single site." +
                    "</p>" +  
                    "</div></body></html>");

        out.flush();
   }
}
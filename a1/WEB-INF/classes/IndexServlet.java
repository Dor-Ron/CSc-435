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
      String status = (session != null 
                               && session.getAttribute("loggedIn") != null 
                               && (Boolean) session.getAttribute("loggedIn") != false) 
                               ? "Authenticated"
                               : "Not Authenticated";
                      

        try {
          out.println("<html>");
          out.println("<head><title>Hello, World</title></head>");
          out.println("<body>");
          out.println("<h1>Welcome to Moocme</h1>");  // says Hello
          // Echo client's request information
          out.println("<p>Request URI: " + req.getRequestURI() + "</p>");
          out.println("<p>Protocol: " + req.getProtocol() + "</p>");
          out.println("<p>PathInfo: " + req.getPathInfo() + "</p>");
          out.println("<p>Remote Address: " + req.getRemoteAddr() + "</p>");
          // Generate a random number upon each request
          out.println("<p>You are <strong>" + status + "</strong></p>");
          out.println("</body></html>");
       } finally {
          out.close();  // Always close the output writer
       }
    } 
}
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
 
public class AboutServlet extends HttpServlet {
   @Override
   public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
      
      // Set the response MIME type of the response message
      res.setContentType("text/html");
      // Allocate a output writer to write the response message into the network socket
      PrintWriter out = res.getWriter();
                      
        try {
          out.println("<html>");
          out.println("<head><title>Hello, World</title></head>");
          out.println("<body>");
          out.println("<h1>About Moocme</h1>");  // says Hello
          // Echo client's request information
          out.println("<p><strong>requirements doc</strong></p>");
          out.println("</body></html>");
       } finally {
          out.close();  // Always close the output writer
       }
    } 
}
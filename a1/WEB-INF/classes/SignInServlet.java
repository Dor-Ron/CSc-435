import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
 
public class SignInServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
      
      // Set the response MIME type of the response message
      res.setContentType("text/html");
 
      HttpSession session = req.getSession(true);
      if (session == null) session.setAttribute("loggedIn", true);
      
      if ((Boolean) session.getAttribute("loggedIn"))
        session.setAttribute("loggedIn", false);
      else
        session.setAttribute("loggedIn", true);
   }
}
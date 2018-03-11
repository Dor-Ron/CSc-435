// package csc435.moocme.a3;

// import java.io.*;
// import java.util.ArrayList;

// import javax.servlet.*;
// import javax.servlet.http.*;
// import com.fasterxml.jackson.annotation.*;
// import com.fasterxml.jackson.databind.*;

// public class SearchServlet extends HttpServlet {
//    @Override
//    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
//         res.setContentType("application/json; charset=UTF-8");

//         String descrip = "Introduction to Data Science using the SciPy suite, R, and Hadoop";
//         String egUrl = "http://www.cousera.com/courses/1212232323";
//         String relURL = "/courses/8080";
//         SearchResult sample = new SearchResult("Intro to Data Science", "Coursera", descrip, egUrl, relURL, false);

//         String descrip2 = "Data Science for beginners taught by experts from UCSD";
//         String egUrl2 = "http://www.cousera.com/courses/121838939";
//         String relURL2 = "/courses/8081";
//         SearchResult sample2 = new SearchResult("Data Science Fundamentals", "edX", descrip2, egUrl2, relURL2, true);

//         String descrip3 = "Introduction to Python and Apache Spark for Data Science and Data Analytics";
//         String egUrl3 = "http://www.cousera.com/courses/12181273";
//         String relURL3 = "/courses/8082";
//         SearchResult sample3 = new SearchResult("Intro to Data Science and Analytics", "Udacity", descrip3, egUrl3, relURL3, true);

//         ArrayList<SearchResult> arr = new ArrayList<SearchResult>();
//         arr.add(sample);
//         arr.add(sample2);
//         arr.add(sample3);
        
//         // jackson
//         ObjectMapper mapper = new ObjectMapper();
//         String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(arr);
        
//         PrintWriter out = res.getWriter();

//         out.print(jsonString);
//         out.flush();
//     }   
// }
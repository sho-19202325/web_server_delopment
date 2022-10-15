package webapps.sessiontest;
import java.io.*;
import com.webserverdevelopment.henacat.servlet.*;
import com.webserverdevelopment.henacat.servlet.http.*;

public class SessionTest extends HttpServlet {
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    response.setContentType("text/plain");
    PrintWriter out = response.getWriter();

    HttpSession session = request.getSession(true);
    Integer counter = (Integer)session.getAttribute("Counter");

    if (counter == null) {
      out.println("No session");
      session.setAttribute("Counter", 1);
    } else {
      out.println("Counter.." + counter);
      session.setAttribute("Counter", counter + 1);
    }
  }
}

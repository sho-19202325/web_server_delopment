import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class CookieTest extends HttpServlet {
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    response.setContentType("text/plain");
    PrintWriter out = response.getWriter();
    String counterStr = null;

    Cookie[] cookies = request.getCookies();
    if (cookies == null) {
      out.println("cookies == null");
    } else {
      out.println("cookies.length.." + cookies.length);
      for (int i = 0; i < cookies.length; i++) {
        out.println("cookies[" + i + "].." + cookies[i].getName() + "/" + cookies[i].getValue());
        if (cookies[i].getName().equals("COUNTER")) {
          counterStr = cookies[i].getValue();
        }
      }
    }

    int counter;
    if (counterStr == null) {
      counter = 1;
    } else {
      counter = Integer.parseInt(counterStr) + 1;
    }
    Cookie newCookie = new Cookie("COUNTER", "" + counter);
    response.addCookie(newCookie);
  }
}

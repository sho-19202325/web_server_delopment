package com.webserverdevelopment.henacat.servlet.http;
import com.webserverdevelopment.henacat.servlet.*;

public class HttpServlet {
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, java.io.IOException {}
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, java.io.IOException {}
  public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, java.io.IOException {
    if (req.getMethod().equals("GET")) {
      doGet(req, resp);
    } else if (req.getMethod().equals("POST")) {
      doPost(req, resp);
    }
  }
}


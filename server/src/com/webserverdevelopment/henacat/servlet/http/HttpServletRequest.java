package com.webserverdevelopment.henacat.servlet.http;
import java.io.*;

public interface HttpServletRequest {
  String getMethod();
  String getParameter(String name);
  String[] getParameterValues(String name);
  void setCharacterEncoding(String env) throws UnsupportedEncodingException;
}

package com.sampledomain.henacat.servlet.http;
import java.io.*;

public class HttpServletRequest {
  String getMethod();
  String getParameter(String name);
  String[] getParameter(String name);
  void setCharacterEncoding(String env) throws UnsupportedEncodingException;
}

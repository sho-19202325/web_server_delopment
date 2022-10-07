package com.webserverdevelopment.henacat.servlet;

public class ServletException {
  public ServletException(String message) {
    super(message);
  }

  public ServletException(String message, Throwable rootCause) {
    super(message, rootCause);
  }
  
  public ServletException(java.lang.Throwable rootCause) {
    super(rootCause);
  }
}

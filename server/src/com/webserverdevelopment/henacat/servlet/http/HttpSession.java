package com.webserverdevelopment.henacat.servlet.http;
import java.util.*;

public interface HttpSession {
  String getId();
  Object getAttribute(String name);
  Enumeration<String> getAttributeNames();
  void removeAttribute(String name);
  void setAttribute(String name, Object value);
}

package com.webserverdevelopment.henacat.servletimpl;
import com.webserverdevelopment.henacat.servlet.http.*;
import java.util.*;
import java.util.concurrent.*;

public class HttpSessionImpl implements HttpSession {
  private String id;
  // ConcurrentHashMapはスレッドセーフなHashMap
  private Map<String, Object> attributes = new ConcurrentHashMap<String, Object>();
  private volatile long lastAccessedTime;

  public String getId() {
    return this.id;
  }

  public Object getAttribute(String name) {
    return this.attributes.get(name);
  }

  public Enumeration<String> getAttributeNames() {
    // Setは重複を許さないリスト
    Set<String> names = new HashSet<String>();
    names.addAll(attributes.keySet());

    return Collections.enumeration(names);
  }

  public void removeAttribute(String name) {
    this.attributes.remove(name);
  }

  public void setAttribute(String name, Object value) {
    if (value == null) {
      removeAttribute(name);
      return;
    }
    this.attributes.put(name, value);
  }

  // synchronizedは排他制御用
  synchronized void access() {
    this.lastAccessedTime = System.currentTimeMillis();
  }

  long getLastAccessedTime() {
    return this.lastAccessedTime;
  }

  public HttpSessionImpl(String id) {
    this.id = id;
    this.access();
  }
}

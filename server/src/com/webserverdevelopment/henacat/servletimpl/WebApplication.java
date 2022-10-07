package com.webserverdevelopment.henacat.servletimpl;
import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;

public class WebApplication {
  // よくわからないので何に使っているか調べて適宜変える
  private static String WEBAPPS_DIR = "/usr/src/webapps";
  // private static final String DOCUMENT_ROOT = "/usr/src";

  private static Map<String, WebApplication> webAppCollection = new HashMap<String, WebApplication>();

  String directory;
  ClassLoader classLoader;

  private Map<String, ServletInfo> servletCollection = new HashMap<String, ServletInfo>;

  private WebApplication(String dir) throws MalformedURLException {
    this.directory = dir;
    FileSystem fs = FileSystems.getDefault();

    Path pathObj = fs.getPath(WEBAPPS_DIR + File.separator + dir);
    this.classLoader = URLClassLoader.newInstance(new URL[]{pathObj.toUri().toURL()});
  }

  public static WebApplication createIncetance(String dir) throws MalformedURLException {
    WebApplication newApp = new WebApplication(dir);
    webAppCollection.put(dir, newApp);

    return newApp;
  }

  public void addServlet(String urlPattern, String servletClassName) {
    this.servletCollection.put(new ServletInfo(urlPattern, servletClassName)
  }

  public ServletInfo serchServlet(String path) {
    return servletCollection.get(path);
  }

  public static WebApplication searchWebApplication(String dir) {
    return webAppCollection.get(dir);
  }
}

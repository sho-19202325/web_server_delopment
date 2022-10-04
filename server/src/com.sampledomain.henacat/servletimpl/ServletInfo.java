public class ServletInfo {
  WebApplication webApp;
  String urlPattern;
  String servletClassName;
  HttpServlet servlet;

  // クラスと同名のpublicメソッドはコンストラクタ
  public ServletInfo(WebApplication webApp, String urlPattern, String servletClassName) {
    this.webApp = webApp;
    this.urlPattern = urlPattern;
    this.servletClassName = servletClassName;
  }
}

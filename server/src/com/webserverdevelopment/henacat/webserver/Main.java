package com.webserverdevelopment.henacat.webserver;
import com.webserverdevelopment.henacat.servletimpl.WebApplication;
import java.net.*;

public class Main {
  public static void main(String[] argv) throws Exception {
    // webapplicationインスタンスを作成
    WebApplication testbbs = WebApplication.createInstance("testbbs");
    WebApplication cookietest = WebApplication.createInstance("cookietest");
    WebApplication sessiontest = WebApplication.createInstance("sessiontest");

    // webapplicationインスタンスにサーブレットを追加
    // NOTE: servletClassNameにはクラス名を指定するがShowBBSのみだと動かない。
    //       パッケージ名を含め、webapps.testbbs.ShowBBSとすること
    testbbs.addServlet("/ShowBBS", "webapps.testbbs.ShowBBS");
    testbbs.addServlet("/PostBBS", "webapps.testbbs.PostBBS");
    cookietest.addServlet("/CookieTest", "webapps.cookietest.CookieTest");
    sessiontest.addServlet("/SessionTest", "webapps.sessiontest.SessionTest");

    try (ServerSocket server = new ServerSocket(8001)) {
      for (;;) {
        // 指定したportで通信を待ち受ける
        Socket socket = server.accept();
        
        // threadを作成し、ソケットを渡す
        ServerThread serverThread = new ServerThread(socket);
        Thread thread = new Thread(serverThread);
        thread.start();
      }
    }
  }
}

package com.webserverdevelopment.henacat.webserver;
import com.webserverdevelopment.henacat.servletimpl.WebApplication;
import java.net.*;

public class Main {
  public static void main(String[] argv) throws Exception {
    // webapplicationインスタンスを作成
    WebApplication app = WebApplication.createInstance("testbbs");

    // webapplicationインスタンスにサーブレットを追加
    // NOTE: servletClassNameにはクラス名を指定するがShowBBSのみだと動かない。
    //       パッケージ名を含め、webapps.testbbs.ShowBBSとすること
    app.addServlet("/ShowBBS", "webapps.testbbs.ShowBBS");
    app.addServlet("/PostBBS", "webapps.testbbs.PostBBS");

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

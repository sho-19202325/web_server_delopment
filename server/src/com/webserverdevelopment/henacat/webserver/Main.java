package com.webserverdevelopment.henacat.webserver;
import com.webserverdevelopment.henacat.servletimpl.WebApplication;
import java.net.*;

public class Main {
  public static void main(String[] argv) throws Exception {
    // webapplicationインスタンスを作成
    WebApplication app = WebApplication.createIncetance("testbbs");

    // webapplicationインスタンスにサーブレットを追加
    app.addServlet("/ShowBBS", "ShowBBS");
    app.addServlet("/PostBBS", "PostBBS");

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

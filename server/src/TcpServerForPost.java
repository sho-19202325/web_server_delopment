import java.io.*;
import java.net.*;

public class TcpServerForPost {
  public static void main(String[] args) throws Exception {
    try (
      ServerSocket server = new ServerSocket(8001);
      FileOutputStream fos = new FileOutputStream("receive/server_recv.txt");
    ) {
      System.out.println("クライアントからの接続を待ちます。");
      Socket socket = server.accept();
      System.out.println("クライアント接続。");

      int ch;
      // クライアントから受け取った内容をserver_recv.txtに出力
      InputStream input = socket.getInputStream();
      // クライアントは終了のマークとして0を送付してくる
      while ((ch = input.read()) != -1) {
        fos.write(ch);
      }

      socket.close();
      System.out.println("通信を終了しました。");
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
  }
}
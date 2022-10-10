package com.webserverdevelopment.henacat.webserver;
import java.util.*;
import java.io.*;
import java.net.*;
import java.nio.file.*;
import com.webserverdevelopment.henacat.servletimpl.*;
import com.webserverdevelopment.henacat.util.*;

public class ServerThread implements Runnable { 
  private static final String DOCUMENT_ROOT = "/usr/src";
  private static final String ERROR_DOCUMENT = "/usr/src/error";
  private Socket socket;

  // readListで読み出したrequest headerを
  // 引数として受け取ったrequestHeaderオブジェクトに追加するmethod
  private static void addRequestHeader(Map<String, String> requestHeader, String line) {
    int colonPos = line.indexOf(':');
    // indexOf()は引数で指定した文字列が見つからなかった時、-1を返す
    if (colonPos == -1)
      return;

    String headerName = line.substring(0, colonPos).toUpperCase();
    String headerValue = line.substring(colonPos + 1).trim();
    requestHeader.put(headerName, headerValue);
  }

  @Override
  public void run() {
    OutputStream output = null;
    try {
      InputStream input = socket.getInputStream();

      String line;
      String requestLine = null;
      String method = null;
      Map<String, String> requestHeader = new HashMap<String, String>();

      // socketに入力されたものを一行ずつ読み出す
      // GETかPOSTで始まっている場合は、それをmethodとし、
      // その文をrequestLineに代入する
      // それ以外の場合は、通常のrequest headerに追加する

      while ((line = Util.readLine(input)) != null) {
        if (line == "") {
          break;
        }
        if (line.startsWith("GET")) {
          method = "GET";
          requestLine = line;
        } else if (line.startsWith("POST")) {
          method = "POST";
          requestLine = line;
        } else {
          addRequestHeader(requestHeader, line);
        }
      }

      // リクエストがなければthreadを閉じる。
      if (requestLine == null)
      return;

      String reqUri = MyURLDecoder.decode(requestLine.split(" ")[1], "UTF-8");
      String[] pathAndQuery = reqUri.split("\\?");
      String path = pathAndQuery[0];
      String query = null;

      if (pathAndQuery.length > 1) {
        query = pathAndQuery[1];
      }

      // 溜め込んだデータをsocketに出力するためのインスタンスを生成
      // OutputStreamとは出力バイトを受け付けて、特定の受け手に送るもの
      output = new BufferedOutputStream(socket.getOutputStream());

      // pathで指定されたディレクトリのwebアプリケーションを取得
      String appDir = path.substring(1).split("/")[0];
      WebApplication webApp = WebApplication.searchWebApplication(appDir);
      if (webApp != null) {
        // NOTE: path = webappdireactryのパス + servletのパス
        ServletInfo servletInfo = webApp.searchServlet(path.substring(appDir.length() + 1));
        if (servletInfo != null) {
          // webアプリケーションに登録したサーブレットのパスに一致すれば、doService()処理を実行する
          ServletService.doService(method, query, servletInfo, requestHeader, input, output);

          return;
        }
      }

      String ext = null;
      String[] tmp = reqUri.split("\\.");
      ext = tmp[tmp.length - 1];

      if (path.endsWith("/")) {
        path += "index.html";
        ext = "html";
      }

      FileSystem fs = FileSystems.getDefault();
      Path pathObj = fs.getPath(DOCUMENT_ROOT + path);
      Path realPath;

      try {
        realPath = pathObj.toRealPath();
      } catch (NoSuchFileException ex) {
        SendResponse.sendNotFoundResponse(output, ERROR_DOCUMENT);
        return;
      }

      if (!realPath.startsWith(DOCUMENT_ROOT)) {
        SendResponse.sendNotFoundResponse(output, ERROR_DOCUMENT);
        return;
      } else if (Files.isDirectory(realPath)) {
        String host = requestHeader.get("HOST");
        String location = "http://" + ((host != null) ? host : Constants.SERVER_NAME) + path + "/";
        SendResponse.sendMovePermanentlyResponse(output, location);
        return;
      }

      try (InputStream fis = new BufferedInputStream(Files.newInputStream(realPath))) {
        SendResponse.sendOkResponse(output, fis, ext);
      } catch (FileNotFoundException ex) {
        SendResponse.sendNotFoundResponse(output, ERROR_DOCUMENT);
      }

    } catch (Exception ex) {
      ex.printStackTrace();
    } finally {
      try {
        if (output != null) {
          output.close();
        }
        socket.close();
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  }

  ServerThread(Socket socket) {
    this.socket = socket;
  }
}

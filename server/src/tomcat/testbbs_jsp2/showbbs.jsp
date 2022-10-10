<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="bbs.Message" %>
<%!
  // HTMLで意味のある文字をエスケープするメソッド
  private String escapeHtml(String src) {
    return src.replace("&", "&amp;")
              .replace("<", "&lt;")
              .replace(">", "&gt;")
              .replace("¥", "&quot;")
              .replace("'", "&#39;");
  }
%>
<html>
  <head>
    <title>テスト掲示板</title>
    <script language="JavaScript">
    function getCookie(key) {
      let i, index, splitted;
      let keyStr = key + "=";
      splitted = document.cookie.split("; ");

      for (i = 0; i < splitted.length; i++) {
        if (splitted[i].substring(0, keyStr.length) == keyStr) {
          return decodeURIComponent(splitted[i].substring(keyStr.length));
        }
      }
      return "";
    }
    function setCookie(key, val) {
      document.cookie = key + "=" + encodeURIComponent(val) + "; expires=Wed, 01-Jan-2037 00:00:00 GMT";
    }
    function setHandleCookie() {
      setCookie("handle", document.forms[0].handle.value);
    }
    </script>
  </head>
  <body>
    <h1>テスト掲示板</h1>
    <form action="/testbbs_jsp2/PostBBS" method="POST">
      タイトル: <input type="text" name="title" size="60"/><br/>
      ハンドル名: <input type="text" name="handle"/><br/>
      <textarea name="message" rows="4" cols="60"></textarea><br/>
      <input type="submit" onclick="setHandleCookie();"/>
    </form>
    <hr/>
    <%
      for (Message message : Message.messageList) {
    %>
    <p> 
    『<%= escapeHtml(message.title) %>』 &nbsp;&nbsp;
      <%= escapeHtml(message.handle) %> さん&nbsp;&nbsp;
      <%= escapeHtml(message.date.toString()) %>
    </p>
    <p>
      <%= escapeHtml(message.message).replace("\r\n", "<br/>") %>
    </p>
    <hr/>
    <%
      }
    %>
    <script language="JavaScript">
      document.forms[0].handle.value = getCookie("handle");
    </script>
  </body>
</html>

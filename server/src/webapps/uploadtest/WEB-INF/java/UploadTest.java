import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.*;

@MultipartConfig(maxFileSize=1000000, maxRequestSize=1000000, fileSizeThreshold=1000000)
public class UploadTest extends HttpServlet {
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    request.setCharacterEncoding("Shift_JIS");
    response.setContentType("text/plain;charset=Shift_JIS");
    PrintWriter out = response.getWriter();

    for (Part part : request.getParts()) {
      out.println("name.." + part.getName());
      for(String headerName : part.getHeaderNames()) {
        out.println(headerName + "=" + part.getHeader(headerName));
      }
      out.println("Content-Type.." + part.getContentType());
      out.println("Name.." + part.getName() + "/size.." + part.getSize());
      Reader reader = new InputStreamReader(part.getInputStream(), "Shift_JIS");
      int ch;
      while((ch = reader.read()) >= 0) {
        out.print((char)(ch & 0xffff));
      }
      reader.close();
      out.println("¥n============================================");
    }
    out.println("text_name=" + request.getParameter("text_name"));
  }
}

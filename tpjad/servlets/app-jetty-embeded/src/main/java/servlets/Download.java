package servlets;

import java.io.*;
import javax.servlet.http.*;

public class Download extends HttpServlet {

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) {
        String fileName = request.getParameter("fileName");
        String filePath = "C:/Users/Sprybase/AppData/Local/Temp/" + fileName + ".json";
        try {
            download(filePath, response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void download(String fileServer, HttpServletResponse response) throws IOException {
        File fs = new File(fileServer);
        String mimeType = getServletContext().getMimeType(fileServer);
        if (mimeType == null) mimeType = "application/octet-stream";
        response.setContentType(mimeType);
        response.setContentLength((int) fs.length());
        response.addHeader("Content-Disposition", "attachment; filename=\""
                + fs.getName() + "\"");
        OutputStream out = response.getOutputStream();
        FileInputStream in = new FileInputStream(fileServer);
        for (; ; ) {
            byte[] b = new byte[4096];
            int n = in.read(b, 0, b.length);
            if (n < 0) break;
            out.write(b, 0, n);
        }
        in.close();
        out.close();
    }

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) {
        doPost(request, response);
    }
}

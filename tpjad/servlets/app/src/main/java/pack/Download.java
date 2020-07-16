package pack;

import java.io.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

//Pentru acest servlet s-a folosit structura descrisa in documentatia cursului
//La POST/GET la aceasta ruta se va descarca fisierul cu numele "fileName" de pe disk
@WebServlet("/download")
public class Download extends HttpServlet {

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) {
        String fileName = request.getParameter("fileName");
        //Aici se initializeaza calea absoluta catre fisierul cerut
        String filePath = "C:/Users/Public/Documents/" + fileName + ".json";
        try {
            download(filePath, response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void download(String fileServer, HttpServletResponse response) throws IOException {
        //Function download este responsabila de aducerea fisierului de pe disk si crearea unui Stream
        //De date catre client
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

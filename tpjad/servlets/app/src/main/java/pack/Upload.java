package pack;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

//Acest endpoint faciliteaza interfata de incarcare a fisierului dorit
//Partile din multi-part-ul requestului sunt scrise pe disk la calea absolutia initializata mai jos
@WebServlet("/upload")
@MultipartConfig
public class Upload extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            String fileName = generateFileName();
            for (Part part : request.getParts()) {
                part.write( "C:/Users/Public/Documents/" + fileName + ".json");
                part.delete();
            }
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<html><head><title>Response</title></head>");
            out.println("<form method=\"GET\" action=\"download\" >");
            out.println("<input type=\"text\" name=\"fileName\" id=\"sort-input\" value=\"" + fileName + "\"/>");
            out.println("<input type=\"button\" id=\"sort-button\" value=\"Sort\"/>");
            out.println("<input type=\"submit\" value=\"Download\"/>");
            out.println("</form>");
            out.println("<script src=\"https://cdnjs.cloudflare.com/ajax/libs/axios/0.18.0/axios.min.js\"></script>");
            out.println("<script src=\"script.js\"></script>");
            out.println("</body></html>");
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String generateFileName() {
        return Integer.toString((int) (Math.random() * Math.pow(10, 6)));
    }

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><head><title>Response</title></head><body>");
        out.println("<form method=\"GET\" action=\"download\" >");
        out.println("<input type=\"text\" name=\"fileName\" id=\"sort-input\"/>");
        out.println("<input type=\"button\" id=\"sort-button\" value=\"Sort\"/>");
        out.println("<input type=\"submit\" value=\"Download\"/>");
        out.println("</form>");
        out.println("<script src=\"https://cdnjs.cloudflare.com/ajax/libs/axios/0.18.0/axios.min.js\"></script>");
        out.println("<script src=\"script.js\"></script>");
        out.println("</body></html>");
        out.close();
    }
}

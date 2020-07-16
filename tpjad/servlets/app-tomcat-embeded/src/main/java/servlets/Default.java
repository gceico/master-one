package servlets;

import javax.servlet.http.*;
import java.io.*;

public class Default extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            PrintWriter out = response.getWriter();
            out.println("<html><head><title>Sort</title></head>");
            out.println(("<body><form method=\"POST\" action=\"upload\" enctype=\"multipart/form-data\">"));
            out.println("<input type=\"file\" name=\"upload\" title=\"JSON File\" />");
            out.println("<input type=\"submit\" value=\"pack.Upload\" />");
            out.println("</form></body></html>");
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        doPost(request, response);
    }
}

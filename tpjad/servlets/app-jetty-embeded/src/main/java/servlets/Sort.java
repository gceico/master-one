package servlets;


import javax.servlet.http.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

import com.google.gson.Gson;

public class Sort extends HttpServlet {
    public void doPost(HttpServletRequest request,
                      HttpServletResponse response) {
        try {
            String fileName = request.getParameter("fileName");
            String filePath = "C:/Users/Sprybase/AppData/Local/Temp" + fileName + ".json";
            String sorted = sortJson(filePath);
            write(sorted, filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private String sortJson(String filePath) throws IOException {
        List<String> content = Files.readAllLines(Paths.get(filePath));
        String json = "";
        for (String line : content) {
            json += line;
        }
        Gson gson = new Gson();
        String[] list = gson.fromJson(json, String[].class);
        ArrayList<String> arrayList = new ArrayList<>();
        for (String elem : list) {
            arrayList.add(elem);
        }
        Collections.sort(arrayList);
        return gson.toJson(arrayList);
    }

    private static void write(String data, String filePath) throws IOException {
        Files.write(Paths.get(filePath), data.getBytes());
    }

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) {
        doPost(request, response);
    }
}

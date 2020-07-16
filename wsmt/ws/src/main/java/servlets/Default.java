package servlets;

import java.io.*;

import javax.servlet.http.*;
import java.util.stream.Collectors;
import com.thetransactioncompany.jsonrpc2.*;
import com.thetransactioncompany.jsonrpc2.server.*;
import handlers.*;

public class Default extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest req, HttpServletResponse res) {
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.register(new CrudHandler());

        try {
            PrintWriter out = res.getWriter();
            BufferedReader reader = req.getReader();
            String jsonString = reader.lines().collect(Collectors.joining());
            System.out.println(jsonString);
            JSONRPC2Response response = dispatcher.process(JSONRPC2Request.parse(jsonString), null);

            out.write(response.toJSONString());
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

    }

    protected void doGet(HttpServletRequest req, HttpServletResponse res) {
        doPost(req, res);
    }
}

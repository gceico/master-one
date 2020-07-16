
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import javax.servlet.MultipartConfigElement;

import servlets.Default;
import servlets.Download;
import servlets.Sort;
import servlets.Upload;

public class Main {
    public static void main(String[] args) throws Exception {
        int port = 8080;
        Server server = new Server(port);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        context.addServlet(new ServletHolder(new Default()), "/");
        context.addServlet(new ServletHolder(new Download()), "/download");
        context.addServlet(new ServletHolder(new Sort()), "/sort");

        ServletHolder fileUploadServletHolder = new ServletHolder(new Upload());
        fileUploadServletHolder.getRegistration().setMultipartConfig(new MultipartConfigElement("/data"));
        context.addServlet(fileUploadServletHolder, "/upload");

        server.setHandler(context);
        server.start();
        server.join();
    }
}

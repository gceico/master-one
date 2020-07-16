import java.io.File;

import javax.servlet.annotation.MultipartConfig;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;

import servlets.Default;
import servlets.Download;
import servlets.Sort;
import servlets.Upload;

@MultipartConfig
public class Main {
    public static void main(String[] args) throws Exception {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);
        File base = new File(System.getProperty("java.io.tmpdir"));
        Context ctx = tomcat.addContext("", base.getAbsolutePath());
        Tomcat.addServlet(ctx, "Download", new Download());
        Tomcat.addServlet(ctx, "Sort", new Sort());
        Tomcat.addServlet(ctx, "Upload", new Upload());
        Tomcat.addServlet(ctx, "Default", new Default());

        // System.out.println("Base: " + ctx.toString());
        // Wrapper defaultServlet = ctx.createWrapper();
        // defaultServlet.setName("default");
        // defaultServlet.setServletClass("org.apache.catalina.servlets.DefaultServlet");
        // defaultServlet.addInitParameter("debug", "0");
        // defaultServlet.addInitParameter("listings", "false");
        // defaultServlet.setLoadOnStartup(1);
        // ctx.addChild(defaultServlet);
        // ctx.addServletMapping("/", "default");

        ctx.addServletMapping("/download", "Download");
        ctx.addServletMapping("/upload", "Upload");
        ctx.addServletMapping("/sort", "Sort");
        ctx.addServletMapping("/", "Default");
        tomcat.setPort(8080);
        tomcat.start();
        tomcat.getServer().await();
    }

}

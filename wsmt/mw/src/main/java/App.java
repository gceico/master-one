import java.io.File;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;

import server.Default;


public class App {
    public static void main(String[] args) throws Exception {
        Tomcat tomcat = new Tomcat();
        File base = new File(System.getProperty("java.io.tmpdir"));
        Context ctx = tomcat.addContext("", base.getAbsolutePath());

        Tomcat.addServlet(ctx, "Default", new Default());
        ctx.addServletMapping("/", "Default");

        tomcat.setPort(8080);
        tomcat.start();
        tomcat.getServer().await();
    }

}

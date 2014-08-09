package demowar;

import java.io.IOException;
import demojar.DemoJar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name="DemoServlet",urlPatterns={"/index"})
public class DemoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String hcv1 = DemoJar.getHardCodedVersionInDemoLib1();
        String hcv2 = DemoJar.getHardCodedVersionInDemoLib2();
        String pomv1 = DemoJar.getVersionDefinedInPomXmlInDemoLib1();
        String pomv2 = DemoJar.getVersionDefinedInPomXmlInDemoLib2();
        resp.getWriter().println("hard coded version in demolib1: " + hcv1);
        resp.getWriter().println("hard coded version in demolib2: " + hcv2);
        resp.getWriter().println("version in pom.xml in demolib1: " + pomv1);
        resp.getWriter().println("version in pom.xml in demolib2: " + pomv2);
    }
}

package demolib2;

import java.io.InputStream;
import java.util.Properties;

public class DemoLib2
{
    public static String getHardCodedVersion() {
        return "2.0";
    }

    public static String getVersionDefinedInPomXml() {
        try {
            String path = "/META-INF/maven/maven-multimodule-demo1/demolib2/pom.properties";
            InputStream stream = DemoLib2.class.getClass().getResourceAsStream(path);
            Properties props = new Properties();
            props.load(stream);
            return props.getProperty("version");
        } catch (Exception ignore) {
            return "-";
        }
    }

    public static void main(String[] args) {
        System.out.println("hard-coded version: " + DemoLib2.getHardCodedVersion());
        System.out.println("version in pom.xml: " + DemoLib2.getVersionDefinedInPomXml());
    }
}

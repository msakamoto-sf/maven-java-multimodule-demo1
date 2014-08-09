package demolib1;

import java.io.InputStream;
import java.util.Properties;

public class DemoLib1
{
    public static String getHardCodedVersion() {
        return "1.1";
    }

    public static String getVersionDefinedInPomXml() {
        try {
            String path = "/META-INF/maven/maven-multimodule-demo1/demolib1/pom.properties";
            InputStream stream = DemoLib1.class.getClass().getResourceAsStream(path);
            Properties props = new Properties();
            props.load(stream);
            return props.getProperty("version");
        } catch (Exception ignore) {
            return "-";
        }
    }

    public static void main(String[] args) {
        System.out.println("hard-coded version: " + DemoLib1.getHardCodedVersion());
        System.out.println("version in pom.xml: " + DemoLib1.getVersionDefinedInPomXml());
    }
}

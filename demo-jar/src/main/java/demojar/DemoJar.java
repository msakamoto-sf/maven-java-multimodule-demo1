package demojar;

import demolib1.DemoLib1;
import demolib2.DemoLib2;

public class DemoJar {

    public static String getHardCodedVersionInDemoLib1() {
        return DemoLib1.getHardCodedVersion();
    }

    public static String getVersionDefinedInPomXmlInDemoLib1() {
        return DemoLib1.getVersionDefinedInPomXml();
    }

    public static String getHardCodedVersionInDemoLib2() {
        return DemoLib2.getHardCodedVersion();
    }

    public static String getVersionDefinedInPomXmlInDemoLib2() {
        return DemoLib2.getVersionDefinedInPomXml();
    }
}

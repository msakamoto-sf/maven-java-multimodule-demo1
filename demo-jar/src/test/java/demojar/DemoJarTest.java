package demojar;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

public class DemoJarTest {
    @Test
    public void testVersions() {
        assertEquals(DemoJar.getHardCodedVersionInDemoLib1(), "1.0");
        assertEquals(DemoJar.getHardCodedVersionInDemoLib2(), "2.2");
        assertEquals(DemoJar.getVersionDefinedInPomXmlInDemoLib1(), "1.0");
        assertEquals(DemoJar.getVersionDefinedInPomXmlInDemoLib2(), "1.2");
    }
}

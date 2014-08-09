package demolib2;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

public class DemoLib2Test {
    @Test
    public void testVersion() {
        assertEquals(DemoLib2.getHardCodedVersion(), "2.0");
        assertEquals(DemoLib2.getVersionDefinedInPomXml(), "-");
    }
}

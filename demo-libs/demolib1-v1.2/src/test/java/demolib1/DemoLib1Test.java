package demolib1;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

public class DemoLib1Test {
    @Test
    public void testVersion() {
        assertEquals(DemoLib1.getHardCodedVersion(), "1.2");
        assertEquals(DemoLib1.getVersionDefinedInPomXml(), "-");
    }
}

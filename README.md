maven-java-multimodule-demo1
============================

Maven Multi-Module Project Sample Demo 1


## directory structure

```
maven-java-multimodule-demo1/
  pom.xml       ... multimodule project parent pom.xml

  demo-jar/     ... multimodule project child pom.xml (jar library project)
    src/
    pom.xml

  demo-war/     ... multimodule project child pom.xml (war serlvet app project)
    src/
    pom.xml

  demo-libs/
    demolib1-v1.[0-2]/   ... sample dependency library (1).
    demolib2-v1.[0-2]/   ... sample dependency library (2).

  localrepo/    ... locally installed jars by "mvn install:install-file" for each demo-libs.
```

## build requirements

 * JDK7 or upper.
 * Maven 3.0.x or upper.

## play guide

This sample project demonstrates `<dependencyManagement>`, `<dependency>`, `<pluginManagement>` setting in pom.xml.

parent pom.xml dependency:
```xml
  <dependencyManagement>
    <dependencies>
      <dependency>
        <groupId>maven-multimodule-demo1</groupId>
        <artifactId>demolib1</artifactId>
        <!-- child projects use v1.2 by default -->
        <version>1.2</version>
      </dependency>
    </dependencies>
  </dependencyManagement>

  <!-- common dependencies -->
  <dependencies>
    <dependency>
      <groupId>maven-multimodule-demo1</groupId>
      <artifactId>demolib2</artifactId>
      <!-- child projects are ENFORCED to use v1.2 -->
      <version>1.2</version>
    </dependency>
    <dependency>
      <groupId>org.testng</groupId>
      <artifactId>testng</artifactId>
      <version>6.8.8</version>
      <scope>test</scope>
    </dependency>
  </dependencies>
```

## 0. prepare

Enter for each `demolibN-V1.[0-2]` directory, build and install by `mvn clean test package install`.

## 1. play with demo-jar

`demo-jar` pom.xml dependency:
```xml
  <dependencies>
    <dependency>
      <groupId>maven-multimodule-demo1</groupId>
      <artifactId>demolib1</artifactId>
      <!-- parent module defines 1.2, but this use v1.0 -->
      <version>1.0</version>
    </dependency>
    <!-- you can override parent's <dependencies> enforcement.
    <dependency>
      <groupId>maven-multimodule-demo1</groupId>
      <artifactId>demolib2</artifactId>
      <version>1.0</version>
    </dependency>
    -->
  </dependencies>
```

`demo-jar` source code using `demolib1` and `demolib2`:
```java
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
```

### Question

 * In parent pom.xml, `demolib1` version is 1.2, but `demo-jar` pom.xml says "I use demolib1 v1.0.".
 * In parent pom.xml, `demolib2` version is 1.2, but `demo-jar` pom.xml says "I don't not demolib2 anymore.".

Question:

 1. Finally, which version is used for `demolib1` in `demo-jar` ?
 2. Finally, which version is used for `demolib2` in `demo-jar` ?

### Answer

Enter `demo-jar` and `mvn clean test`. All tests will passed. Let's see `src/main/test/java/demojar/DemoJarTest.java`.
```java
public class DemoJarTest {
    @Test
    public void testVersions() {
        assertEquals(DemoJar.getHardCodedVersionInDemoLib1(), "1.0");
        assertEquals(DemoJar.getHardCodedVersionInDemoLib2(), "2.2");
        assertEquals(DemoJar.getVersionDefinedInPomXmlInDemoLib1(), "1.0");
        assertEquals(DemoJar.getVersionDefinedInPomXmlInDemoLib2(), "1.2");
    }
}
```

 1. Finally, `demo-jar` uses `demolib1`'s **v1.0** specified in **child pom.xml**.
 2. Finally, `demo-jar` uses `demolib2`'s **v1.2** specified in **parent pom.xml**.

### Why ?

About `demolib1` version:

 1. For `demolib1`, parent's pom.xml specifies v1.2 in `<dependencyManagement>` setting. This effects as default setting in child's pom.xml.
 2. `demo-jar` pom.xml specifies `demolib1` version setting to v1.0. This overwrite version setting in parent pom.xml.
 3. Finally, maven resolved `demolib1` version to v1.0 specified and overwritten in child pom.xml.

About `demolib2` version:

 1. For `demolib2`, parent's pom.xml specifies v1.2 in `<dependency>` setting. This is **inheritted** to child's pom.xml.
 2. There's no `demolib2` dependency in `demo-jar` pom.xml. Parent's `<dependency>` settings are implicitly inheritted.
 3. Finally, maven resolved `demolib2` version to v1.2 specified in parent pom.xml.


## 2. play with demo-war

`demo-war` pom.xml dependency:
```xml
  <dependencies>
    <!-- snipped -->
    <dependency>
      <groupId>maven-multimodule-demo1</groupId>
      <artifactId>demolib1</artifactId>
      <!-- parent module defines 1.2, but this use v1.1 -->
      <version>1.1</version>
    </dependency>
    <!-- you can override parent's <dependencies> enforcement. -->
    <dependency>
      <groupId>maven-multimodule-demo1</groupId>
      <artifactId>demolib2</artifactId>
      <version>1.0</version>
    </dependency>
  </dependencies>
```

`src/main/java/demowar/DemoServlet.java` use `demo-jar`:
```java
package demowar;
import demojar.DemoJar;
// ...
@WebServlet(name="DemoServlet",urlPatterns={"/index"})
public class DemoServlet extends HttpServlet {
// ...
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String hcv1 = DemoJar.getHardCodedVersionInDemoLib1();
        String hcv2 = DemoJar.getHardCodedVersionInDemoLib2();
// ...
    }
}
```

### Question

 * In parent pom.xml, `demolib1` version is 1.2, but `demo-war` pom.xml says "I use demolib1 v1.1.".
 * In parent pom.xml, `demolib2` version is 1.2, but `demo-war` pom.xml says "I use demolib2 v1.0.".

Graph:
```
parent pom.xml   --> demo-war pom.xml  --> demo-jar pom.xml
  demolib1-v1.2         v1.1                  v1.0
  demolib2-v1.2         v1.0                  ----
```

Question:

 1. Finally, which version is used for `demolib1` in `demo-war` ?
 2. Finally, which version is used for `demolib2` in `demo-war` ?

### Answer

 1. Enter top directory and `mvn clean test package`.
 2. Enter `demo-jar` and `mvn install`. (install demo-jar jar files into your local ".m2" repository.)
 3. Enter `demo-war` and `mvn tomcat7:run`.
 4. After tomcat started, start your favorite browser and access to `http://localhost:8090/`.

result:
```
hard coded version in demolib1: 1.1
hard coded version in demolib2: 2.0
...(snip)...
```

 1. Finally, `demo-jar` uses `demolib1`'s **v1.1** specified in **child pom.xml**.
 2. Finally, `demo-jar` uses `demolib2`'s **v1.0** specified in **parent pom.xml**.

### Why ?

About `demolib1` version:

 1. For `demolib1`, parent's pom.xml specifies v1.2 in `<dependencyManagement>` setting. This effects as default setting in child's pom.xml.
 2. `demo-war` pom.xml specifies `demolib1` version setting to v1.1. This overwrite version setting in parent pom.xml.
 3. Finally, maven resolved `demolib1` version to v1.1 specified and overwritten in child pom.xml.

About `demolib2` version:

 1. For `demolib2`, parent's pom.xml specifies v1.2 in `<dependency>` setting. This is **inheritted** to child's pom.xml.
 2. But `demo-war` pom.xml **overwrites** `demolib2` version to v1.0. Parent's `<dependency>` setting are ignored.
 3. Finally, maven resolved `demolib2` version to v1.0 specified in child pom.xml.

After `mvn package` in `demo-war`, extract `target/demo-war-1.0.war`. You can find these jars in `WEB-INF/lib/`.

 * demo-jar-1.0.jar
 * demolib1-1.1.jar
 * demolib2-1.0.jar

## References

 * Maven – Guide to Working with Multiple Modules
   * http://maven.apache.org/guides/mini/guide-multiple-modules.html
 * Maven by Example - Chapter 6. A Multi-module Project - Sonatype.com
   * http://books.sonatype.com/mvnex-book/reference/multimodule.html
 * Maven 入門 (7) | TECHSCORE(テックスコア)
   * http://www.techscore.com/tech/Java/ApacheJakarta/Maven/3-6/#maven-3-4
 * Maven Assembly Plugin をマルチモジュールプロジェクトで使う - A Memorandum
   * http://etc9.hatenablog.com/entry/20101207/1291733943
 * java - Maven: what is pluginManagement? - Stack Overflow
   * http://stackoverflow.com/questions/10483180/maven-what-is-pluginmanagement
 * java - Maven dependency management for plugin dependencies - Stack Overflow
   * http://stackoverflow.com/questions/11254356/maven-dependency-management-for-plugin-dependencies



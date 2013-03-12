Following project demonstrates a problem (?) with classloading in Groovy 2.0.

Invoking `groovyClassLoader.getResource("groovy")` returns `null`, but when using Groovy 1.8 it returns URL to the jar file from classpath (groovy-all).

Interesting thing is that `groovy-all-2.0.4` which is used in the failing example has similar structure to the 1.8 version.

 * Use `mvn clean package` to run against Groovy 1.8 (tests will pass)
 * Use `mvn clean package -Pgroovy20` to run against Groovy 2.0 (one test will fail)

```
mvn -version
Apache Maven 3.0.3 (r1075438; 2011-02-28 18:31:09+0100)
Maven home: /usr/local/apache-maven/apache-maven-3.0.3
Java version: 1.7.0_15, vendor: Oracle Corporation
Java home: /usr/lib/jvm/java-7-openjdk-amd64/jre
Default locale: en_US, platform encoding: UTF-8
OS name: "linux", version: "3.2.0-38-generic", arch: "amd64", family: "unix"
```
The same behaviour under JDK6 from Oracle.

### TL;DR : It's not a bug! It's a feature - http://bugs.sun.com/view_bug.do?bug_id=4761949

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

If you use repackaged JAR included in this repositry (i.e. by using `<systemPath>` for dependency in Maven) this test will pass.


### Evaluation

I came across this behaviour while working on Groovy 2.0 support for Spock Test Runner. First I thought that there is some essential difference in the MANIFEST file. But after repacking (jar xvf and then jar cvf) including the same manifest file the test was passing. Here's the isolated problem shared as mvn project on github.
Interestingly enough those two JARs are identical in terms of the content (at least based on what pkgdiff is showing), but they were still different in the size. Then I tried zipinfo, and here's when I saw the real problem. Original groovy JAR was packaged without the directory entries, and therefore, according to this bug evaluation from Sun the JAR is not found through the classLoader.getResource() call. To quote:
In general, Class.getResource() is intended to access file based resources
(on the filesystem, or from jar files or wherever..) It is not specified
what the effect of accessing a directory entry is, and therefore this
behavior can not be expected to be the same across different URL schemes.
As a solution we should scan all the jars from the classpath directly in case when classLoader.getResource() will return ambiguous null (as it can also mean that we don't have sufficient privileges according to the javadoc).

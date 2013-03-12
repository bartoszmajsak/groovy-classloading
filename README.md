Following project demonstrates a problem (?) with classloading in Groovy 2.0.

Invoking `groovyClassLoader.getResource("groovy")` returns `null`, but when using Groovy 1.8 it returns URL to the jar file from classpath (groovy-all).

Interesting thing is that `groovy-all-2.0.4` which is used in the failing example has similar structure to the 1.8 version.

 * Use `mvn clean package` to run against Groovy 1.8 (tests will pass)
 * Use `mvn clean package -Pgroovy20` to run against Groovy 2.0 (one test will fail)

package com.bartoszmajsak.groovy;

import static org.fest.assertions.Assertions.assertThat;
import groovy.lang.GroovyClassLoader;

import java.net.URL;

import org.junit.Test;

public class ClassLoadingTest
{

   private final ClassLoader groovyClassLoader = new GroovyClassLoader(this.getClass().getClassLoader());

   @Test
   public void should_load_spock_package() throws Exception
   {
      // when
      URL resource = groovyClassLoader.getResource("spock");

      // then
      assertThat(resource).isNotNull();
   }

   @Test
   public void should_load_groovy_package() throws Exception
   {
      // when
      URL resource = groovyClassLoader.getResource("groovy");

      // then
      assertThat(resource).isNotNull();
   }

}

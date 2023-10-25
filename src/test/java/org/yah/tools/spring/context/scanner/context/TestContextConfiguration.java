package org.yah.tools.spring.context.scanner.context;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class TestContextConfiguration {
    public static ConfigurableApplicationContext createContext() {
        return new AnnotationConfigApplicationContext(TestContextConfiguration.class);
    }
}

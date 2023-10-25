package org.yah.tools.spring.context.scanner.context;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@SuppressWarnings({"unused", "FieldCanBeLocal"})
@Configuration
@ComponentScan
public class TestContextConfiguration {

    @Component("A")
    public static class A {
    }

    @Component("B")
    public static class B {
        private final A a;

        public B(A a) {
            this.a = a;
        }
    }

    @Component("C")
    public static class C {
        private final A a;
        private final B b;

        public C(A a, B b) {
            this.a = a;
            this.b = b;
        }
    }

    @Component("D")
    public static class D {
        private final B b;

        public D(B b) {
            this.b = b;
        }
    }

    @Component("E")
    public static class E {
        private final B b;
        private final D d;

        public E(B b, D d) {
            this.b = b;
            this.d = d;
        }
    }

    public static ConfigurableApplicationContext createContext() {
        return new AnnotationConfigApplicationContext(TestContextConfiguration.class);
    }
}

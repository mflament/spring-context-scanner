package org.yah.tools.spring.context.scanner.context.components;

import org.springframework.stereotype.Component;

@Component
public class B {
    private final A a;

    public B(A a) {
        this.a = a;
    }
}

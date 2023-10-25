package org.yah.tools.spring.context.scanner.context.components;

import org.springframework.stereotype.Component;

@Component
public class C {
    private final A a;
    private final B b;

    public C(A a, B b) {
        this.a = a;
        this.b = b;
    }
}

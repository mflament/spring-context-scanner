package org.yah.tools.spring.context.scanner.context.components;

import org.springframework.stereotype.Component;

@Component
public class D {
    private final B b;

    public D(B b) {
        this.b = b;
    }
}

package org.yah.tools.spring.context.scanner.context.components;

import org.springframework.stereotype.Component;

@Component
public class E {
    private final B b;
    private final D d;

    public E(B b, D d) {
        this.b = b;
        this.d = d;
    }
}

package org.yah.tools.spring.context.scanner;

import guru.nidi.graphviz.attribute.Color;
import guru.nidi.graphviz.attribute.Shape;
import guru.nidi.graphviz.attribute.Style;
import guru.nidi.graphviz.model.Factory;
import guru.nidi.graphviz.model.Node;

import java.util.function.Function;

public final class GraphNodeFactories {
    private GraphNodeFactories() {
    }

    public static Function<NamedBeanDefinition, Node> defaultNodeFactory() {
        return beanDefinition -> Factory.node(beanDefinition.beanName())
                    .with(Shape.OVAL)
                    .with(Color.BLACK)
                    .with(beanDefinition.isAbstract() ? Style.STRIPED : Style.SOLID);
    }
}

package org.yah.tools.spring.context.scanner;

import guru.nidi.graphviz.model.Graph;
import guru.nidi.graphviz.model.Node;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public final class ScanOptions {

    @Nonnull
    private final ConfigurableListableBeanFactory beanFactory;

    @Nonnull
    private Predicate<NamedBeanDefinition> beanPredicate = BeanPredicates.defaultBeanPredicate();

    @Nonnull
    private Function<NamedBeanDefinition, Node> nodeFactory = GraphNodeFactories.defaultNodeFactory();

    @Nonnull
    private String graphName = "SpringContext";

    @Nonnull
    private List<GraphWriter> graphWriters = List.of(SVGGraphWriter.builder().build());

    @Nullable
    private Duration timeout;

    @Nullable
    private UnaryOperator<Graph> graphCustomizer;

    public ScanOptions(@Nonnull ConfigurableApplicationContext context) {
        this(Objects.requireNonNull(context, "context is null").getBeanFactory());
        graphName = context.getApplicationName();
    }

    public ScanOptions(@Nonnull ConfigurableListableBeanFactory beanFactory) {
        this.beanFactory = Objects.requireNonNull(beanFactory, "beanFactory is null");
    }

    @Nonnull
    public ConfigurableListableBeanFactory beanFactory() {
        return beanFactory;
    }

    @Nonnull
    public Predicate<NamedBeanDefinition>  beanPredicate() {
        return beanPredicate;
    }

    @Nonnull
    public Function<NamedBeanDefinition, Node> nodeFactory() {
        return nodeFactory;
    }

    @Nonnull
    public String graphName() {
        return graphName;
    }

    @Nullable
    public Duration timeout() {
        return timeout;
    }

    @Nullable
    public UnaryOperator<Graph>  graphCustomizer() {
        return graphCustomizer;
    }

    @Nonnull
    public List<GraphWriter> graphWriters() {
        return graphWriters;
    }

    @Nonnull
    public ScanOptions beanPredicate(@Nonnull Predicate<NamedBeanDefinition>  beanPredicate) {
        this.beanPredicate = Objects.requireNonNull(beanPredicate, "beanPredicate is null");
        return this;
    }

    @Nonnull
    public ScanOptions nodeFactory(@Nonnull Function<NamedBeanDefinition, Node> nodeFactory) {
        this.nodeFactory = Objects.requireNonNull(nodeFactory, "nodeFactory is null");
        return this;
    }

    @Nonnull
    public ScanOptions graphName(@Nonnull String graphName) {
        this.graphName = Objects.requireNonNull(graphName, "graphName is null");
        return this;
    }

    @Nonnull
    public ScanOptions timeout(@Nullable Duration timeout) {
        this.timeout = timeout;
        return this;
    }

    @Nonnull
    public ScanOptions graphCustomizer(@Nullable UnaryOperator<Graph>  graphCustomizer) {
        this.graphCustomizer = graphCustomizer;
        return this;
    }

    @Nonnull
    public ScanOptions graphWriters(GraphWriter... graphWriters) {
        this.graphWriters = Arrays.asList(graphWriters);
        return this;
    }
}

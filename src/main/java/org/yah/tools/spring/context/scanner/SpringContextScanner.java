package org.yah.tools.spring.context.scanner;

import guru.nidi.graphviz.attribute.Font;
import guru.nidi.graphviz.attribute.Rank;
import guru.nidi.graphviz.attribute.Rank.RankDir;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.engine.GraphvizCmdLineEngine;
import guru.nidi.graphviz.model.Factory;
import guru.nidi.graphviz.model.Graph;
import guru.nidi.graphviz.model.Node;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import java.io.IOException;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

public final class SpringContextScanner {

    private SpringContextScanner() {
    }

    public static void scan(ScanOptions options) throws IOException {
        ConfigurableListableBeanFactory beanFactory = options.beanFactory();

        // filter and map bean definitions by name
        Map<String, NamedBeanDefinition> beanDefinitions = new HashMap<>();
        Arrays.stream(beanFactory.getBeanDefinitionNames())
                .map(name -> new NamedBeanDefinition(name, beanFactory.getBeanDefinition(name)))
                .filter(options.beanPredicate())
                .forEach(nbd -> beanDefinitions.put(nbd.beanName(), nbd));

        // dependency name to beans depending on this dependency
        Map<String, List<NamedBeanDefinition>> dependencies = new HashMap<>(beanDefinitions.size());
        for (NamedBeanDefinition beanDefinition : beanDefinitions.values()) {
            String[] dependencyNames = beanFactory.getDependenciesForBean(beanDefinition.beanName());
            for (String dependencyName : dependencyNames) {
                List<NamedBeanDefinition> depDependsOn = dependencies.computeIfAbsent(dependencyName, name -> new ArrayList<>());
                if (!beanDefinitions.containsKey(dependencyName)) {
                    BeanDefinition dependencyDefinition = beanFactory.getBeanDefinition(dependencyName);
                    beanDefinitions.put(dependencyName, new NamedBeanDefinition(dependencyName, dependencyDefinition));
                }
                depDependsOn.add(beanDefinition);
            }
        }

        System.out.println(beanDefinitions.keySet());
        dependencies.forEach((name, deps) -> System.out.printf("%s: %s%n", name, deps.stream().map(NamedBeanDefinition::beanName).collect(Collectors.joining(", "))));

        Function<NamedBeanDefinition, Node> nodeFactory = options.nodeFactory();
        Map<String, Node> nodes = new HashMap<>(dependencies.size());
        for (NamedBeanDefinition beanDefinition : beanDefinitions.values()) {
            Node node = nodeFactory.apply(beanDefinition);
            nodes.put(beanDefinition.beanName(), node);
        }
        for (Map.Entry<String, List<NamedBeanDefinition>> entry : dependencies.entrySet()) {
            String dependencyName = entry.getKey();
            Node dependencyNode = nodes.get(dependencyName);
            List<NamedBeanDefinition> dependentBeans = entry.getValue();
            for (NamedBeanDefinition dependentBean : dependentBeans) {
                String dependentName = dependentBean.beanName();
                Node dependantNode = nodes.get(dependentName);
                nodes.replace(dependentName, dependantNode.link(dependencyNode));
            }
        }

        Graph graph = Factory.graph(options.graphName())
                .directed()
                .graphAttr().with(Rank.dir(RankDir.LEFT_TO_RIGHT))
                .nodeAttr().with(Font.name("arial"))
                .linkAttr().with("class", "link-class")
                .with(new ArrayList<>(nodes.values()));
        UnaryOperator<Graph> graphCustomizer = options.graphCustomizer();
        if (graphCustomizer != null)
            graph = graphCustomizer.apply(graph);

        try (GraphvizCmdLineEngine engine = new GraphvizCmdLineEngine()) {
            Duration timeout = options.timeout();
            if (timeout == null)
                timeout = Duration.of(200L * beanDefinitions.size(), ChronoUnit.MILLIS);
            int amount = (int) timeout.toMillis();
            if (amount < 0) amount = Integer.MAX_VALUE;
            engine.timeout(amount, TimeUnit.MILLISECONDS);
            Graphviz.useEngine(engine);
            Graphviz graphviz = Graphviz.fromGraph(graph);
            List<GraphWriter> graphWriters = options.graphWriters();
            for (GraphWriter graphWriter : graphWriters) {
                graphWriter.saveGraph(graphviz);
            }
        }
    }

}

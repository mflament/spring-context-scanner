package org.yah.tools.spring.context.scanner;

import guru.nidi.graphviz.engine.Graphviz;

import java.io.IOException;

@FunctionalInterface
public interface GraphWriter {

    void saveGraph(Graphviz graphviz) throws IOException;

}

package org.yah.tools.spring.context.scanner;

import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class SVGGraphWriter implements GraphWriter {
    private final File path;
    private final Integer width;
    private final Integer height;

    private SVGGraphWriter(Builder builder) {
        this.path = builder.path;
        this.width = builder.width;
        this.height = builder.height;
    }

    @Override
    public void saveGraph(Graphviz graphviz) throws IOException {
        if (width != null) graphviz = graphviz.width(width);
        if (height != null) graphviz = graphviz.height(height);
        graphviz.render(Format.SVG).toFile(path);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private File path = new File("spring-context.svg");
        private Integer width;
        private Integer height = 800;

        private Builder() {
        }

        public Builder path(File path) {
            this.path = Objects.requireNonNull(path, "path is null");
            return this;
        }

        public Builder width(Integer width) {
            this.width = width;
            return this;
        }

        public Builder height(Integer height) {
            this.height = height;
            return this;
        }

        public SVGGraphWriter build() {
            return new SVGGraphWriter(this);
        }
    }
}

# spring-context-scanner

Scan [spring beans context](https://docs.spring.io/spring-framework/reference/core.html) and generate graph
using [graphiz](https://graphviz.org/download/) thanks its [java API](https://github.com/nidi3/graphviz-java).

## Usage
A simple example can be found [here](./src/test/java/org/yah/tools/spring/context/scanner/SpringContextScannerTest.java).
That will generate this output SVG :
![output SVG](./spring-context.svg)
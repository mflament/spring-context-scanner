package org.yah.tools.spring.context.scanner;

import guru.nidi.graphviz.model.Factory;
import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.yah.tools.spring.context.scanner.context.TestContextConfiguration;

import java.io.IOException;

class SpringContextScannerTest {

    @Test
    void test() throws IOException {
        ConfigurableApplicationContext context = TestContextConfiguration.createContext();
        ScanOptions options = new ScanOptions(context);
        SpringContextScanner.scan(options);
    }
}
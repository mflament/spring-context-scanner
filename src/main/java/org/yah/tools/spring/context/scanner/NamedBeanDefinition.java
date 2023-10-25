package org.yah.tools.spring.context.scanner;

import org.springframework.beans.factory.config.BeanDefinition;

public record NamedBeanDefinition(String beanName, BeanDefinition definition) {
    public String beanClassName() {
        return definition.getBeanClassName();
    }

    public boolean isPrimary() {
        return definition.isPrimary();
    }

    public boolean isSingleton() {
        return definition.isSingleton();
    }

    public boolean isPrototype() {
        return definition.isPrototype();
    }

    public boolean isAbstract() {
        return definition.isAbstract();
    }
}

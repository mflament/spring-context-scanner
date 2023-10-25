package org.yah.tools.spring.context.scanner;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public final class BeanPredicates {

    private BeanPredicates() {
    }

    public static Predicate<NamedBeanDefinition> all() {
        return b -> true;
    }

    public static Predicate<NamedBeanDefinition> classNameStartingWith(String prefix) {
        return nbd -> {
            String beanClassName = nbd.beanClassName();
            return beanClassName != null && beanClassName.startsWith(prefix);
        };
    }

    public static Predicate<NamedBeanDefinition> classNameMatching(String pattern) {
        return classNameMatching(Pattern.compile(pattern));
    }

    public static Predicate<NamedBeanDefinition> classNameMatching(Pattern pattern) {
        return nbd -> {
            String beanClassName = nbd.beanClassName();
            return beanClassName != null && pattern.matcher(beanClassName).matches();
        };
    }

    public static Predicate<NamedBeanDefinition> noSpringBeans() {
        return classNameStartingWith("org.springframework").negate();
    }

    public static Predicate<NamedBeanDefinition> noSpringConfiguration() {
        return annotatedWith(Configuration.class).negate();
    }

    public static Predicate<NamedBeanDefinition> annotatedWith(Class<? extends Annotation> annotationType) {
        return beanDefinition -> {
            String beanClassName = beanDefinition.beanClassName();
            Class<?> beanClass;
            try {
                beanClass = Class.forName(beanClassName);
            } catch (ClassNotFoundException e) {
                return false;
            }
            return AnnotationUtils.findAnnotation(beanClass, annotationType) != null;
        };
    }

    public static Predicate<NamedBeanDefinition> defaultBeanPredicate() {
        return noSpringBeans().and(noSpringConfiguration());
    }
}

package top.jinjinz.spring.context.annotation;

import top.jinjinz.spring.beans.factory.annotation.AnnotatedBeanDefinition;
import top.jinjinz.spring.beans.factory.config.BeanDefinition;
import top.jinjinz.spring.beans.factory.support.BeanDefinitionRegistry;

/**
 * 注解bean类的注册器
 * @author jinjin
 * @date 2019-04-15
 */
public class AnnotatedBeanDefinitionReader {

    private final BeanDefinitionRegistry registry;

    public AnnotatedBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    public void register(Class<?>... annotatedClasses) {
        for (Class<?> annotatedClass : annotatedClasses) {
            registerBean(annotatedClass);
        }
    }

    public void registerBean(Class<?> annotatedClass) {
        doRegisterBean(annotatedClass);
    }

    private <T> void doRegisterBean(Class<T> annotatedClass){
        registry.registerBeanDefinition(annotatedClass.getName(),doCreateBeanDefinition(
                toLowerFirstCase(annotatedClass.getSimpleName()),annotatedClass.getName()));
        Class<?> [] interfaces = annotatedClass.getInterfaces();
        for (Class<?> i : interfaces) {
            registry.registerBeanDefinition(i.getName(),doCreateBeanDefinition(i.getName(),annotatedClass.getName()));
        }
    }

    private BeanDefinition doCreateBeanDefinition(String factoryBeanName, String beanClassName){
        BeanDefinition beanDefinition = new AnnotatedBeanDefinition();

        beanDefinition.setBeanClassName(beanClassName);
        beanDefinition.setFactoryBeanName(factoryBeanName);
        return beanDefinition;
    }

    private String toLowerFirstCase(String simpleName) {
        char [] chars = simpleName.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }
}

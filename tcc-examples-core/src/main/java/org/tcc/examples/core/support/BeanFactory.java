package org.tcc.examples.core.support;

public interface BeanFactory {
	
    <T> T getBean(Class<T> var1);
    
    <T> boolean isFactoryOf(Class<T> clazz);
    
}

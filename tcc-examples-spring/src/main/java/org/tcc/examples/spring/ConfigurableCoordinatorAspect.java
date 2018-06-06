package org.tcc.examples.spring;

import org.aspectj.lang.annotation.Aspect;

import org.tcc.examples.core.interceptor.ResourceCoordinatorAspect;
import org.tcc.examples.core.interceptor.ResourceCoordinatorInterceptor;
import org.tcc.examples.core.support.TransactionConfigurator;

import org.springframework.core.Ordered;


@Aspect
public class ConfigurableCoordinatorAspect extends ResourceCoordinatorAspect implements Ordered {

    private TransactionConfigurator transactionConfigurator;

    public void init() {

        ResourceCoordinatorInterceptor resourceCoordinatorInterceptor = new ResourceCoordinatorInterceptor();
        resourceCoordinatorInterceptor.setTransactionManager(transactionConfigurator.getTransactionManager());
        this.setResourceCoordinatorInterceptor(resourceCoordinatorInterceptor);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 1;
    }

    public void setTransactionConfigurator(TransactionConfigurator transactionConfigurator) {
        this.transactionConfigurator = transactionConfigurator;
    }
}

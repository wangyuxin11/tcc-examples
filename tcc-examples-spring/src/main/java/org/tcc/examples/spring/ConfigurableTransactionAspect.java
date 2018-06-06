package org.tcc.examples.spring;

import org.aspectj.lang.annotation.Aspect;

import org.tcc.examples.core.TransactionManager;
import org.tcc.examples.core.interceptor.CompensableTransactionAspect;
import org.tcc.examples.core.interceptor.CompensableTransactionInterceptor;
import org.tcc.examples.core.support.TransactionConfigurator;

import org.springframework.core.Ordered;


@Aspect
public class ConfigurableTransactionAspect extends CompensableTransactionAspect implements Ordered {

    private TransactionConfigurator transactionConfigurator;

    public void init() {

        TransactionManager transactionManager = transactionConfigurator.getTransactionManager();

        CompensableTransactionInterceptor compensableTransactionInterceptor = new CompensableTransactionInterceptor();
        compensableTransactionInterceptor.setTransactionManager(transactionManager);
        compensableTransactionInterceptor.setDelayCancelExceptions(transactionConfigurator.getRecoverConfig().getDelayCancelExceptions());

        this.setCompensableTransactionInterceptor(compensableTransactionInterceptor);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    public void setTransactionConfigurator(TransactionConfigurator transactionConfigurator) {
        this.transactionConfigurator = transactionConfigurator;
    }
}
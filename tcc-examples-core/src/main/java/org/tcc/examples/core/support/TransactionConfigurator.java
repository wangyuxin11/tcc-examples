package org.tcc.examples.core.support;

import org.tcc.examples.core.TransactionManager;
import org.tcc.examples.core.TransactionRepository;
import org.tcc.examples.core.recover.RecoverConfig;

public interface TransactionConfigurator {

    TransactionManager getTransactionManager();

    TransactionRepository getTransactionRepository();

    RecoverConfig getRecoverConfig();
}

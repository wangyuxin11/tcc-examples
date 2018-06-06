package org.tcc.examples.spring.support;

import org.tcc.examples.core.TransactionManager;
import org.tcc.examples.core.TransactionRepository;
import org.tcc.examples.core.recover.RecoverConfig;
import org.tcc.examples.core.repository.CachableTransactionRepository;
import org.tcc.examples.spring.recover.DefaultRecoverConfig;
import org.tcc.examples.core.support.TransactionConfigurator;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class SpringTransactionConfigurator implements TransactionConfigurator {

    private static volatile ExecutorService executorService = null;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired(required = false)
    private RecoverConfig recoverConfig = DefaultRecoverConfig.INSTANCE;


    private TransactionManager transactionManager;

    public void init() {
        transactionManager = new TransactionManager();
        transactionManager.setTransactionRepository(transactionRepository);

        if (executorService == null) {

            synchronized (SpringTransactionConfigurator.class) {

                if (executorService == null) {
//                    executorService = new ThreadPoolExecutor(recoverConfig.getAsyncTerminateThreadPoolSize(),
//                            recoverConfig.getAsyncTerminateThreadPoolSize(),
//                            0L, TimeUnit.SECONDS,
//                            new SynchronousQueue<Runnable>());
                    executorService = Executors.newCachedThreadPool();
                }
            }
        }

        transactionManager.setExecutorService(executorService);

        if (transactionRepository instanceof CachableTransactionRepository) {
            ((CachableTransactionRepository) transactionRepository).setExpireDuration(recoverConfig.getRecoverDuration());
        }
    }

    public TransactionManager getTransactionManager() {
        return transactionManager;
    }

    public TransactionRepository getTransactionRepository() {
        return transactionRepository;
    }

    public RecoverConfig getRecoverConfig() {
        return recoverConfig;
    }
}

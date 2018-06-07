package org.tcc.examples.test.client;

import org.tcc.examples.api.TransactionContext;
import org.tcc.examples.test.thirdservice.AccountRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;


@Service
public class AccountRecordServiceProxy {


    @Autowired
    private AccountRecordService accountRecordService;

    private ExecutorService executorService = Executors.newFixedThreadPool(100);

    public void record(final TransactionContext transactionContext, final long accountId, final int amount) {
//        Future<Boolean> future = this.executorService
//                .submit(new Callable<Boolean>() {
//                    @Override
//                    public Boolean call() throws Exception {
//                        accountRecordService.record(transactionContext, accountId, amount);
//                        return true;
//                    }
//                });
//
//        handleResult(future);

        accountRecordService.record(transactionContext, accountId, amount);

    }

    private void handleResult(Future<Boolean> future) {
        while (!future.isDone()) {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            future.get();
        } catch (InterruptedException e) {
            throw new Error(e);
        } catch (ExecutionException e) {
            throw new Error(e);
        }
    }
}

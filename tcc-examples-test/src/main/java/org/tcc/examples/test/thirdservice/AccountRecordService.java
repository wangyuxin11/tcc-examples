package org.tcc.examples.test.thirdservice;

import org.tcc.examples.api.TransactionContext;

public interface AccountRecordService {
	
    public void record(TransactionContext transactionContext, long accountId, int amount);

    void recordConfirm(TransactionContext transactionContext, long accountId, int amount);

    void recordCancel(TransactionContext transactionContext, long accountId, int amount);
    
}

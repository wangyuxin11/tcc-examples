package org.tcc.examples.test.thirdservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tcc.examples.api.Compensable;
import org.tcc.examples.api.TransactionContext;
import org.tcc.examples.test.entity.AccountRecord;
import org.tcc.examples.test.entity.AccountStatus;
import org.tcc.examples.test.repository.AccountRecordRepository;
import org.tcc.examples.test.utils.UnitTest;


@Service
public class AccountRecordServiceImpl implements AccountRecordService {

    @Autowired
    AccountRecordRepository accountRecordRepository;

    @Compensable(confirmMethod = "recordConfirm", cancelMethod = "recordCancel")
    public void record(TransactionContext transactionContext, long accountId, int amount) {

        System.out.println("record");

        AccountRecord accountRecord = accountRecordRepository.findById(accountId);
        accountRecord.setBalanceAmount(amount);
        accountRecord.setStatusId(AccountStatus.TRANSFERING.getId());

        if (UnitTest.TRYING_EXCEPTION) {
            throw new RuntimeException("record try failed.");
        }
    }

    public void recordConfirm(TransactionContext transactionContext, long accountId, int amount) {
        System.out.println("recordConfirm");
        AccountRecord accountRecord = accountRecordRepository.findById(accountId);
        accountRecord.setStatusId(AccountStatus.NORMAL.getId());
    }

    public void recordCancel(TransactionContext transactionContext, long accountId, int amount) {
        System.out.println("recordCancel");

        if (UnitTest.TRYING_EXCEPTION) {
            throw new RuntimeException("record cancel failed.");
        }

        AccountRecord accountRecord = accountRecordRepository.findById(accountId);
        accountRecord.setBalanceAmount(accountRecord.getBalanceAmount() - amount);
        accountRecord.setStatusId(AccountStatus.NORMAL.getId());


    }
}

package org.tcc.examples.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tcc.examples.api.Compensable;
import org.tcc.examples.api.Propagation;
import org.tcc.examples.api.TransactionContext;
import org.tcc.examples.test.client.AccountRecordServiceProxy;
import org.tcc.examples.test.entity.AccountStatus;
import org.tcc.examples.test.entity.SubAccount;
import org.tcc.examples.test.repository.SubAccountRepository;
import org.tcc.examples.test.utils.UnitTest;


@Service
public class AccountServiceImpl implements AccountService {


    @Autowired
    AccountRecordServiceProxy accountRecordServiceProxy;

    @Autowired
    SubAccountRepository subAccountRepository;

    @Compensable(confirmMethod = "transferFromConfirm", cancelMethod = "transferFromCancel")
    public void transferFrom(TransactionContext transactionContext, long accountId, int amount) {
        System.out.println("transferFrom called");
        SubAccount subAccount = subAccountRepository.findById(accountId);
        subAccount.setStatus(AccountStatus.TRANSFERING.getId());
        subAccount.setBalanceAmount(subAccount.getBalanceAmount() - amount);
        accountRecordServiceProxy.record(null, accountId, amount);
    }

    @Compensable(propagation = Propagation.REQUIRED, confirmMethod = "transferToConfirm", cancelMethod = "transferToCancel")
    public void transferTo(TransactionContext transactionContext, long accountId, int amount) {

        System.out.println("transferTo called");
        SubAccount subAccount = subAccountRepository.findById(accountId);
        subAccount.setStatus(AccountStatus.TRANSFERING.getId());
        subAccount.setBalanceAmount(subAccount.getBalanceAmount() + amount);
    }

    @Compensable(confirmMethod = "transferFromConfirm", cancelMethod = "transferFromCancel")
    public void transferFromWithMultipleTier(TransactionContext transactionContext, long accountId, int amount) {
        System.out.println("transferFromWithMultipleTier called");
        SubAccount subAccount = subAccountRepository.findById(accountId);
        subAccount.setStatus(AccountStatus.TRANSFERING.getId());
        subAccount.setBalanceAmount(subAccount.getBalanceAmount() + amount);
        accountRecordServiceProxy.record(null, accountId, amount);
    }

    @Compensable(confirmMethod = "transferToConfirm", cancelMethod = "transferToCancel")
    public void transferToWithMultipleTier(TransactionContext transactionContext, long accountId, int amount) {

        System.out.println("transferToWithMultipleTier called");

        SubAccount subAccount = subAccountRepository.findById(accountId);
        subAccount.setStatus(AccountStatus.TRANSFERING.getId());
        subAccount.setBalanceAmount(subAccount.getBalanceAmount() + amount);

        accountRecordServiceProxy.record(null, accountId, amount);
    }

    @Compensable(propagation = Propagation.REQUIRES_NEW, confirmMethod = "transferToConfirmWithNoTransactionContext", cancelMethod = "transferToCancelWithNoTransactionContext")
    public void transferToWithNoTransactionContext(long accountId, int amount) {

        System.out.println("transferToWithNoTransactionContext called");
        SubAccount subAccount = subAccountRepository.findById(accountId);
        subAccount.setStatus(AccountStatus.TRANSFERING.getId());
        subAccount.setBalanceAmount(subAccount.getBalanceAmount() + amount);
        accountRecordServiceProxy.record(null, accountId, amount);
    }


    public void transferFromConfirm(TransactionContext transactionContext, long accountId, int amount) {
        System.out.println("transferFromConfirm called");
        SubAccount subAccount = subAccountRepository.findById(accountId);
        subAccount.setStatus(AccountStatus.NORMAL.getId());
    }

    public void transferFromCancel(TransactionContext transactionContext, long accountId, int amount) {
        System.out.println("transferFromCancel called");
        SubAccount subAccount = subAccountRepository.findById(accountId);

        if (subAccount.getStatus() == AccountStatus.TRANSFERING.getId()) {

            subAccount.setStatus(AccountStatus.NORMAL.getId());
            subAccount.setBalanceAmount(subAccount.getBalanceAmount() + amount);
        }
    }

    public void transferToConfirm(TransactionContext transactionContext, long accountId, int amount) {
        System.out.println("transferToConfirm called");

        if (UnitTest.CONFIRMING_EXCEPTION) {
            throw new RuntimeException("transferToConfirm confirm failed.");
        }

        SubAccount subAccount = subAccountRepository.findById(accountId);
        subAccount.setStatus(AccountStatus.NORMAL.getId());
    }

    public void transferToCancel(TransactionContext transactionContext, long accountId, int amount) {
        System.out.println("transferToCancel called");

        SubAccount subAccount = subAccountRepository.findById(accountId);

        if (subAccount.getStatus() == AccountStatus.TRANSFERING.getId()) {

            subAccount.setStatus(AccountStatus.NORMAL.getId());
            subAccount.setBalanceAmount(subAccount.getBalanceAmount() - amount);
        }
    }

    public void transferToConfirmWithNoTransactionContext(long accountId, int amount) {
        System.out.println("transferToConfirmWithNoTransactionContext called");
        SubAccount subAccount = subAccountRepository.findById(accountId);
        subAccount.setStatus(AccountStatus.NORMAL.getId());
    }

    public void transferToCancelWithNoTransactionContext(long accountId, int amount) {
        System.out.println("transferToCancelWithNoTransactionContext called");

        SubAccount subAccount = subAccountRepository.findById(accountId);

        if (subAccount.getStatus() == AccountStatus.TRANSFERING.getId()) {

            subAccount.setStatus(AccountStatus.NORMAL.getId());
            subAccount.setBalanceAmount(subAccount.getBalanceAmount() - amount);
        }
    }

}

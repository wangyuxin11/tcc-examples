package org.tcc.examples.test.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.tcc.examples.api.Compensable;
import org.tcc.examples.api.Propagation;
import org.tcc.examples.test.entity.AccountStatus;
import org.tcc.examples.test.entity.SubAccount;
import org.tcc.examples.test.repository.SubAccountRepository;


@Service
public class TransferService {

    @Autowired
    AccountServiceProxy accountService;

    @Autowired
    SubAccountRepository subAccountRepository;

    public TransferService() {
    }


    @Compensable
    @Transactional
    public void performenceTuningTransfer() {
        accountService.performanceTuningTransferTo(null);
    }

    @Compensable(propagation = Propagation.MANDATORY)
    public void transferWithMandatoryPropagation(long fromAccountId, long toAccountId, int amount) {
        System.out.println("transfer called");

        SubAccount subAccount = subAccountRepository.findById(fromAccountId);

        subAccount.setStatus(AccountStatus.TRANSFERING.getId());

        subAccount.setBalanceAmount(subAccount.getBalanceAmount() - amount);

        accountService.transferTo(null, toAccountId, amount);
    }

    @Compensable(confirmMethod = "transferConfirm", cancelMethod = "transferCancel")
    @Transactional
    public void transfer(long fromAccountId, long toAccountId, int amount) {

        System.out.println("transfer called");

        SubAccount subAccount = subAccountRepository.findById(fromAccountId);

        subAccount.setStatus(AccountStatus.TRANSFERING.getId());

        subAccount.setBalanceAmount(subAccount.getBalanceAmount() - amount);

        accountService.transferTo(null, toAccountId, amount);
    }

    @Compensable(confirmMethod = "transferConfirm", cancelMethod = "transferCancel")
    public void transferWithMultipleTier(long fromAccountId, long toAccountId, int amount) {

        System.out.println("transferWithMultipleTier called");

        SubAccount subAccount = subAccountRepository.findById(fromAccountId);

        subAccount.setStatus(AccountStatus.TRANSFERING.getId());

        subAccount.setBalanceAmount(subAccount.getBalanceAmount() - amount);

        accountService.transferToWithMultipleTier(null, toAccountId, amount);
    }

    @Compensable(confirmMethod = "transferWithMultipleConsumerConfirm", cancelMethod = "transferWithMultipleConsumerCancel")
    @Transactional
    public void transferWithMultipleConsumer(long fromAccountId, long toAccountId, int amount) {
        System.out.println("transferWithMultipleConsumer called");
        accountService.transferFrom(null, fromAccountId, amount);
        accountService.transferTo(null, toAccountId, amount);
    }

    @Compensable
    public void transferWithOnlyTryAndMultipleConsumer(long fromAccountId, long toAccountId, int amount) {
        System.out.println("transferWithOnlyTryAndMultipleConsumer called");
        accountService.transferFrom(null, fromAccountId, amount);
        accountService.transferTo(null, toAccountId, amount);
    }

    @Compensable
    public void transferWithNoTransactionContext(long fromAccountId, long toAccountId, int amount) {
        System.out.println("transferWithNoTransactionContext called");
        accountService.transferTo(toAccountId, amount);
    }

    public void transferConfirm(long fromAccountId, long toAccountId, int amount) {
        System.out.println("transferConfirm called");
        SubAccount subAccount = subAccountRepository.findById(fromAccountId);
        subAccount.setStatus(AccountStatus.NORMAL.getId());
    }

    public void transferCancel(long fromAccountId, long toAccountId, int amount) {

        System.out.println("transferCancel called");

        SubAccount subAccount = subAccountRepository.findById(fromAccountId);

        if (subAccount.getStatus() == AccountStatus.TRANSFERING.getId()) {
            subAccount.setBalanceAmount(subAccount.getBalanceAmount() + amount);
        }

        subAccount.setStatus(AccountStatus.NORMAL.getId());
    }

    public void transferWithMultipleConsumerConfirm(long fromAccountId, long toAccountId, int amount) {
        System.out.println("transferWithMultipleConsumerConfirm called");
    }

    public void transferWithMultipleConsumerCancel(long fromAccountId, long toAccountId, int amount) {
        System.out.println("transferWithMultipleConsumerCancel called");
    }
}

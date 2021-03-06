package org.tcc.examples.test.repository;

import org.tcc.examples.test.entity.AccountRecord;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;


@Repository
public class AccountRecordRepository {

    private Map<Long, AccountRecord> accountRecordMap = new HashMap<Long, AccountRecord>();

    {
        accountRecordMap.put(1L, new AccountRecord(1, 0));
        accountRecordMap.put(2L, new AccountRecord(2, 0));
        accountRecordMap.put(3L, new AccountRecord(3, 0));
    }

    public AccountRecord findById(Long id) {
        return accountRecordMap.get(id);
    }
}

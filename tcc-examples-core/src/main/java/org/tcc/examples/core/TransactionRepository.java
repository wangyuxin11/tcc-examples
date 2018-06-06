package org.tcc.examples.core;

//import org.mengyun.tcctransaction.api.TransactionXid;

import org.tcc.examples.api.TransactionXid;

import java.util.Collection;
import java.util.Date;
import java.util.List;


public interface TransactionRepository {

    int create(Transaction transaction);

    int update(Transaction transaction);

    int delete(Transaction transaction);

    Transaction findByXid(TransactionXid xid);

    List<Transaction> findAllUnmodifiedSince(Date date);
}


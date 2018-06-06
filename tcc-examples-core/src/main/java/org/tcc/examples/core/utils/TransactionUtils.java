package org.tcc.examples.core.utils;

import org.tcc.examples.api.Propagation;
import org.tcc.examples.api.TransactionContext;

public class TransactionUtils {

    public static boolean isLegalTransactionContext(boolean isTransactionActive, Propagation propagation, TransactionContext transactionContext) {

        if (propagation.equals(Propagation.MANDATORY) && !isTransactionActive && transactionContext == null) {
            return false;
        }

        return true;
    }
}

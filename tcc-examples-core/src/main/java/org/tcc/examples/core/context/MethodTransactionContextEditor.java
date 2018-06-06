package org.tcc.examples.core.context;

import java.lang.reflect.Method;

import org.tcc.examples.api.TransactionContext;
import org.tcc.examples.api.TransactionContextEditor;
import org.tcc.examples.core.utils.CompensableMethodUtils;

@Deprecated
public class MethodTransactionContextEditor implements TransactionContextEditor {

    public TransactionContext get(Object target, Method method, Object[] args) {
        int position = CompensableMethodUtils.getTransactionContextParamPosition(method.getParameterTypes());

        if (position >= 0) {
            return (TransactionContext) args[position];
        }
        
        return null;
    }

    public void set(TransactionContext transactionContext, Object target, Method method, Object[] args) {

        int position = CompensableMethodUtils.getTransactionContextParamPosition(method.getParameterTypes());
        if (position >= 0) {
            args[position] = transactionContext;
        }
    }
}
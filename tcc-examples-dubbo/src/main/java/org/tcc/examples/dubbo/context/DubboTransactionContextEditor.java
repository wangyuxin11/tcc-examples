package org.tcc.examples.dubbo.context;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.fastjson.JSON;

import org.tcc.examples.api.TransactionContext;
import org.tcc.examples.api.TransactionContextEditor;
import org.tcc.examples.dubbo.constants.TransactionContextConstants;

import java.lang.reflect.Method;

public class DubboTransactionContextEditor implements TransactionContextEditor {
    public TransactionContext get(Object target, Method method, Object[] args) {

        String context = RpcContext.getContext().getAttachment(TransactionContextConstants.TRANSACTION_CONTEXT);

        if (StringUtils.isNotEmpty(context)) {
            return JSON.parseObject(context, TransactionContext.class);
        }

        return null;
    }

    public void set(TransactionContext transactionContext, Object target, Method method, Object[] args) {
        RpcContext.getContext().setAttachment(TransactionContextConstants.TRANSACTION_CONTEXT, JSON.toJSONString(transactionContext));
    }
}

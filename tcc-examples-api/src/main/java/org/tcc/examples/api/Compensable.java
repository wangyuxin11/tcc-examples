package org.tcc.examples.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Compensable {

    public Propagation propagation() default Propagation.REQUIRED;

    public String confirmMethod() default "";

    public String cancelMethod() default "";

    public Class<? extends TransactionContextEditor> transactionContextEditor() default DefaultTransactionContextEditor.class;

    public boolean asyncConfirm() default false;

    public boolean asyncCancel() default false;

    class NullableTransactionContextEditor implements TransactionContextEditor {

        public TransactionContext get(Object target, Method method, Object[] args) {
            return null;
        }

        public void set(TransactionContext transactionContext, Object target, Method method, Object[] args) {

        }
    }

    class DefaultTransactionContextEditor implements TransactionContextEditor {

        public TransactionContext get(Object target, Method method, Object[] args) {
            int position = getTransactionContextParamPosition(method.getParameterTypes());

            if (position >= 0) {
                return (TransactionContext) args[position];
            }

            return null;
        }

        public void set(TransactionContext transactionContext, Object target, Method method, Object[] args) {

            int position = getTransactionContextParamPosition(method.getParameterTypes());
            if (position >= 0) {
                args[position] = transactionContext;
            }
        }

        public static int getTransactionContextParamPosition(Class<?>[] parameterTypes) {

            int position = -1;

            for (int i = 0; i < parameterTypes.length; i++) {
                if (parameterTypes[i].equals(TransactionContext.class)) {
                    position = i;
                    break;
                }
            }
            return position;
        }

        public static TransactionContext getTransactionContextFromArgs(Object[] args) {

            TransactionContext transactionContext = null;

            for (Object arg : args) {
                if (arg != null && TransactionContext.class.isAssignableFrom(arg.getClass())) {

                    transactionContext = (TransactionContext) arg;
                }
            }

            return transactionContext;
        }
    }
}
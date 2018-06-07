package org.tcc.examples.test;

import org.tcc.examples.core.Transaction;
import org.tcc.examples.core.serializer.ObjectSerializer;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtobufIOUtil;
import io.protostuff.runtime.RuntimeSchema;

public class ProtoStuffSerializer implements ObjectSerializer<Transaction> {

	public byte[] serialize(Transaction t) {
		return ProtobufIOUtil.toByteArray(t, RuntimeSchema.createFrom(Transaction.class),
				LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
	}

	public Transaction deserialize(byte[] data) {
		RuntimeSchema<Transaction> runtimeSchema = RuntimeSchema.createFrom(Transaction.class);
		Transaction t = runtimeSchema.newMessage();
		ProtobufIOUtil.mergeFrom(data, t, runtimeSchema);
		return t;
	}

	public Transaction clone(Transaction object) {
		throw new UnsupportedOperationException();
	}
}

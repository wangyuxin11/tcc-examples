package org.tcc.examples.core.serializer;

import org.apache.commons.lang3.SerializationUtils;
import java.io.Serializable;

public class JdkSerializationSerializer<T extends Serializable> implements ObjectSerializer<T> {

    public byte[] serialize(T object) {
        return SerializationUtils.serialize(object);
    }

    public T deserialize(byte[] bytes) {
        if (bytes == null) {
            return null;
        } else {
            return (T) SerializationUtils.deserialize(bytes);
        }
    }

    public T clone(T object) {
        return SerializationUtils.clone(object);
    }
}

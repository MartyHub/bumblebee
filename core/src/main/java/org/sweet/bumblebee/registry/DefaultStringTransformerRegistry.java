package org.sweet.bumblebee.registry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sweet.bumblebee.StringTransformer;
import org.sweet.bumblebee.StringTransformerRegistry;

import java.util.HashMap;
import java.util.Map;

public class DefaultStringTransformerRegistry implements StringTransformerRegistry {

    private final static Logger LOGGER = LoggerFactory.getLogger(DefaultStringTransformerRegistry.class);

    private final Map<Class<?>, StringTransformer<?>> transformers = new HashMap<Class<?>, StringTransformer<?>>();

    private final Map<String, StringTransformer<?>> overridings = new HashMap<String, StringTransformer<?>>();

    @Override
    @SuppressWarnings("unchecked")
    public <T> StringTransformer<T> register(StringTransformer<T> stringTransformer) {
        return (StringTransformer<T>) transformers.put(stringTransformer.getKey(), stringTransformer);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> StringTransformer<T> register(StringTransformer<T> stringTransformer, String name) {
        if (name == null) {
            return register(stringTransformer);
        } else {
            return (StringTransformer<T>) overridings.put(name, stringTransformer);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> StringTransformer<T> getStringConverter(String name, Class<T> type) {
        StringTransformer result = null;

        if (name != null) {
            result = overridings.get(name);

            if (result != null && !result.getKey()
                    .equals(type)) {
                LOGGER.warn("Discard <{}> as <{}> mismatch with expected <{}> for <{}>", result, result.getKey(), type, name);

                result = null;
            }
        }

        if (result == null) {
            result = transformers.get(type);
        }

        return result;
    }
}

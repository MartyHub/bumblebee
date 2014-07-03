package org.sweet.bumblebee.registry;

import org.sweet.bumblebee.StringTransformer;
import org.sweet.bumblebee.StringTransformerRegistry;

public class StringTransformerRegistryAdapter implements StringTransformerRegistry {

    protected final StringTransformerRegistry delegate;

    public StringTransformerRegistryAdapter(StringTransformerRegistry delegate) {
        this.delegate = delegate;
    }

    @Override
    public <T> StringTransformer<T> register(StringTransformer<T> stringTransformer) {
        return delegate.register(stringTransformer);
    }

    @Override
    public <T> StringTransformer<?> register(StringTransformer<T> stringTransformer, String name) {
        return delegate.register(stringTransformer, name);
    }

    @Override
    public <T> StringTransformer<T> getStringConverter(String name, Class<T> type) {
        return delegate.getStringConverter(name, type);
    }
}

package org.sweet.bumblebee.registry;

import org.sweet.bumblebee.StringTransformer;
import org.sweet.bumblebee.StringTransformerRegistry;
import org.sweet.bumblebee.transformer.ArrayStringTransformer;

public class ArrayStringTransformerRegistryAdapter extends StringTransformerRegistryAdapter {

    public ArrayStringTransformerRegistryAdapter(StringTransformerRegistry delegate) {
        super(delegate);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> StringTransformer<T> getStringConverter(String name, Class<T> type) {
        StringTransformer<T> result = super.getStringConverter(name, type);

        if (result == null && type.isArray()) {
            Class<?> componentType = type.getComponentType();
            StringTransformer<?> componentConverter = delegate.getStringConverter(name, componentType);

            if (componentConverter != null) {
                result = new ArrayStringTransformer(componentConverter, componentType);

                register(result, name);
            }
        }

        return result;
    }
}

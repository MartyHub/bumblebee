package org.sweet.bumblebee.registry;

import org.sweet.bumblebee.StringTransformer;
import org.sweet.bumblebee.StringTransformerRegistry;
import org.sweet.bumblebee.transformer.EnumStringTransformer;

public class EnumStringTransformerRegistryAdapter extends StringTransformerRegistryAdapter {

    public EnumStringTransformerRegistryAdapter(StringTransformerRegistry delegate) {
        super(delegate);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> StringTransformer<T> getStringConverter(String name, Class<T> type) {
        StringTransformer<T> result = super.getStringConverter(name, type);

        if (result == null && type.isEnum()) {
            result = new EnumStringTransformer(type);

            register(result, name);
        }

        return result;
    }
}

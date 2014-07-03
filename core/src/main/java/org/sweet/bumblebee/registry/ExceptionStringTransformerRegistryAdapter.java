package org.sweet.bumblebee.registry;

import org.sweet.bumblebee.BumblebeeException;
import org.sweet.bumblebee.StringTransformer;
import org.sweet.bumblebee.StringTransformerRegistry;

public class ExceptionStringTransformerRegistryAdapter extends StringTransformerRegistryAdapter {

    public ExceptionStringTransformerRegistryAdapter(StringTransformerRegistry delegate) {
        super(delegate);
    }

    @Override
    public <T> StringTransformer<T> getStringConverter(String name, Class<T> type) {
        StringTransformer<T> result = super.getStringConverter(name, type);

        if (result == null) {
            throw new BumblebeeException(String.format("Don't know how to handle <%s>", type));
        }

        return result;
    }
}

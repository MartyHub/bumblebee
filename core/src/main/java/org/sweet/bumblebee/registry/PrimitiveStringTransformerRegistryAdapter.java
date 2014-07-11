package org.sweet.bumblebee.registry;

import org.sweet.bumblebee.StringTransformer;
import org.sweet.bumblebee.StringTransformerRegistry;

public class PrimitiveStringTransformerRegistryAdapter extends StringTransformerRegistryAdapter {

    public PrimitiveStringTransformerRegistryAdapter(StringTransformerRegistry delegate) {
        super(delegate);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> StringTransformer<T> getStringConverter(String name, Class<T> type) {
        StringTransformer<T> result = super.getStringConverter(name, type);

        if (result == null && type.isPrimitive()) {
            if (Boolean.TYPE.equals(type)) {
                result = (StringTransformer<T>) super.getStringConverter(name, Boolean.class);
            } else if (Character.TYPE.equals(type)) {
                result = (StringTransformer<T>) super.getStringConverter(name, Character.class);
            } else if (Byte.TYPE.equals(type)) {
                result = (StringTransformer<T>) super.getStringConverter(name, Byte.class);
            } else if (Short.TYPE.equals(type)) {
                result = (StringTransformer<T>) super.getStringConverter(name, Short.class);
            } else if (Integer.TYPE.equals(type)) {
                result = (StringTransformer<T>) super.getStringConverter(name, Integer.class);
            } else if (Long.TYPE.equals(type)) {
                result = (StringTransformer<T>) super.getStringConverter(name, Long.class);
            } else if (Float.TYPE.equals(type)) {
                result = (StringTransformer<T>) super.getStringConverter(name, Float.class);
            } else if (Double.TYPE.equals(type)) {
                result = (StringTransformer<T>) super.getStringConverter(name, Double.class);
            }
        }

        return result;
    }
}

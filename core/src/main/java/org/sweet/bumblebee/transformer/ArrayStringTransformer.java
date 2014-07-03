package org.sweet.bumblebee.transformer;

import org.sweet.bumblebee.BumblebeeException;
import org.sweet.bumblebee.StringTransformer;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class ArrayStringTransformer<T> implements StringTransformer<T[]> {

    private final StringTransformer<T> elementConverter;

    private final Class<T> elementClass;

    public ArrayStringTransformer(StringTransformer<T> elementConverter, Class<T> elementClass) {
        if (elementConverter == null) {
            throw new NullPointerException();
        }

        if (elementClass == null) {
            throw new NullPointerException();
        }

        this.elementConverter = elementConverter;
        this.elementClass = elementClass;
    }

    @Override
    public Class<T[]> getKey() {
        return (Class<T[]>) Array.newInstance(elementClass, 0)
                .getClass();
    }

    @Override
    public T[] convert(String s) throws BumblebeeException {
        List<String> values = new ArrayList<String>();

        for (StringTokenizer tokenizer = new StringTokenizer(s, ","); tokenizer.hasMoreTokens(); ) {
            final String value = tokenizer.nextToken();

            values.add(value);
        }

        List<T> resultList = new ArrayList<T>(values.size());

        for (String value : values) {
            final T element = elementConverter.convert(value);

            if (element != null) {
                resultList.add(element);
            }
        }

        final int size = resultList.size();
        final Object result = Array.newInstance(getElementWrapperClass(), size);

        for (int i = 0; i < size; ++i) {
            Array.set(result, i, resultList.get(i));
        }

        return (T[]) result;
    }

    @Override
    public String getUsage() {
        return "comma separated list of " + elementConverter.getUsage();
    }

    private Class<?> getElementWrapperClass() {
        if (elementClass.isPrimitive()) {
            if (Boolean.TYPE.equals(elementClass)) {
                return Boolean.class;
            } else if (Character.TYPE.equals(elementClass)) {
                return Character.class;
            } else if (Byte.TYPE.equals(elementClass)) {
                return Byte.class;
            } else if (Short.TYPE.equals(elementClass)) {
                return Short.class;
            } else if (Integer.TYPE.equals(elementClass)) {
                return Integer.class;
            } else if (Long.TYPE.equals(elementClass)) {
                return Long.class;
            } else if (Float.TYPE.equals(elementClass)) {
                return Float.class;
            } else if (Double.TYPE.equals(elementClass)) {
                return Double.class;
            }
        }

        return elementClass;
    }
}

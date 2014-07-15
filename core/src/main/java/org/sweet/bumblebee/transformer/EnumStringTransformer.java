package org.sweet.bumblebee.transformer;

import org.sweet.bumblebee.StringTransformer;
import org.sweet.bumblebee.StringTransformerException;
import org.sweet.bumblebee.util.Joiner;

public class EnumStringTransformer<E extends Enum<E>> implements StringTransformer<E> {

    private final Class<E> enumClass;

    public EnumStringTransformer(Class<E> enumClass) {
        if (enumClass == null) {
            throw new NullPointerException();
        }

        this.enumClass = enumClass;
    }

    @Override
    public Class<E> getKey() {
        return enumClass;
    }

    @Override
    public E convert(String s) throws StringTransformerException {
        E[] enumConstants = enumClass.getEnumConstants();

        for (E enumConstant : enumConstants) {
            if (enumConstant.name().equalsIgnoreCase(s)) {
                return enumConstant;
            }
        }

        throw new StringTransformerException(String.format("Invalid value <%s>, must be one of <%s>", s, new Joiner().join(enumConstants)));
    }

    @Override
    public String getUsage() {
        return new Joiner().join(enumClass.getEnumConstants());
    }

    @Override
    public String toString() {
        return String.format("EnumStringConverter of <%s>", enumClass);
    }
}

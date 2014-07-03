package org.sweet.bumblebee.transformer;

import org.sweet.bumblebee.BumblebeeException;
import org.sweet.bumblebee.StringTransformer;

import java.util.Arrays;

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
    public E convert(String s) throws BumblebeeException {
        E[] enumConstants = enumClass.getEnumConstants();

        for (E enumConstant : enumConstants) {
            if (enumConstant.name()
                    .equals(s)) {
                return enumConstant;
            }
        }

        throw new BumblebeeException(String.format("Invalid value <%s>, must be one of %s", s, Arrays.toString(enumConstants)));
    }

    @Override
    public String getUsage() {
        StringBuilder sb = new StringBuilder();

        for (E enumConstant : enumClass.getEnumConstants()) {
            if (sb.length() > 0) {
                sb.append(", ");
            }

            sb.append(enumConstant.name());
        }

        return sb.toString();
    }

    @Override
    public String toString() {
        return String.format("EnumStringConverter of <%s>", enumClass);
    }
}

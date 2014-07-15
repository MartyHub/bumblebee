package org.sweet.bumblebee.transformer;

import org.sweet.bumblebee.StringTransformer;
import org.sweet.bumblebee.StringTransformerException;

public class ClassStringTransformer implements StringTransformer<Class> {

    @Override
    public Class<Class> getKey() {
        return Class.class;
    }

    @Override
    public Class convert(String s) throws StringTransformerException {
        try {
            return Class.forName(s);
        } catch (ClassNotFoundException e) {
            throw new StringTransformerException(String.format("Failed to lookup class <%s>", s), e);
        }
    }

    @Override
    public String getUsage() {
        return "class name";
    }
}

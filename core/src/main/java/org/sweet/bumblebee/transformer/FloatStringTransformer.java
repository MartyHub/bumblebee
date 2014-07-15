package org.sweet.bumblebee.transformer;

import org.sweet.bumblebee.StringTransformer;
import org.sweet.bumblebee.StringTransformerException;

public class FloatStringTransformer implements StringTransformer<Float> {

    @Override
    public Class<Float> getKey() {
        return Float.class;
    }

    @Override
    public Float convert(String s) throws StringTransformerException {
        try {
            return Float.valueOf(s);
        } catch (NumberFormatException e) {
            throw new StringTransformerException(String.format("Invalid float value <%s>", s), e);
        }
    }

    @Override
    public String getUsage() {
        return "float";
    }
}

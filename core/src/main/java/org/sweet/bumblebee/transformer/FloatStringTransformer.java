package org.sweet.bumblebee.transformer;

import org.sweet.bumblebee.BumblebeeException;
import org.sweet.bumblebee.StringTransformer;

public class FloatStringTransformer implements StringTransformer<Float> {

    @Override
    public Class<Float> getKey() {
        return Float.class;
    }

    @Override
    public Float convert(String s) throws BumblebeeException {
        try {
            return Float.valueOf(s);
        } catch (NumberFormatException e) {
            throw new BumblebeeException(String.format("Invalid float value <%s>", s), e);
        }
    }

    @Override
    public String getUsage() {
        return "float";
    }
}

package org.sweet.bumblebee.transformer;

import org.sweet.bumblebee.StringTransformer;
import org.sweet.bumblebee.StringTransformerException;

public class DoubleStringTransformer implements StringTransformer<Double> {

    @Override
    public Class<Double> getKey() {
        return Double.class;
    }

    @Override
    public Double convert(String s) throws StringTransformerException {
        try {
            return Double.valueOf(s);
        } catch (NumberFormatException e) {
            throw new StringTransformerException(String.format("Invalid double value <%s>", s), e);
        }
    }

    @Override
    public String getUsage() {
        return "double";
    }
}

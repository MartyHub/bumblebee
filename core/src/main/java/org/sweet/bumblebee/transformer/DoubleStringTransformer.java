package org.sweet.bumblebee.transformer;

import org.sweet.bumblebee.BumblebeeException;
import org.sweet.bumblebee.StringTransformer;

public class DoubleStringTransformer implements StringTransformer<Double> {

    @Override
    public Class<Double> getKey() {
        return Double.class;
    }

    @Override
    public Double convert(String s) throws BumblebeeException {
        try {
            return Double.valueOf(s);
        } catch (NumberFormatException e) {
            throw new BumblebeeException(String.format("Invalid double value <%s>", s), e);
        }
    }

    @Override
    public String getUsage() {
        return "double";
    }
}

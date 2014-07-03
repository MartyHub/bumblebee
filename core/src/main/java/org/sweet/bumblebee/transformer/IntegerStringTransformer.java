package org.sweet.bumblebee.transformer;

import org.sweet.bumblebee.BumblebeeException;
import org.sweet.bumblebee.StringTransformer;

public class IntegerStringTransformer implements StringTransformer<Integer> {

    @Override
    public Class<Integer> getKey() {
        return Integer.class;
    }

    @Override
    public Integer convert(String s) throws BumblebeeException {
        try {
            return Integer.valueOf(s);
        } catch (NumberFormatException e) {
            throw new BumblebeeException(String.format("Invalid integer value <%s>", s), e);
        }
    }

    @Override
    public String getUsage() {
        return "integer";
    }
}

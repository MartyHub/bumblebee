package org.sweet.bumblebee.transformer;

import org.sweet.bumblebee.StringTransformer;
import org.sweet.bumblebee.StringTransformerException;

public class IntegerStringTransformer implements StringTransformer<Integer> {

    @Override
    public Class<Integer> getKey() {
        return Integer.class;
    }

    @Override
    public Integer convert(String s) throws StringTransformerException {
        try {
            return Integer.valueOf(s);
        } catch (NumberFormatException e) {
            throw new StringTransformerException(String.format("Invalid integer value <%s>", s), e);
        }
    }

    @Override
    public String getUsage() {
        return "integer";
    }
}

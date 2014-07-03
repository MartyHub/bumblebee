package org.sweet.bumblebee.transformer;

import org.sweet.bumblebee.BumblebeeException;
import org.sweet.bumblebee.StringTransformer;

public class LongStringTransformer implements StringTransformer<Long> {

    @Override
    public Class<Long> getKey() {
        return Long.class;
    }

    @Override
    public Long convert(String s) throws BumblebeeException {
        try {
            return Long.valueOf(s);
        } catch (NumberFormatException e) {
            throw new BumblebeeException(String.format("Invalid long value <%s>", s), e);
        }
    }

    @Override
    public String getUsage() {
        return "long";
    }
}

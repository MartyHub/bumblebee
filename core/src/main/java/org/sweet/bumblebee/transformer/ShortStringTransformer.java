package org.sweet.bumblebee.transformer;

import org.sweet.bumblebee.BumblebeeException;
import org.sweet.bumblebee.StringTransformer;

public class ShortStringTransformer implements StringTransformer<Short> {

    @Override
    public Class<Short> getKey() {
        return Short.class;
    }

    @Override
    public Short convert(String s) throws BumblebeeException {
        try {
            return Short.valueOf(s);
        } catch (NumberFormatException e) {
            throw new BumblebeeException(String.format("Invalid short value <%s>", s), e);
        }
    }

    @Override
    public String getUsage() {
        return "short";
    }
}

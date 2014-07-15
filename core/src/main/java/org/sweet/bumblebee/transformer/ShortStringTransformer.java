package org.sweet.bumblebee.transformer;

import org.sweet.bumblebee.StringTransformer;
import org.sweet.bumblebee.StringTransformerException;

public class ShortStringTransformer implements StringTransformer<Short> {

    @Override
    public Class<Short> getKey() {
        return Short.class;
    }

    @Override
    public Short convert(String s) throws StringTransformerException {
        try {
            return Short.valueOf(s);
        } catch (NumberFormatException e) {
            throw new StringTransformerException(String.format("Invalid short value <%s>", s), e);
        }
    }

    @Override
    public String getUsage() {
        return "short";
    }
}

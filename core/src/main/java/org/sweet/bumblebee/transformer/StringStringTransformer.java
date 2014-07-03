package org.sweet.bumblebee.transformer;

import org.sweet.bumblebee.BumblebeeException;
import org.sweet.bumblebee.StringTransformer;

public class StringStringTransformer implements StringTransformer<String> {

    @Override
    public Class<String> getKey() {
        return String.class;
    }

    @Override
    public String convert(String s) throws BumblebeeException {
        return s;
    }

    @Override
    public String getUsage() {
        return "string";
    }
}

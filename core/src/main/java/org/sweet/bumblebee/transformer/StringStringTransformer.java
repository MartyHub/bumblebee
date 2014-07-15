package org.sweet.bumblebee.transformer;

import org.sweet.bumblebee.StringTransformer;
import org.sweet.bumblebee.StringTransformerException;

public class StringStringTransformer implements StringTransformer<String> {

    @Override
    public Class<String> getKey() {
        return String.class;
    }

    @Override
    public String convert(String s) throws StringTransformerException {
        return s;
    }

    @Override
    public String getUsage() {
        return "string";
    }
}

package org.sweet.bumblebee.transformer;

import org.sweet.bumblebee.BumblebeeException;
import org.sweet.bumblebee.StringTransformer;

public class BooleanStringTransformer implements StringTransformer<Boolean> {

    @Override
    public Class<Boolean> getKey() {
        return Boolean.class;
    }

    @Override
    public Boolean convert(String s) throws BumblebeeException {
        if ("y".equals(s)) {
            return Boolean.TRUE;
        } else if ("n".equals(s)) {
            return Boolean.FALSE;
        } else {
            throw new BumblebeeException(String.format("Invalid boolean value <%s>, must be one of <y, n>", s));
        }
    }

    @Override
    public String getUsage() {
        return "y, n";
    }
}

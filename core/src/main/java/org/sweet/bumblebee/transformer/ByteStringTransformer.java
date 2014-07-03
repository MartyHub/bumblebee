package org.sweet.bumblebee.transformer;

import org.sweet.bumblebee.BumblebeeException;
import org.sweet.bumblebee.StringTransformer;

public class ByteStringTransformer implements StringTransformer<Byte> {

    @Override
    public Class<Byte> getKey() {
        return Byte.class;
    }

    @Override
    public Byte convert(String s) throws BumblebeeException {
        try {
            return Byte.valueOf(s);
        } catch (NumberFormatException e) {
            throw new BumblebeeException(String.format("Invalid byte value <%s>", s), e);
        }
    }

    @Override
    public String getUsage() {
        return "byte";
    }
}

package org.sweet.bumblebee.transformer;

import org.sweet.bumblebee.StringTransformer;
import org.sweet.bumblebee.StringTransformerException;

import java.math.BigInteger;

public class BigIntegerStringTransformer implements StringTransformer<BigInteger> {

    @Override
    public Class<BigInteger> getKey() {
        return BigInteger.class;
    }

    @Override
    public BigInteger convert(String s) throws StringTransformerException {
        try {
            return new BigInteger(s);
        } catch (NumberFormatException e) {
            throw new StringTransformerException(String.format("Failed to parse <%s> as an integer", s), e);
        }
    }

    @Override
    public String getUsage() {
        return "integer";
    }
}

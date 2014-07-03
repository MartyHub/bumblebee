package org.sweet.bumblebee.transformer;

import org.sweet.bumblebee.BumblebeeException;
import org.sweet.bumblebee.StringTransformer;

import java.math.BigInteger;

public class BigIntegerStringTransformer implements StringTransformer<BigInteger> {

    @Override
    public Class<BigInteger> getKey() {
        return BigInteger.class;
    }

    @Override
    public BigInteger convert(String s) throws BumblebeeException {
        try {
            return new BigInteger(s);
        } catch (NumberFormatException e) {
            throw new BumblebeeException(String.format("Failed to parse <%s> as an integer", s), e);
        }
    }

    @Override
    public String getUsage() {
        return "integer";
    }
}

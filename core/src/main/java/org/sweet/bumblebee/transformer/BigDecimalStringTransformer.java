package org.sweet.bumblebee.transformer;

import org.sweet.bumblebee.BumblebeeException;
import org.sweet.bumblebee.StringTransformer;

import java.math.BigDecimal;

public class BigDecimalStringTransformer implements StringTransformer<BigDecimal> {

    @Override
    public Class<BigDecimal> getKey() {
        return BigDecimal.class;
    }

    @Override
    public BigDecimal convert(String s) throws BumblebeeException {
        try {
            return new BigDecimal(s);
        } catch (NumberFormatException e) {
            throw new BumblebeeException(String.format("Failed to parse <%s> as an decimal number", s), e);
        }
    }

    @Override
    public String getUsage() {
        return "decimal number";
    }
}

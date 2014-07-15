package org.sweet.bumblebee.transformer;

import org.sweet.bumblebee.StringTransformer;
import org.sweet.bumblebee.StringTransformerException;

import java.math.BigDecimal;

public class BigDecimalStringTransformer implements StringTransformer<BigDecimal> {

    @Override
    public Class<BigDecimal> getKey() {
        return BigDecimal.class;
    }

    @Override
    public BigDecimal convert(String s) throws StringTransformerException {
        try {
            return new BigDecimal(s);
        } catch (NumberFormatException e) {
            throw new StringTransformerException(String.format("Failed to parse <%s> as an decimal number", s), e);
        }
    }

    @Override
    public String getUsage() {
        return "decimal number";
    }
}

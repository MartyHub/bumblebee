package org.sweet.bumblebee.transformer;

import org.sweet.bumblebee.BumblebeeException;
import org.sweet.bumblebee.StringTransformer;

import java.sql.Timestamp;

public class TimestampStringTransformer implements StringTransformer<Timestamp> {

    @Override
    public Class<Timestamp> getKey() {
        return Timestamp.class;
    }

    @Override
    public Timestamp convert(String s) throws BumblebeeException {
        try {
            return Timestamp.valueOf(s);
        } catch (IllegalArgumentException e) {
            throw new BumblebeeException(String.format("Failed to parse <%s> with pattern <%s>", s, getUsage()), e);
        }
    }

    @Override
    public String getUsage() {
        return "yyyy-MM-dd HH:mm:ss[.fffffffff]";
    }
}

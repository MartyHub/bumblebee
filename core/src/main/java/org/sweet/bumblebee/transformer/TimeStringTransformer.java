package org.sweet.bumblebee.transformer;

import org.sweet.bumblebee.BumblebeeException;
import org.sweet.bumblebee.StringTransformer;

import java.sql.Time;

public class TimeStringTransformer implements StringTransformer<Time> {

    @Override
    public Class<Time> getKey() {
        return Time.class;
    }

    @Override
    public Time convert(String s) throws BumblebeeException {
        try {
            return Time.valueOf(s);
        } catch (IllegalArgumentException e) {
            throw new BumblebeeException(String.format("Failed to parse <%s> with pattern <%s>", s, getUsage()), e);
        }
    }

    @Override
    public String getUsage() {
        return "HH:mm:ss";
    }
}

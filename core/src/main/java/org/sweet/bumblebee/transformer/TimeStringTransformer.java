package org.sweet.bumblebee.transformer;

import org.sweet.bumblebee.StringTransformer;
import org.sweet.bumblebee.StringTransformerException;

import java.sql.Time;

public class TimeStringTransformer implements StringTransformer<Time> {

    @Override
    public Class<Time> getKey() {
        return Time.class;
    }

    @Override
    public Time convert(String s) throws StringTransformerException {
        try {
            return Time.valueOf(s);
        } catch (IllegalArgumentException e) {
            throw new StringTransformerException(String.format("Failed to parse <%s> with pattern <%s>", s, getUsage()), e);
        }
    }

    @Override
    public String getUsage() {
        return "HH:mm:ss";
    }
}

package org.sweet.bumblebee.transformer;

import org.sweet.bumblebee.BumblebeeException;
import org.sweet.bumblebee.StringTransformer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateStringTransformer implements StringTransformer<Date> {

    private final String pattern;

    public DateStringTransformer() {
        this("yyyy-MM-dd'T'HH:mm:ss");
    }

    public DateStringTransformer(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public Class<Date> getKey() {
        return Date.class;
    }

    @Override
    public Date convert(String s) throws BumblebeeException {
        try {
            return new SimpleDateFormat(pattern).parse(s);
        } catch (ParseException e) {
            throw new BumblebeeException(String.format("Failed to parse <%s> with pattern <%s>", s, pattern), e);
        }
    }

    @Override
    public String getUsage() {
        return pattern;
    }
}

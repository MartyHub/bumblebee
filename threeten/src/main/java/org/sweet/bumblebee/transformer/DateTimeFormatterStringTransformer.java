package org.sweet.bumblebee.transformer;

import org.sweet.bumblebee.BumblebeeException;
import org.sweet.bumblebee.StringTransformer;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.format.DateTimeParseException;

public abstract class DateTimeFormatterStringTransformer<T> implements StringTransformer<T> {

    protected final DateTimeFormatter formatter;

    private final String pattern;

    protected DateTimeFormatterStringTransformer(String pattern) {
        this.pattern = pattern;
        this.formatter = DateTimeFormatter.ofPattern(pattern);
    }

    @Override
    public T convert(String s) throws BumblebeeException {
        try {
            return doConvert(s);
        } catch (DateTimeParseException e) {
            throw new BumblebeeException(String.format("Failed to parse <%s> with pattern <%s>", s, getPattern()), e);
        }
    }

    @Override
    public String getUsage() {
        return pattern;
    }

    public String getPattern() {
        return pattern;
    }

    protected abstract T doConvert(String s) throws BumblebeeException;
}

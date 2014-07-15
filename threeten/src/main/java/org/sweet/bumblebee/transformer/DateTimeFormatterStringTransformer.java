package org.sweet.bumblebee.transformer;

import org.sweet.bumblebee.StringTransformer;
import org.sweet.bumblebee.StringTransformerException;
import org.sweet.bumblebee.util.Joiner;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.format.DateTimeParseException;

public abstract class DateTimeFormatterStringTransformer<T> implements StringTransformer<T>, StringTransformerWithContext {

    private String[] patterns;

    private DateTimeFormatter[] formatters;

    public DateTimeFormatterStringTransformer() {
    }

    protected DateTimeFormatterStringTransformer(String pattern) {
        this.patterns = new String[]{pattern};
        this.formatters = new DateTimeFormatter[]{DateTimeFormatter.ofPattern(pattern)};
    }

    @Override
    public T convert(String s) throws StringTransformerException {
        for (DateTimeFormatter formatter : formatters) {
            try {
                return doConvert(s, formatter);
            } catch (DateTimeParseException e) {
            }
        }

        throw new StringTransformerException(String.format("Failed to parse <%s> with pattern(s) <%s>", s, getUsage()));
    }

    @Override
    public String getUsage() {
        return new Joiner().join(patterns);
    }

    protected final void setPatterns(String[] patterns) {
        if (this.patterns == null) {
            this.patterns = patterns;

            final int length = patterns.length;

            this.formatters = new DateTimeFormatter[length];

            for (int i = 0; i < length; ++i) {
                formatters[i] = DateTimeFormatter.ofPattern(patterns[i]);
            }
        }
    }

    protected abstract T doConvert(String s, DateTimeFormatter formatter) throws StringTransformerException;
}

package org.sweet.bumblebee.transformer;

import org.sweet.bumblebee.BumblebeeException;
import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

public class LocalDateStringTransformer extends DateTimeFormatterStringTransformer<LocalDate> {

    public LocalDateStringTransformer() {
    }

    public LocalDateStringTransformer(String pattern) {
        super(pattern);
    }

    @Override
    public void setContext(StringTransformerContext context) {
        setPatterns(context.getDatePatterns());
    }

    @Override
    public Class<LocalDate> getKey() {
        return LocalDate.class;
    }

    @Override
    protected LocalDate doConvert(String s, DateTimeFormatter formatter) throws BumblebeeException {
        return LocalDate.parse(s, formatter);
    }
}

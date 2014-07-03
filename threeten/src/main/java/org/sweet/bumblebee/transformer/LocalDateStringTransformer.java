package org.sweet.bumblebee.transformer;

import org.sweet.bumblebee.BumblebeeException;
import org.threeten.bp.LocalDate;

public class LocalDateStringTransformer extends DateTimeFormatterStringTransformer<LocalDate> {

    public LocalDateStringTransformer() {
        super("yyyy-MM-dd");
    }

    public LocalDateStringTransformer(String pattern) {
        super(pattern);
    }

    @Override
    public Class<LocalDate> getKey() {
        return LocalDate.class;
    }

    @Override
    protected LocalDate doConvert(String s) throws BumblebeeException {
        return LocalDate.parse(s, formatter);
    }
}

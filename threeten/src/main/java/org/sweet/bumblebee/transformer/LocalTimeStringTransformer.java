package org.sweet.bumblebee.transformer;

import org.sweet.bumblebee.BumblebeeException;
import org.threeten.bp.LocalTime;

public class LocalTimeStringTransformer extends DateTimeFormatterStringTransformer<LocalTime> {

    public LocalTimeStringTransformer() {
        super("HH:mm:ss");
    }

    public LocalTimeStringTransformer(String pattern) {
        super(pattern);
    }

    @Override
    public Class<LocalTime> getKey() {
        return LocalTime.class;
    }

    @Override
    protected LocalTime doConvert(String s) throws BumblebeeException {
        return LocalTime.parse(s, formatter);
    }
}

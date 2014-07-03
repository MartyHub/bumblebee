package org.sweet.bumblebee.transformer;

import org.sweet.bumblebee.BumblebeeException;
import org.threeten.bp.LocalDateTime;

public class LocalDateTimeStringTransformer extends DateTimeFormatterStringTransformer<LocalDateTime> {

    public LocalDateTimeStringTransformer() {
        super("yyyy-MM-dd'T'HH:mm:ss");
    }

    public LocalDateTimeStringTransformer(String pattern) {
        super(pattern);
    }

    @Override
    public Class<LocalDateTime> getKey() {
        return LocalDateTime.class;
    }

    @Override
    protected LocalDateTime doConvert(String s) throws BumblebeeException {
        return LocalDateTime.parse(s, formatter);
    }
}

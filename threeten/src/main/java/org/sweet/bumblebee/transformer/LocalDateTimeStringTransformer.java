package org.sweet.bumblebee.transformer;

import org.sweet.bumblebee.BumblebeeException;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;

public class LocalDateTimeStringTransformer extends DateTimeFormatterStringTransformer<LocalDateTime> {

    public LocalDateTimeStringTransformer() {
    }

    public LocalDateTimeStringTransformer(String pattern) {
        super(pattern);
    }

    @Override
    public void setContext(StringTransformerContext context) {
        setPatterns(context.getDateTimePatterns());
    }

    @Override
    public Class<LocalDateTime> getKey() {
        return LocalDateTime.class;
    }

    @Override
    protected LocalDateTime doConvert(String s, DateTimeFormatter formatter) throws BumblebeeException {
        return LocalDateTime.parse(s, formatter);
    }
}

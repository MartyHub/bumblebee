package org.sweet.bumblebee.transformer;

import org.sweet.bumblebee.BumblebeeException;
import org.threeten.bp.LocalTime;
import org.threeten.bp.format.DateTimeFormatter;

public class LocalTimeStringTransformer extends DateTimeFormatterStringTransformer<LocalTime> {

    public LocalTimeStringTransformer() {
    }

    public LocalTimeStringTransformer(String pattern) {
        super(pattern);
    }

    @Override
    public void setContext(StringTransformerContext context) {
        setPatterns(context.getTimePatterns());
    }

    @Override
    public Class<LocalTime> getKey() {
        return LocalTime.class;
    }

    @Override
    protected LocalTime doConvert(String s, DateTimeFormatter formatter) throws BumblebeeException {
        return LocalTime.parse(s, formatter);
    }
}

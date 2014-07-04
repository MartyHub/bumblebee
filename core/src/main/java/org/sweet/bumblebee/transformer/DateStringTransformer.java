package org.sweet.bumblebee.transformer;

import org.sweet.bumblebee.BumblebeeException;
import org.sweet.bumblebee.StringTransformer;
import org.sweet.bumblebee.util.Joiner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Warning, not thread safe !
 */
public class DateStringTransformer implements StringTransformer<Date>, StringTransformerWithContext {

    private SimpleDateFormat[] simpleDateFormats;

    public DateStringTransformer() {
    }

    public DateStringTransformer(String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        this.simpleDateFormats = new SimpleDateFormat[] {simpleDateFormat};
    }

    @Override
    public void setContext(StringTransformerContext context) {
        if (simpleDateFormats == null) {
            String[] dateTimePatterns = context.getDateTimePatterns();
            final int length = dateTimePatterns.length;

            simpleDateFormats = new SimpleDateFormat[length];

            for (int i = 0; i < length; ++i) {
                simpleDateFormats[i] = new SimpleDateFormat(dateTimePatterns[i]);
            }
        }
    }

    @Override
    public Class<Date> getKey() {
        return Date.class;
    }

    @Override
    public Date convert(String s) throws BumblebeeException {
        for (SimpleDateFormat simpleDateFormat : simpleDateFormats) {
            try {
                return simpleDateFormat.parse(s);
            } catch (ParseException e) {
            }
        }

        throw new BumblebeeException(String.format("Failed to parse <%s> with pattern(s) <%s>", s, getUsage()));
    }

    @Override
    public String getUsage() {
        final int length = simpleDateFormats.length;
        String[] patterns = new String[length];

        for (int i = 0; i < length; ++i) {
            patterns[i] = simpleDateFormats[i].toPattern();
        }

        return new Joiner().join(patterns);
    }
}

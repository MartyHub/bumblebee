package org.sweet.bumblebee.transformer;

import org.sweet.bumblebee.BumblebeeException;
import org.sweet.bumblebee.StringTransformer;
import org.sweet.bumblebee.util.Joiner;

public class BooleanStringTransformer implements StringTransformer<Boolean>, StringTransformerWithContext {

    private StringTransformerContext context;

    @Override
    public void setContext(StringTransformerContext context) {
        this.context = context;
    }

    @Override
    public Class<Boolean> getKey() {
        return Boolean.class;
    }

    @Override
    public Boolean convert(String s) throws BumblebeeException {
        if (contains(context.getTrueMappings(), s)) {
            return Boolean.TRUE;
        } else if (contains(context.getFalseMappings(), s)) {
            return Boolean.FALSE;
        } else {
            throw new BumblebeeException(String.format("Invalid boolean value <%s>, must be one of <%s>", s, getUsage()));
        }
    }

    @Override
    public String getUsage() {
        return new Joiner().append(context.getTrueMappings())
                .append(context.getFalseMappings())
                .toString();
    }

    private boolean contains(String[] values, String s) {
        for (String value : values) {
            if (value.equalsIgnoreCase(s)) {
                return true;
            }
        }

        return false;
    }
}

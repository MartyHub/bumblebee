package org.sweet.bumblebee.bean;

import org.sweet.bumblebee.BeanArgumentsIntrospector;
import org.sweet.bumblebee.StringTransformer;

public class BeanUsageBuilder<T> extends BeanBuilder<T> {

    private final String newLine = System.getProperty("line.separator");

    private StringBuilder sb;

    public BeanUsageBuilder(BeanArgumentsIntrospector<T> beanArgumentsIntrospector) {
        super(beanArgumentsIntrospector);
    }

    public String getUsage() {
        return sb.toString();
    }

    protected void doBuild() {
        sb = new StringBuilder();

        appendln(" Arguments :");

        processArguments();

        newLine();
        appendln("* indicates mandatory arguments");
    }

    private void appendln(String s) {
        sb.append(s);

        newLine();
    }

    private void newLine() {
        sb.append(newLine);
    }

    private void processArguments() {
        for (BeanArgumentAdapter beanArgumentAdapter : beanArgumentsIntrospector) {
            processArgument(beanArgumentAdapter);
        }
    }

    private void processArgument(BeanArgumentAdapter beanArgumentAdapter) {
        sb.append("  ");

        if (beanArgumentAdapter.isOptional()) {
            sb.append("  ");
        } else {
            sb.append("* ");
        }

        sb.append('-');
        sb.append(beanArgumentAdapter.getDisplayName());
        sb.append("=<");

        Class<?> type = beanArgumentAdapter.getType();
        StringTransformer<?> converter = beanArgumentsIntrospector.getStringTransformerRegistry()
                .getStringConverter(beanArgumentAdapter.getJavaName(), type);

        sb.append(converter.getUsage());
        sb.append('>');

        String defaultValue = beanArgumentAdapter.getDefaultValue(bean);

        if (defaultValue != null) {
            sb.append(" (default to <");
            sb.append(defaultValue);
            sb.append(">)");
        }

        String doc = beanArgumentAdapter.getDoc();

        if (doc != null) {
            newLine();

            sb.append("     ");
            sb.append(doc);
        }

        newLine();
    }
}

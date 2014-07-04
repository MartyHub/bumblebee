package org.sweet.bumblebee.util;

public class Joiner {

    private final String separator;

    private final StringBuilder sb = new StringBuilder();

    public Joiner() {
        this(", ");
    }

    public Joiner(String separator) {
        this.separator = separator;
    }

    public Joiner append(Object[] array) {
        if (array == null) {
            return this;
        }

        for (Object value : array) {
            if (separator != null && sb.length() > 0) {
                sb.append(separator);
            }

            sb.append(value);
        }

        return this;
    }

    public String join(Object[] array) {
        append(array);

        return toString();
    }

    public String toString() {
        return sb.toString();
    }
}

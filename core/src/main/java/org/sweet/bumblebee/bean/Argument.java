package org.sweet.bumblebee.bean;

public class Argument {

    private final String name;

    private final String value;

    public Argument(String arg) {
        if (arg == null) {
            throw new NullPointerException();
        }

        if (arg.charAt(0) != '-') {
            throw new IllegalArgumentException(String.format("Invalid argument <%s>, must start with '-' : '-name=value'", arg));
        }

        final int index = arg.indexOf('=');

        if (index == -1) {
            throw new IllegalArgumentException(String.format("Invalid argument <%s>, must have a value : '-name=value'", arg));
        }

        if (index < 2) {
            throw new IllegalArgumentException(String.format("Invalid argument <%s>, must have a name : '-name=value'", arg));
        }

        this.name = arg.substring(1, index);
        this.value = arg.substring(index + 1)
                .trim();
    }

    public Argument(String name, String value) {
        if (name == null) {
            throw new NullPointerException();
        }

        if (value == null) {
            throw new NullPointerException();
        }

        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("%s = %s", name, value);
    }
}

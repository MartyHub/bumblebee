package org.sweet.bumblebee.bean;

import java.util.Iterator;

public class ArrayArgumentProvider implements Iterable<Argument> {

    private final String[] args;

    public ArrayArgumentProvider(String[] args) {
        if (args == null) {
            this.args = new String[0];
        } else {
            this.args = args;
        }
    }

    @Override
    public Iterator<Argument> iterator() {
        return new Iterator<Argument>() {

            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < args.length;
            }

            @Override
            public Argument next() {
                String arg = args[index];

                ++index;

                return new Argument(arg);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
}

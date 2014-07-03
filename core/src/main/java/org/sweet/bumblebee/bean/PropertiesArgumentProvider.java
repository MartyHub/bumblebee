package org.sweet.bumblebee.bean;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;

public class PropertiesArgumentProvider implements Iterable<Argument> {

    private final Properties properties;

    public PropertiesArgumentProvider(String classPathResource) {
        if (classPathResource == null) {
            throw new NullPointerException();
        }

        this.properties = new Properties();

        InputStream is = null;

        try {
            is = ClassLoader.getSystemResourceAsStream(classPathResource);

            if (is == null) {
                throw new IllegalArgumentException(String.format("Failed to find classpath resource <%s>", classPathResource));
            }

            this.properties.load(is);
        } catch (IOException e) {
            throw new IllegalArgumentException(String.format("Failed to read classpath resource <%s>", classPathResource), e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
        }
    }

    public PropertiesArgumentProvider(Properties properties) {
        if (properties == null) {
            throw new NullPointerException();
        }

        this.properties = properties;
    }

    @Override
    public Iterator<Argument> iterator() {
        return new Iterator<Argument>() {

            private final Iterator<String> nameIterator = properties.stringPropertyNames()
                    .iterator();

            @Override
            public boolean hasNext() {
                return nameIterator.hasNext();
            }

            @Override
            public Argument next() {
                String name = nameIterator.next();
                String value = properties.getProperty(name);

                return new Argument(name, value);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
}

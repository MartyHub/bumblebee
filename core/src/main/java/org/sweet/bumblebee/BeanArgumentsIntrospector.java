package org.sweet.bumblebee;

import org.sweet.bumblebee.bean.Argument;
import org.sweet.bumblebee.bean.BeanArgumentAdapter;
import org.sweet.bumblebee.bean.BeanArgumentsBuilder;
import org.sweet.bumblebee.bean.BeanUsageBuilder;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;

public class BeanArgumentsIntrospector<T> implements Iterable<BeanArgumentAdapter> {

    private final Map<String, BeanArgumentAdapter> argumentByDisplayName;

    private final StringTransformerRegistry stringTransformerRegistry;

    private final Class<T> beanClass;

    @SuppressWarnings("unchecked")
    public BeanArgumentsIntrospector(StringTransformerRegistry stringTransformerRegistry, Class<T> beanClass) {
        if (stringTransformerRegistry == null) {
            throw new NullPointerException();
        }

        if (beanClass == null) {
            throw new NullPointerException();
        }

        this.stringTransformerRegistry = stringTransformerRegistry;
        this.beanClass = beanClass;

        try {
            PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(beanClass).getPropertyDescriptors();

            this.argumentByDisplayName = new HashMap<String, BeanArgumentAdapter>(propertyDescriptors.length);

            for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                if (propertyDescriptor.getWriteMethod() != null) {
                    final String propertyName = propertyDescriptor.getName();
                    final Class<?> propertyType = propertyDescriptor.getPropertyType();
                    StringTransformer<?> stringTransformer = null;

                    try {
                        stringTransformer = stringTransformerRegistry.getStringConverter(propertyName, propertyType);
                    } catch (Exception e) {
                    }

                    if (stringTransformer == null) {
                        // try as sub property
                        for (BeanArgumentAdapter subBeanArgumentAdapter : (Iterable<BeanArgumentAdapter>) new BeanArgumentsIntrospector(stringTransformerRegistry, propertyType)) {
                            addArgumentAdapter(subBeanArgumentAdapter.withPrefix(propertyName));
                        }
                    } else {
                        addArgumentAdapter(new BeanArgumentAdapter(beanClass, propertyDescriptor));
                    }
                }
            }
        } catch (IntrospectionException e) {
            throw new BeanArgumentException(String.format("Failed to introspect class <%s>", beanClass), e);
        }
    }

    public T newBean() {
        try {
            return beanClass.newInstance();
        } catch (Exception e) {
            throw new BeanArgumentException(String.format("Failed to create new instance of <%s>", beanClass), e);
        }
    }

    public BeanArgumentAdapter getArgumentAdapter(String displayName) {
        return argumentByDisplayName.get(displayName);
    }

    @Override
    public Iterator<BeanArgumentAdapter> iterator() {
        return new TreeSet<BeanArgumentAdapter>(argumentByDisplayName.values()).iterator();
    }

    public BeanArgumentsBuilder<T> builder(Iterable<Argument> arguments) {
        return new BeanArgumentsBuilder<T>(this, arguments);
    }

    public String getUsage() {
        return new BeanUsageBuilder<T>(this).build();
    }

    public Class<T> getBeanClass() {
        return beanClass;
    }

    public StringTransformerRegistry getStringTransformerRegistry() {
        return stringTransformerRegistry;
    }

    private void addArgumentAdapter(BeanArgumentAdapter beanArgumentAdapter) {
        String displayName = beanArgumentAdapter.getDisplayName();

        if (argumentByDisplayName.containsKey(displayName)) {
            throw new IllegalArgumentException(String.format("Bean <%s> has 2 arguments with name <%s>", beanClass, displayName));
        }

        argumentByDisplayName.put(displayName, beanArgumentAdapter);
    }
}

package org.sweet.bumblebee;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sweet.bumblebee.registry.ArrayStringTransformerRegistryAdapter;
import org.sweet.bumblebee.registry.DefaultStringTransformerRegistry;
import org.sweet.bumblebee.registry.EnumStringTransformerRegistryAdapter;
import org.sweet.bumblebee.registry.ExceptionStringTransformerRegistryAdapter;
import org.sweet.bumblebee.registry.PrimitiveStringTransformerRegistryAdapter;

import java.util.Iterator;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;

public class StringTransformerRegistryBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(StringTransformerRegistryBuilder.class);

    private final StringTransformerRegistry defaultStringTransformerRegistry = new DefaultStringTransformerRegistry();

    private boolean enumEnabled;

    private boolean primitiveEnabled;

    private boolean arrayEnabled;

    private boolean exceptionEnabled;

    public StringTransformerRegistryBuilder() {
        for (Iterator<StringTransformer> it = ServiceLoader.load(StringTransformer.class)
                .iterator(); it.hasNext(); ) {
            try {
                final StringTransformer stringTransformer = it.next();

                defaultStringTransformerRegistry.register(stringTransformer);
            } catch (ServiceConfigurationError e) {
                LOGGER.warn("Failed to load a StringTransformer", e);
            }
        }
    }

    public StringTransformerRegistry build() {
        StringTransformerRegistry result = defaultStringTransformerRegistry;

        if (enumEnabled) {
            result = new EnumStringTransformerRegistryAdapter(result);
        }

        if (primitiveEnabled) {
            result = new PrimitiveStringTransformerRegistryAdapter(result);
        }

        if (arrayEnabled) {
            result = new ArrayStringTransformerRegistryAdapter(result);
        }

        if (exceptionEnabled) {
            result = new ExceptionStringTransformerRegistryAdapter(result);
        }

        return result;
    }

    public StringTransformerRegistryBuilder register(StringTransformer stringTransformer, String name) {
        defaultStringTransformerRegistry.register(stringTransformer, name);

        return this;
    }

    public StringTransformerRegistryBuilder withAll() {
        return withArray().withEnum()
                .withException()
                .withPrimitive();
    }

    public StringTransformerRegistryBuilder withEnum() {
        return withEnum(true);
    }

    public StringTransformerRegistryBuilder withEnum(final boolean enabled) {
        enumEnabled = enabled;

        return this;
    }

    public StringTransformerRegistryBuilder withPrimitive() {
        return withPrimitive(true);
    }

    public StringTransformerRegistryBuilder withPrimitive(final boolean enabled) {
        primitiveEnabled = enabled;

        return this;
    }

    public StringTransformerRegistryBuilder withArray() {
        return withArray(true);
    }

    public StringTransformerRegistryBuilder withArray(final boolean enabled) {
        arrayEnabled = enabled;

        return this;
    }

    public StringTransformerRegistryBuilder withException() {
        return withException(true);
    }

    public StringTransformerRegistryBuilder withException(final boolean enabled) {
        exceptionEnabled = enabled;

        return this;
    }
}

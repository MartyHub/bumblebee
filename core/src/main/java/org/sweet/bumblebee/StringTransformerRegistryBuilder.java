package org.sweet.bumblebee;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sweet.bumblebee.registry.ArrayStringTransformerRegistryAdapter;
import org.sweet.bumblebee.registry.DefaultStringTransformerRegistry;
import org.sweet.bumblebee.registry.EnumStringTransformerRegistryAdapter;
import org.sweet.bumblebee.registry.ExceptionStringTransformerRegistryAdapter;
import org.sweet.bumblebee.registry.PrimitiveStringTransformerRegistryAdapter;
import org.sweet.bumblebee.transformer.StringTransformerContext;
import org.sweet.bumblebee.transformer.StringTransformerWithContext;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;

public class StringTransformerRegistryBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(StringTransformerRegistryBuilder.class);

    private final StringTransformerContext.Builder contextBuilder = StringTransformerContext.builder();

    private final Map<StringTransformer, String> transformers = new HashMap<StringTransformer, String>();

    private boolean enumEnabled;

    private boolean primitiveEnabled;

    private boolean arrayEnabled;

    private boolean exceptionEnabled;

    public StringTransformerRegistry build() {
        StringTransformerRegistry result = new DefaultStringTransformerRegistry();

        registerTransformers(result, contextBuilder.build());

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
        transformers.put(stringTransformer, name);

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

    public StringTransformerRegistryBuilder trueMappings(String... mappings) {
        contextBuilder.trueMappings(mappings);

        return this;
    }

    public StringTransformerRegistryBuilder falseMappings(String... mappings) {
        contextBuilder.falseMappings(mappings);

        return this;
    }

    public StringTransformerRegistryBuilder dateMappings(String... patterns) {
        contextBuilder.dateMappings(patterns);

        return this;
    }

    public StringTransformerRegistryBuilder timeMappings(String... patterns) {
        contextBuilder.timeMappings(patterns);

        return this;
    }

    public StringTransformerRegistryBuilder dateTimeMappings(String... patterns) {
        contextBuilder.dateTimeMappings(patterns);

        return this;
    }

    private void registerTransformers(StringTransformerRegistry registry, StringTransformerContext context) {
        for (Iterator<StringTransformer> it = ServiceLoader.load(StringTransformer.class)
                .iterator(); it.hasNext(); ) {
            try {
                registry.register(setContext(it.next(), context));
            } catch (ServiceConfigurationError e) {
                LOGGER.warn("Failed to load a StringTransformer", e);
            }
        }

        for (Map.Entry<StringTransformer, String> entry : transformers.entrySet()) {
            registry.register(setContext(entry.getKey(), context), entry.getValue());
        }
    }

    private StringTransformer setContext(StringTransformer stringTransformer, StringTransformerContext context) {
        if (stringTransformer instanceof StringTransformerWithContext) {
            ((StringTransformerWithContext) stringTransformer).setContext(context);
        }

        return stringTransformer;
    }
}

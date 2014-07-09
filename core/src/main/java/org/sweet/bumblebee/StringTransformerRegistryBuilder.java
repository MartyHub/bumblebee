package org.sweet.bumblebee;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sweet.bumblebee.registry.*;
import org.sweet.bumblebee.transformer.StringTransformerContext;
import org.sweet.bumblebee.transformer.StringTransformerWithContext;

import java.util.*;

public class StringTransformerRegistryBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(StringTransformerRegistryBuilder.class);

    private final StringTransformerContext context = new StringTransformerContext();

    private final Map<StringTransformer, String> transformers = new HashMap<StringTransformer, String>();

    private boolean enumEnabled;

    private boolean primitiveEnabled;

    private boolean arrayEnabled;

    private boolean exceptionEnabled;

    public StringTransformerRegistry build() {
        StringTransformerRegistry result = new DefaultStringTransformerRegistry();

        registerTransformers(result, context);

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
        return withArray().withEnum().withException().withPrimitive();
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
        context.setProperty(StringTransformerContext.TRUE_MAPPINGS, mappings);

        return this;
    }

    public StringTransformerRegistryBuilder falseMappings(String... mappings) {
        context.setProperty(StringTransformerContext.FALSE_MAPPINGS, mappings);

        return this;
    }

    public StringTransformerRegistryBuilder datePatterns(String... patterns) {
        context.setProperty(StringTransformerContext.DATE_PATTERNS, patterns);

        return this;
    }

    public StringTransformerRegistryBuilder timePatterns(String... patterns) {
        context.setProperty(StringTransformerContext.TIME_PATTERNS, patterns);

        return this;
    }

    public StringTransformerRegistryBuilder dateTimePatterns(String... patterns) {
        context.setProperty(StringTransformerContext.DATE_TIME_PATTERNS, patterns);

        return this;
    }

    public StringTransformerRegistryBuilder property(String name, String... values) {
        context.setProperty(name, values);

        return this;
    }

    private void registerTransformers(StringTransformerRegistry registry, StringTransformerContext context) {
        for (Iterator<StringTransformer> it = ServiceLoader.load(StringTransformer.class).iterator(); it.hasNext(); ) {
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

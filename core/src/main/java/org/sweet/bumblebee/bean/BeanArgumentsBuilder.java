package org.sweet.bumblebee.bean;

import org.sweet.bumblebee.BeanArgumentException;
import org.sweet.bumblebee.BeanArgumentsIntrospector;
import org.sweet.bumblebee.StringTransformer;
import org.sweet.bumblebee.StringTransformerException;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Collections;
import java.util.Set;

public class BeanArgumentsBuilder<T> {

    private final BeanArgumentsIntrospector<T> beanArgumentsIntrospector;

    private final Iterable<Argument> arguments;

    private T bean;

    private Validator validator;

    private Set<ConstraintViolation<T>> constraintViolations;

    public BeanArgumentsBuilder(BeanArgumentsIntrospector<T> beanArgumentsIntrospector, Iterable<Argument> arguments) {
        if (beanArgumentsIntrospector == null) {
            throw new NullPointerException();
        }

        if (arguments == null) {
            throw new NullPointerException();
        }

        this.beanArgumentsIntrospector = beanArgumentsIntrospector;
        this.arguments = arguments;
    }

    public BeanArgumentsBuilder<T> withBean(T bean) {
        this.bean = bean;

        return this;
    }

    public BeanArgumentsBuilder<T> withValidator(Validator validator) {
        this.validator = validator;

        return this;
    }

    public T build() {
        if (bean == null) {
            bean = beanArgumentsIntrospector.newBean();
        }

        processArguments();
        validate();

        return bean;
    }

    public Set<ConstraintViolation<T>> getConstraintViolations() {
        if (constraintViolations == null) {
            return Collections.emptySet();
        }

        return constraintViolations;
    }

    private void processArguments() {
        for (Argument argument : arguments) {
            final BeanArgumentAdapter beanArgumentAdapter = beanArgumentsIntrospector.getArgumentAdapter(argument.getName());

            if (beanArgumentAdapter != null) {
                processArgument(beanArgumentAdapter, argument.getValue());
            }
        }
    }

    private void processArgument(BeanArgumentAdapter beanArgumentAdapter, String argumentValue) {
        StringTransformer<?> stringTransformer = beanArgumentsIntrospector.getStringTransformerRegistry().getStringConverter(beanArgumentAdapter.getJavaName(), beanArgumentAdapter.getType());
        Object value;

        try {
            value = stringTransformer.convert(argumentValue);
        } catch (StringTransformerException e) {
            throw new BeanArgumentException(String.format("Invalid value <%s> for argument <%s>, must be <%s>", argumentValue, beanArgumentAdapter.getDisplayName(), stringTransformer.getUsage()), e);
        }

        beanArgumentAdapter.setArgumentValue(bean, value);
    }

    private void validate() {
        if (validator != null) {
            constraintViolations = validator.validate(bean);
        }
    }
}

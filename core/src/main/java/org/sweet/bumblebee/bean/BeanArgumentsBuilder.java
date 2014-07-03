package org.sweet.bumblebee.bean;

import org.sweet.bumblebee.BeanArgumentsIntrospector;
import org.sweet.bumblebee.BumblebeeException;
import org.sweet.bumblebee.StringTransformer;
import org.sweet.bumblebee.util.ValidationResult;

public class BeanArgumentsBuilder<T> extends BeanBuilder<T> {

    private final ValidationResult validationResult = new ValidationResult();

    private final Iterable<Argument> arguments;

    public BeanArgumentsBuilder(BeanArgumentsIntrospector beanArgumentsIntrospector, Iterable<Argument> arguments) {
        super(beanArgumentsIntrospector);

        this.arguments = arguments;
    }

    protected void doBuild() {
        processArguments(arguments);
        checkValidationResult();

        checkMandatoryArguments();
        checkValidationResult();

        validate();
        checkValidationResult();
    }

    private void processArguments(Iterable<Argument> arguments) {
        for (Argument argument : arguments) {
            final BeanArgumentAdapter beanArgumentAdapter = beanArgumentsIntrospector.getArgumentAdapter(argument.getName());

            if (beanArgumentAdapter == null) {
                validationResult.addError(String.format("Unknown argument <%s>", argument.getName()));
            } else {
                processArgument(beanArgumentAdapter, argument.getValue());
            }
        }
    }

    private void processArgument(BeanArgumentAdapter beanArgumentAdapter, String argumentValue) {
        StringTransformer<?> stringTransformer = beanArgumentsIntrospector.getStringTransformerRegistry()
                .getStringConverter(beanArgumentAdapter.getJavaName(), beanArgumentAdapter.getType());
        Object value;

        try {
            value = stringTransformer.convert(argumentValue);
        } catch (BumblebeeException e) {
            validationResult.addError(String.format("Invalid value <%s> for argument <%s>, must be <%s>", argumentValue, beanArgumentAdapter.getDisplayName(),
                    stringTransformer.getUsage()));

            return;
        }

        beanArgumentAdapter.setArgumentValue(getBean(), value);
    }

    private void checkMandatoryArguments() {
        for (BeanArgumentAdapter beanArgumentAdapter : beanArgumentsIntrospector) {
            if (!beanArgumentAdapter.isOptional() && beanArgumentAdapter.getArgumentValue(getBean()) == null) {
                validationResult.addError(String.format("The following argument is mandatory : <%s>", beanArgumentAdapter.getDisplayName()));
            }
        }
    }

    private void validate() {
        for (BeanArgumentAdapter beanArgumentAdapter : beanArgumentsIntrospector) {
            beanArgumentAdapter.validate(getBean(), validationResult);
        }

        if (getBean() instanceof ValidatableBean) {
            ((ValidatableBean) getBean()).validate(validationResult);
        }
    }

    private void checkValidationResult() {
        if (!validationResult.isOk()) {
            throw new BumblebeeException(validationResult.toString());
        }
    }
}

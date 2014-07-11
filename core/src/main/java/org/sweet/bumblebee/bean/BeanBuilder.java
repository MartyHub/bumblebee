package org.sweet.bumblebee.bean;

import org.sweet.bumblebee.BeanArgumentsIntrospector;
import org.sweet.bumblebee.BumblebeeException;

public abstract class BeanBuilder<T> {

    protected final BeanArgumentsIntrospector<T> beanArgumentsIntrospector;

    protected final boolean failedOnUnknownArgument;

    protected final T bean;

    public BeanBuilder(BeanArgumentsIntrospector<T> beanArgumentsIntrospector) {
        this(beanArgumentsIntrospector, null);
    }

    public BeanBuilder(BeanArgumentsIntrospector<T> beanArgumentsIntrospector, T bean) {
        if (beanArgumentsIntrospector == null) {
            throw new NullPointerException();
        }

        this.beanArgumentsIntrospector = beanArgumentsIntrospector;

        if (bean == null) {
            this.failedOnUnknownArgument = true;
            this.bean = newBean();
        } else {
            this.failedOnUnknownArgument = false;
            this.bean = bean;
        }
    }

    public final T build() {
        doBuild();

        return bean;
    }

    protected abstract void doBuild();

    private T newBean() {
        try {
            return beanArgumentsIntrospector.getBeanClass()
                    .newInstance();
        } catch (Exception e) {
            throw new BumblebeeException(String.format("Failed to create new instance of <%s>", beanArgumentsIntrospector.getBeanClass()), e);
        }
    }
}

package org.sweet.bumblebee.bean;

import org.sweet.bumblebee.BeanArgumentsIntrospector;
import org.sweet.bumblebee.BumblebeeException;

public abstract class BeanBuilder<T> {

    protected final BeanArgumentsIntrospector<T> beanArgumentsIntrospector;

    private T bean;

    public BeanBuilder(BeanArgumentsIntrospector<T> beanArgumentsIntrospector) {
        if (beanArgumentsIntrospector == null) {
            throw new NullPointerException();
        }

        this.beanArgumentsIntrospector = beanArgumentsIntrospector;
        this.bean = newBean();
    }

    public final T build() {
        this.bean = newBean();

        doBuild();

        return bean;
    }

    protected abstract void doBuild();

    protected final T getBean() {
        return bean;
    }

    private T newBean() {
        try {
            return beanArgumentsIntrospector.getBeanClass()
                    .newInstance();
        } catch (Exception e) {
            throw new BumblebeeException(String.format("Failed to create new instance of <%s>", beanArgumentsIntrospector.getBeanClass()), e);
        }
    }
}

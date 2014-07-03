package org.sweet.bumblebee;

public interface StringTransformer<T> {

    Class<T> getKey();

    T convert(String s) throws BumblebeeException;

    String getUsage();
}

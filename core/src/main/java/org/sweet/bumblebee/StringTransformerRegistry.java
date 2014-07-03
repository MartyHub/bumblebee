package org.sweet.bumblebee;

public interface StringTransformerRegistry {

    <T> StringTransformer<T> register(StringTransformer<T> stringTransformer);

    <T> StringTransformer<?> register(StringTransformer<T> stringTransformer, String name);

    <T> StringTransformer<T> getStringConverter(String name, Class<T> type);
}

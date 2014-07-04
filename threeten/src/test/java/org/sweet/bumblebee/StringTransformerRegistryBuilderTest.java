package org.sweet.bumblebee;

import org.junit.Before;
import org.junit.Test;
import org.sweet.bumblebee.transformer.LocalDateStringTransformer;
import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.LocalTime;

import java.lang.reflect.Array;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class StringTransformerRegistryBuilderTest {

    private StringTransformerRegistry stringTransformerRegistry;

    @Before
    public void setup() {
        stringTransformerRegistry = new StringTransformerRegistryBuilder().withAll()
                .build();
    }

    @Test
    public void convert_local_date() {
        test(LocalDate.class, "2014-07-01", LocalDate.of(2014, 7, 1));
        test(LocalDate.class, "20140701", LocalDate.of(2014, 7, 1));
    }

    @Test
    public void convert_local_date_time() {
        test(LocalDateTime.class, "2014-07-01T14:20:05", LocalDateTime.of(2014, 7, 1, 14, 20, 5));
        test(LocalDateTime.class, "20140701142005", LocalDateTime.of(2014, 7, 1, 14, 20, 5));
    }

    @Test
    public void convert_local_time() {
        test(LocalTime.class, "14:20:05", LocalTime.of(14, 20, 5));
        test(LocalTime.class, "142005", LocalTime.of(14, 20, 5));
    }

    @Test
    public void test_override() {
        String name = "myProperty";

        stringTransformerRegistry.register(new LocalDateStringTransformer("dd/MM/yyyy"), name);

        test(name, LocalDate.class, "01/07/2014", LocalDate.of(2014, 7, 1));
        test(LocalDate.class, "2014-07-01", LocalDate.of(2014, 7, 1));
    }

    private <T> void test(Class<T> type, String s, T value) {
        test(null, type, s, value);
    }

    private <T> void test(String name, Class<T> type, String s, T value) {
        assertThat(stringTransformerRegistry.getStringConverter(name, type)
                .convert(s), equalTo(value));

        test_array(name, type, s, value);
    }

    private <T> void test_array(String name, Class<T> type, String s, T value) {
        Object values = Array.newInstance(type, 2);

        Array.set(values, 0, value);
        Array.set(values, 1, value);

        assertThat(stringTransformerRegistry.getStringConverter(name, values.getClass())
                .convert(s + "," + s), equalTo(values));
    }
}

package org.sweet.bumblebee;

import org.junit.Before;
import org.junit.Test;
import org.sweet.bumblebee.transformer.DateStringTransformer;

import java.io.File;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class StringTransformerRegistryBuilderTest {

    private StringTransformerRegistry stringTransformerRegistry;

    @Before
    public void setup() {
        stringTransformerRegistry = new StringTransformerRegistryBuilder().withAll()
                .build();
    }

    @Test(expected = BumblebeeException.class)
    public void convert_unknown() {
        test(Void.class, "whatever", null);
    }

    @Test
    public void convert_big_decimal() {
        test(BigDecimal.class, "1234567890.0123456789", new BigDecimal("1234567890.0123456789"));
    }

    @Test
    public void convert_big_integer() {
        test(BigInteger.class, "1234567890", new BigInteger("1234567890"));
    }

    @Test
    public void convert_boolean() {
        test(Boolean.class, "y", Boolean.TRUE);
        test(Boolean.class, "n", Boolean.FALSE);

        test(Boolean.TYPE, "y", Boolean.TRUE);
        test(Boolean.TYPE, "n", Boolean.FALSE);
    }

    @Test
    public void convert_byte() {
        test(Byte.class, "12", (byte) 12);
        test(Byte.TYPE, "12", (byte) 12);
    }

    @Test
    public void convert_character() {
        test(Character.class, "c", 'c');
        test(Character.TYPE, "c", 'c');
    }

    @Test
    public void convert_class() {
        test(Class.class, getClass().getName(), getClass());
    }

    @Test
    public void convert_date() throws ParseException {
        final String date = "2014-07-03T12:02:30";

        test(Date.class, date, new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(date));
    }

    @Test
    public void convert_double() {
        test(Double.class, "123456789.0123456789", 123456789.0123456789);
        test(Double.TYPE, "123456789.0123456789", 123456789.0123456789);
    }

    @Test
    public void convert_enum() {
        test(TimeUnit.class, "SECONDS", TimeUnit.SECONDS);
    }

    @Test
    public void convert_file() {
        test(File.class, "filename.txt", new File("filename.txt"));
    }

    @Test
    public void convert_float() {
        test(Float.class, "123.456", 123.456f);
        test(Float.TYPE, "123.456", 123.456f);
    }

    @Test
    public void convert_integer() {
        test(Integer.class, "123", 123);
        test(Integer.TYPE, "123", 123);
    }

    @Test
    public void convert_long() {
        test(Long.class, "1234567890", 1234567890L);
        test(Long.TYPE, "1234567890", 1234567890L);
    }

    @Test
    public void convert_short() {
        test(Short.class, "1234", (short) 1234);
        test(Short.TYPE, "1234", (short) 1234);
    }

    @Test
    public void convert_string() {
        final String value = "hello world";

        test(String.class, value, value);
    }

    @Test
    public void convert_time() {
        final String value = "12:16:30";

        test(Time.class, value, Time.valueOf(value));
    }

    @Test
    public void convert_timestamp() {
        final String value = "2014-07-03 12:16:30";

        test(Timestamp.class, value, Timestamp.valueOf(value));
    }

    @Test
    public void test_override() throws ParseException {
        String name = "myProperty";

        stringTransformerRegistry.register(new DateStringTransformer("yyyyMMdd"), name);

        String date = "20140703";
        test(name, Date.class, date, new SimpleDateFormat("yyyyMMdd").parse(date));

        date = "2014-07-03T12:02:30";
        test(Date.class, date, new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(date));
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

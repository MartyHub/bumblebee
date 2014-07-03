package org.sweet.bumblebee.bean;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class ArgumentTest {

    @Test(expected = NullPointerException.class)
    public void arg_must_not_be_null() {
        new Argument(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void arg_must_start_with_dash() {
        new Argument("name=value");
    }

    @Test(expected = IllegalArgumentException.class)
    public void arg_must_have_a_value() {
        new Argument("-name");
    }

    @Test(expected = IllegalArgumentException.class)
    public void arg_must_have_a_name() {
        new Argument("-=value");
    }

    @Test
    public void standard_arg_is_valid() {
        Argument argument = new Argument("-name=value");

        assertThat(argument.getName(), equalTo("name"));
        assertThat(argument.getValue(), equalTo("value"));
    }

    @Test
    public void arg_with_no_value_is_valid() {
        Argument argument = new Argument("-name=");

        assertThat(argument.getName(), equalTo("name"));
        assertThat(argument.getValue(), equalTo(""));
    }
}

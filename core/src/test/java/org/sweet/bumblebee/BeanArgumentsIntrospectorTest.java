package org.sweet.bumblebee;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sweet.bumblebee.bean.ArrayArgumentProvider;
import org.sweet.bumblebee.bean.BeanArgumentAdapter;
import org.sweet.bumblebee.bean.BeanArgumentsBuilder;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class BeanArgumentsIntrospectorTest {

    private BeanArgumentsIntrospector<SampleBean> beanArgumentsIntrospector;

    private ValidatorFactory validatorFactory;

    private Validator validator;

    @Before
    public void setup() {
        beanArgumentsIntrospector = new BeanArgumentsIntrospector<SampleBean>(new StringTransformerRegistryBuilder().withAll().build(), SampleBean.class);
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @After
    public void clean() {
        if (validatorFactory != null) {
            validatorFactory.close();
        }
    }

    @Test
    public void test_arguments() {
        int count = 0;
        int optionalCount = 0;

        for (BeanArgumentAdapter beanArgumentAdapter : beanArgumentsIntrospector) {
            ++count;

            if (beanArgumentAdapter.isOptional()) {
                ++optionalCount;
            }
        }

        assertThat(count, is(4));
        assertThat(optionalCount, is(3));
    }

    @Test
    public void test_mandatory_argument() {
        BeanArgumentsBuilder<SampleBean> builder = beanArgumentsIntrospector.builder(new ArrayArgumentProvider(new String[0])).withValidator(validator);

        assertThat(builder.build(), is(notNullValue()));
        assertThat(builder.getConstraintViolations().size(), is(1));
    }

    @Test
    public void test_mandatory_argument_with_default_value() {
        SampleBean bean = beanArgumentsIntrospector.builder(new ArrayArgumentProvider(new String[]{"-flag3=y"})).withValidator(validator).build();

        assertThat(bean.isPrimitiveFlag1(), equalTo(Boolean.FALSE));
    }

    @Test
    public void test_set_argument_value() {
        SampleBean bean = beanArgumentsIntrospector.builder(new ArrayArgumentProvider(new String[]{"-flag3=y"})).withValidator(validator).build();

        assertThat(bean.getFlag3(), equalTo(Boolean.TRUE));
    }

    @Test
    public void test_doc() {
        assertThat(beanArgumentsIntrospector.getArgumentAdapter("primitiveFlag1").getDoc(), is(notNullValue()));
    }

    @Test
    public void test_validation() {
        BeanArgumentsBuilder<SampleBean> builder = beanArgumentsIntrospector.builder(new ArrayArgumentProvider(new String[]{"-flag3=y", "-primitiveFlag2=y"})).withValidator(validator);

        assertThat(builder.build(), is(notNullValue()));
        assertThat(builder.getConstraintViolations().size(), is(1));
    }

    @Test
    public void test_usage() {
        String usage = beanArgumentsIntrospector.getUsage();

        System.out.println(usage);

        assertThat(usage, is(notNullValue()));
    }
}

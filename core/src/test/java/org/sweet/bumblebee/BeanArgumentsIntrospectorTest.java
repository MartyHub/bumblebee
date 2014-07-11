package org.sweet.bumblebee;

import org.junit.Before;
import org.junit.Test;
import org.sweet.bumblebee.bean.ArrayArgumentProvider;
import org.sweet.bumblebee.bean.BeanArgumentAdapter;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class BeanArgumentsIntrospectorTest {

    private BeanArgumentsIntrospector<SampleBean> beanArgumentsIntrospector;

    @Before
    public void setup() {
        beanArgumentsIntrospector = new BeanArgumentsIntrospector<SampleBean>(new StringTransformerRegistryBuilder().withAll()
                .build(), SampleBean.class);
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
        assertThat(optionalCount, is(2));
    }

    @Test(expected = BumblebeeException.class)
    public void test_mandatory_argument() {
        beanArgumentsIntrospector.build(new ArrayArgumentProvider(new String[0]));
    }

    @Test
    public void test_mandatory_argument_with_default_value() {
        SampleBean bean = beanArgumentsIntrospector.build(new ArrayArgumentProvider(new String[] {"-flag3=y"}));

        assertThat(bean.isPrimitiveFlag1(), equalTo(Boolean.FALSE));
    }

    @Test
    public void test_set_argument_value() {
        SampleBean bean = beanArgumentsIntrospector.build(new ArrayArgumentProvider(new String[] {"-flag3=y"}));

        assertThat(bean.getFlag3(), equalTo(Boolean.TRUE));
    }

    @Test
    public void test_doc() {
        assertThat(beanArgumentsIntrospector.getArgumentAdapter("primitiveFlag1")
                .getDoc(), is(notNullValue()));
    }

    @Test(expected = BumblebeeException.class)
    public void test_validation() {
        beanArgumentsIntrospector.build(new ArrayArgumentProvider(new String[] {"-flag3=y", "-primitiveFlag1=y"}));
    }

    @Test
    public void test_usage() {
        String usage = beanArgumentsIntrospector.getUsage();

        System.out.println(usage);

        assertThat(usage, is(notNullValue()));
    }
}

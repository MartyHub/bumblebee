package org.sweet.bumblebee.bean;

import org.sweet.bumblebee.BumblebeeException;
import org.sweet.bumblebee.Doc;
import org.sweet.bumblebee.util.ValidationResult;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.util.StringTokenizer;

public class BeanArgumentAdapter implements Comparable<BeanArgumentAdapter> {

    private final String javaName;

    private final String displayName;

    private final Class<?> type;

    private final boolean optional;

    private final String doc;

    private final boolean subProperty;

    public BeanArgumentAdapter(PropertyDescriptor propertyDescriptor) {
        if (propertyDescriptor == null) {
            throw new NullPointerException();
        }

        this.javaName = propertyDescriptor.getName();
        this.type = propertyDescriptor.getPropertyType();
        this.subProperty = false;
        this.optional = javaName.startsWith("optional");
        this.displayName = optional ? Introspector.decapitalize(javaName.substring(8)) : javaName;

        if (propertyDescriptor.getWriteMethod() == null) {
            this.doc = null;
        } else {
            Doc docAnnotation = propertyDescriptor.getWriteMethod()
                    .getAnnotation(Doc.class);

            this.doc = docAnnotation == null ? null : docAnnotation.value();
        }
    }

    private BeanArgumentAdapter(String javaName, String displayName, Class<?> type, final boolean optional, String doc) {
        this.javaName = javaName;
        this.displayName = displayName;
        this.type = type;
        this.optional = optional;
        this.doc = doc;
        this.subProperty = true;
    }

    public BeanArgumentAdapter withPrefix(String prefix) {
        return new BeanArgumentAdapter(prefix + "." + javaName, displayName, type, optional, doc);
    }

    public String getJavaName() {
        return javaName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Class<?> getType() {
        return type;
    }

    public boolean isOptional() {
        return optional;
    }

    public boolean isSubProperty() {
        return subProperty;
    }

    public String getDoc() {
        return doc;
    }

    public Object getArgumentValue(Object bean) {
        Object safeBean = bean;
        String propertyName = javaName;

        if (subProperty) {
            for (StringTokenizer st = new StringTokenizer(javaName, "."); st.hasMoreTokens(); ) {
                propertyName = st.nextToken();

                if (!st.hasMoreTokens()) {
                    break;
                }

                final PropertyDescriptor propertyDescriptor = getPropertyDescriptor(safeBean, propertyName);

                safeBean = getProperty(safeBean, propertyDescriptor);

                if (safeBean == null) {
                    return null;
                }
            }
        }

        return getProperty(safeBean, getPropertyDescriptor(safeBean, propertyName));
    }

    public String getDefaultValue(Object bean) {
        try {
            final Object value = getArgumentValue(bean);

            if (value != null) {
                if (value.getClass()
                        .isArray()) {
                    StringBuilder sb = new StringBuilder();

                    for (int i = 0, length = Array.getLength(value); i < length; ++i) {
                        if (sb.length() > 0) {
                            sb.append(", ");
                        }

                        sb.append(Array.get(value, i));
                    }

                    return sb.toString();
                } else {
                    return String.valueOf(value);
                }
            }
        } catch (Exception e) {
        }

        return null;
    }

    public void setArgumentValue(Object bean, Object value) {
        Object safeBean = bean;
        String propertyName = javaName;

        if (subProperty) {
            for (StringTokenizer st = new StringTokenizer(javaName, "."); st.hasMoreTokens(); ) {
                propertyName = st.nextToken();

                if (!st.hasMoreTokens()) {
                    break;
                }

                final PropertyDescriptor propertyDescriptor = getPropertyDescriptor(safeBean, propertyName);
                Object propertyValue = getProperty(safeBean, propertyDescriptor);

                if (propertyValue == null) {
                    propertyValue = newInstance(propertyDescriptor);

                    setProperty(safeBean, propertyDescriptor, propertyValue);
                }

                safeBean = propertyValue;
            }
        }

        setProperty(safeBean, propertyName, value);
    }

    public void validate(Object bean, ValidationResult validationResult) {
        if (subProperty) {
            Object safeBean = bean;

            for (StringTokenizer st = new StringTokenizer(javaName, "."); st.hasMoreTokens(); ) {
                final String propertyName = st.nextToken();
                final PropertyDescriptor propertyDescriptor = getPropertyDescriptor(safeBean, propertyName);
                Object propertyValue = getProperty(safeBean, propertyDescriptor);

                if (propertyValue == null) {
                    break;
                }

                if (propertyValue instanceof ValidatableBean) {
                    ((ValidatableBean) propertyValue).validate(validationResult);
                }

                safeBean = propertyValue;
            }
        }
    }

    @Override
    public int compareTo(BeanArgumentAdapter other) {
        int result = 0;

        if (isOptional()) {
            if (!other.isOptional()) {
                result = 1;
            }
        } else if (other.isOptional()) {
            result = -1;
        }

        if (result == 0) {
            result = getDisplayName().compareTo(other.getDisplayName());
        }

        return result;
    }

    @Override
    public int hashCode() {
        return getDisplayName().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BeanArgumentAdapter other = (BeanArgumentAdapter) o;

        return getDisplayName().equals(other.getDisplayName());
    }

    private void setProperty(Object bean, String propertyName, Object value) {
        setProperty(bean, getPropertyDescriptor(bean, propertyName), value);
    }

    private void setProperty(Object bean, PropertyDescriptor propertyDescriptor, Object value) {
        try {
            propertyDescriptor.getWriteMethod()
                    .invoke(bean, value);
        } catch (Exception e) {
            throw new BumblebeeException(String.format("Failed to set property <%s> on bean <%s> with value <%s>", propertyDescriptor.getName(), bean, value), e);
        }
    }

    private Object getProperty(Object bean, PropertyDescriptor propertyDescriptor) {
        try {
            return propertyDescriptor.getReadMethod()
                    .invoke(bean);
        } catch (Exception e) {
            throw new BumblebeeException(String.format("Failed to get property <%s> on bean <%s>", propertyDescriptor.getName(), bean), e);
        }
    }

    private PropertyDescriptor getPropertyDescriptor(Object bean, String propertyName) {
        try {
            for (PropertyDescriptor propertyDescriptor : Introspector.getBeanInfo(bean.getClass())
                    .getPropertyDescriptors()) {
                if (propertyDescriptor.getName()
                        .equals(propertyName)) {
                    return propertyDescriptor;
                }
            }
        } catch (IntrospectionException e) {
            throw new BumblebeeException(String.format("Failed to introspect class <%s>", bean.getClass()), e);
        }

        throw new BumblebeeException(String.format("Failed to find property <%s> for bean <%s>", propertyName, bean));
    }

    private Object newInstance(PropertyDescriptor propertyDescriptor) {
        try {
            return propertyDescriptor.getPropertyType()
                    .newInstance();
        } catch (Exception e) {
            throw new BumblebeeException(String.format("Failed to create new instance of <%s>", propertyDescriptor.getPropertyType()), e);
        }
    }
}

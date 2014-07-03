package org.sweet.bumblebee.bean;

import org.sweet.bumblebee.util.ValidationResult;

public interface ValidatableBean {

    void validate(ValidationResult result);
}

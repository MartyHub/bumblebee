package org.sweet.bumblebee;

import org.sweet.bumblebee.bean.ValidatableBean;
import org.sweet.bumblebee.util.ValidationResult;

public class SampleBean implements ValidatableBean {

    private boolean primitiveFlag1;

    private boolean optionalPrimitiveFlag2;

    private Boolean flag3;

    private Boolean optionalFlag4;

    public boolean isPrimitiveFlag1() {
        return primitiveFlag1;
    }

    @Doc("sample description")
    public void setPrimitiveFlag1(boolean primitiveFlag1) {
        this.primitiveFlag1 = primitiveFlag1;
    }

    public boolean isOptionalPrimitiveFlag2() {
        return optionalPrimitiveFlag2;
    }

    public void setOptionalPrimitiveFlag2(boolean optionalPrimitiveFlag2) {
        this.optionalPrimitiveFlag2 = optionalPrimitiveFlag2;
    }

    public Boolean getFlag3() {
        return flag3;
    }

    public void setFlag3(Boolean flag3) {
        this.flag3 = flag3;
    }

    public Boolean getOptionalFlag4() {
        return optionalFlag4;
    }

    public void setOptionalFlag4(Boolean optionalFlag4) {
        this.optionalFlag4 = optionalFlag4;
    }

    @Override
    public void validate(ValidationResult result) {
        if (primitiveFlag1 != optionalPrimitiveFlag2) {
            result.addError("primitiveFlag1 and primitiveFlag2 must have the same value");
        }
    }
}

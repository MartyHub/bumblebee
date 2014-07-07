package org.sweet.bumblebee;

import org.sweet.bumblebee.bean.ValidatableBean;
import org.sweet.bumblebee.util.ValidationResult;

public class SampleBean implements ValidatableBean {

    private boolean primitiveFlag1;

    private boolean primitiveFlag2;

    private Boolean flag3;

    private Boolean flag4;

    public boolean isPrimitiveFlag1() {
        return primitiveFlag1;
    }

    @Doc("sample description")
    public void setPrimitiveFlag1(final boolean primitiveFlag1) {
        this.primitiveFlag1 = primitiveFlag1;
    }

    public boolean isPrimitiveFlag2() {
        return primitiveFlag2;
    }

    @Optional
    public void setPrimitiveFlag2(final boolean primitiveFlag2) {
        this.primitiveFlag2 = primitiveFlag2;
    }

    public Boolean getFlag3() {
        return flag3;
    }

    public void setFlag3(Boolean flag3) {
        this.flag3 = flag3;
    }

    public Boolean getFlag4() {
        return flag4;
    }

    @Optional
    public void setFlag4(Boolean flag4) {
        this.flag4 = flag4;
    }

    @Override
    public void validate(ValidationResult result) {
        if (primitiveFlag1 != primitiveFlag2) {
            result.addError("primitiveFlag1 and primitiveFlag2 must have the same value");
        }
    }
}

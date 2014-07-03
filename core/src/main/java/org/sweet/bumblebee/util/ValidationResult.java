package org.sweet.bumblebee.util;

import java.util.SortedSet;
import java.util.TreeSet;

public class ValidationResult {

    private final SortedSet<String> errors = new TreeSet<String>();

    public void addError(String error) {
        errors.add(error);
    }

    public boolean isOk() {
        return errors.isEmpty();
    }

    public String[] getErrors() {
        return errors.toArray(new String[errors.size()]);
    }

    @Override
    public String toString() {
        if (isOk()) {
            return "OK";
        } else {
            StringBuilder sb = new StringBuilder();

            for (String error : errors) {
                if (sb.length() > 0) {
                    sb.append(System.getProperty("line.separator"));
                }

                sb.append(error);
            }

            return sb.toString();
        }
    }
}

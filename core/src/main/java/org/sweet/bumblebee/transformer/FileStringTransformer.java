package org.sweet.bumblebee.transformer;

import org.sweet.bumblebee.BumblebeeException;
import org.sweet.bumblebee.StringTransformer;

import java.io.File;

public class FileStringTransformer implements StringTransformer<File> {

    @Override
    public Class<File> getKey() {
        return File.class;
    }

    @Override
    public File convert(String s) throws BumblebeeException {
        return new File(s);
    }

    @Override
    public String getUsage() {
        return "filename";
    }
}

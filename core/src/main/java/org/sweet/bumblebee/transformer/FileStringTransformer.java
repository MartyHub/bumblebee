package org.sweet.bumblebee.transformer;

import org.sweet.bumblebee.StringTransformer;
import org.sweet.bumblebee.StringTransformerException;

import java.io.File;

public class FileStringTransformer implements StringTransformer<File> {

    @Override
    public Class<File> getKey() {
        return File.class;
    }

    @Override
    public File convert(String s) throws StringTransformerException {
        return new File(s);
    }

    @Override
    public String getUsage() {
        return "filename";
    }
}

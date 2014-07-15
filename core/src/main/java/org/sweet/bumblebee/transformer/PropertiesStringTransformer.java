package org.sweet.bumblebee.transformer;

import org.sweet.bumblebee.StringTransformer;
import org.sweet.bumblebee.StringTransformerException;

import java.io.*;
import java.util.Properties;

public class PropertiesStringTransformer implements StringTransformer<Properties> {

    @Override
    public Class<Properties> getKey() {
        return Properties.class;
    }

    @Override
    public Properties convert(String s) throws StringTransformerException {
        InputStream is = null;
        File propertiesFileName = new File(s);

        if (propertiesFileName.exists()) {
            try {
                is = new BufferedInputStream(new FileInputStream(propertiesFileName));

                return load(propertiesFileName.getAbsolutePath(), is);
            } catch (FileNotFoundException e) {
                throw new StringTransformerException(String.format("Failed to find properties file <%s>", propertiesFileName.getAbsolutePath()));
            }
        } else {
            is = ClassLoader.getSystemResourceAsStream(s);

            if (is == null) {
                throw new StringTransformerException(String.format("Failed to read properties from <%s> : neither a file nor a classpath resource", s));
            }

            return load(s, is);
        }
    }

    @Override
    public String getUsage() {
        return "properties file name or classpath resource";
    }

    private Properties load(String source, InputStream is) {
        try {
            Properties result = new Properties();

            result.load(is);

            return result;
        } catch (IOException e) {
            throw new StringTransformerException(String.format("Failed to read properties from <%s>", source), e);
        } finally {
            try {
                is.close();
            } catch (IOException ioe) {
            }
        }
    }
}

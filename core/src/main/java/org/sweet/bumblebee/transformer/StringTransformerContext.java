package org.sweet.bumblebee.transformer;

import java.util.HashMap;
import java.util.Map;

public class StringTransformerContext {

    public static final String TRUE_MAPPINGS = "TRUE_MAPPINGS";

    public static final String FALSE_MAPPINGS = "FALSE_MAPPINGS";

    public static final String DATE_PATTERNS = "DATE_PATTERNS";

    public static final String TIME_PATTERNS = "TIME_PATTERNS";

    public static final String DATE_TIME_PATTERNS = "DATE_TIME_PATTERNS";

    private final Map<String, String[]> properties = new HashMap<String, String[]>(5);

    public StringTransformerContext() {
        properties.put(TRUE_MAPPINGS, new String[]{"y", "yes", "true"});
        properties.put(FALSE_MAPPINGS, new String[]{"n", "no", "false"});
        properties.put(DATE_PATTERNS, new String[]{"yyyy-MM-dd", "yyyyMMdd"});
        properties.put(TIME_PATTERNS, new String[]{"HH:mm:ss", "HHmmss"});
        properties.put(DATE_TIME_PATTERNS, new String[]{"yyyy-MM-dd'T'HH:mm:ss", "yyyyMMddHHmmss"});
    }

    public String[] getProperty(String name) {
        String[] values = properties.get(name);

        if (values == null) {
            return new String[0];
        }

        return values;
    }

    public void setProperty(String name, String[] values) {
        properties.put(name, values);
    }

    public String[] getTrueMappings() {
        return getProperty(TRUE_MAPPINGS);
    }

    public String[] getFalseMappings() {
        return getProperty(FALSE_MAPPINGS);
    }

    public String[] getDatePatterns() {
        return getProperty(DATE_PATTERNS);
    }

    public String[] getTimePatterns() {
        return getProperty(TIME_PATTERNS);
    }

    public String[] getDateTimePatterns() {
        return getProperty(DATE_TIME_PATTERNS);
    }
}

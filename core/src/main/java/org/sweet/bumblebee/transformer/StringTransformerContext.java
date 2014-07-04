package org.sweet.bumblebee.transformer;

public class StringTransformerContext {

    public static final Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final StringTransformerContext result = new StringTransformerContext();

        public Builder trueMappings(String... mappings) {
            result.setTrueMappings(mappings);

            return this;
        }

        public Builder falseMappings(String... mappings) {
            result.setFalseMappings(mappings);

            return this;
        }

        public Builder dateMappings(String... patterns) {
            result.setDatePatterns(patterns);

            return this;
        }

        public Builder timeMappings(String... patterns) {
            result.setTimePatterns(patterns);

            return this;
        }

        public Builder dateTimeMappings(String... patterns) {
            result.setDateTimePatterns(patterns);

            return this;
        }

        public StringTransformerContext build() {
            return result;
        }
    }

    private String[] trueMappings = {"y", "yes", "true"};

    private String[] falseMappings = {"n", "no", "false"};

    private String[] datePatterns = {"yyyy-MM-dd", "yyyyMMdd"};

    private String[] timePatterns = {"HH:mm:ss", "HHmmss"};

    private String[] dateTimePatterns = {"yyyy-MM-dd'T'HH:mm:ss", "yyyyMMddHHmmss"};

    public String[] getTrueMappings() {
        return trueMappings;
    }

    public String[] getFalseMappings() {
        return falseMappings;
    }

    public String[] getDatePatterns() {
        return datePatterns;
    }

    public String[] getTimePatterns() {
        return timePatterns;
    }

    public String[] getDateTimePatterns() {
        return dateTimePatterns;
    }

    private void setTrueMappings(String[] trueMappings) {
        if (trueMappings == null) {
            throw new NullPointerException();
        }

        this.trueMappings = trueMappings;
    }

    private void setFalseMappings(String[] falseMappings) {
        if (falseMappings == null) {
            throw new NullPointerException();
        }

        this.falseMappings = falseMappings;
    }

    private void setDatePatterns(String[] datePatterns) {
        if (datePatterns == null) {
            throw new NullPointerException();
        }

        this.datePatterns = datePatterns;
    }

    private void setTimePatterns(String[] timePatterns) {
        if (timePatterns == null) {
            throw new NullPointerException();
        }

        this.timePatterns = timePatterns;
    }

    private void setDateTimePatterns(String[] dateTimePatterns) {
        if (dateTimePatterns == null) {
            throw new NullPointerException();
        }

        this.dateTimePatterns = dateTimePatterns;
    }
}

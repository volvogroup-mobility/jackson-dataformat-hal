package io.openapitools.jackson.dataformat.hal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Representation of a HAL-FORMS template.
 */
public class HALTemplate implements Serializable {
    private static final long serialVersionUID = 1L;

    private String title;
    private String method;
    private String contentType;
    private List<Property> properties;

    public HALTemplate() {
        // Used by databind.
    }

    protected HALTemplate(Builder builder) {
        this.title = builder.title;
        this.method = builder.method;
        this.contentType = builder.contentType;
        this.properties = builder.properties;
    }

    public String getTitle() {
        return title;
    }

    public String getMethod() {
        return method;
    }

    public String getContentType() {
        return contentType;
    }

    public List<Property> getProperties() {
        return properties;
    }

    /**
     * Builder to help build {@link HALTemplate} instances.
     */
    public static class Builder {
        private String title;
        private String method;
        private String contentType;
        private List<Property> properties = new ArrayList<>();

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder method(String method) {
            this.method = method;
            return this;
        }

        public Builder contentType(String contentType) {
            this.contentType = contentType;
            return this;
        }

        public Builder addProperty(Property property) {
            if (property != null) {
                properties.add(property);
            }
            return this;
        }

        public Builder properties(List<Property> properties) {
            this.properties = properties == null ? new ArrayList<>() : new ArrayList<>(properties);
            return this;
        }

        public HALTemplate build() {
            this.properties = Collections.unmodifiableList(properties);
            return new HALTemplate(this);
        }
    }

    /**
     * Representation of a HAL-FORMS template property.
     */
    public static class Property implements Serializable {
        private static final long serialVersionUID = 1L;

        private String name;
        private String prompt;
        private Boolean required;
        private Boolean readOnly;
        private String regex;
        private Object value;

        public Property() {
            // Used by databind.
        }

        protected Property(Property.Builder builder) {
            this.name = builder.name;
            this.prompt = builder.prompt;
            this.required = builder.required;
            this.readOnly = builder.readOnly;
            this.regex = builder.regex;
            this.value = builder.value;
        }

        public String getName() {
            return name;
        }

        public String getPrompt() {
            return prompt;
        }

        public Boolean getRequired() {
            return required;
        }

        public Boolean getReadOnly() {
            return readOnly;
        }

        public String getRegex() {
            return regex;
        }

        public Object getValue() {
            return value;
        }

        /**
         * Builder to help build {@link Property} instances.
         */
        public static class Builder {
            private final String name;
            private String prompt;
            private Boolean required;
            private Boolean readOnly;
            private String regex;
            private Object value;

            public Builder(String name) {
                this.name = name;
            }

            public Builder prompt(String prompt) {
                this.prompt = prompt;
                return this;
            }

            public Builder required(boolean required) {
                this.required = required;
                return this;
            }

            public Builder readOnly(boolean readOnly) {
                this.readOnly = readOnly;
                return this;
            }

            public Builder regex(String regex) {
                this.regex = regex;
                return this;
            }

            public Builder value(Object value) {
                this.value = value;
                return this;
            }

            public Property build() {
                return new Property(this);
            }
        }
    }
}

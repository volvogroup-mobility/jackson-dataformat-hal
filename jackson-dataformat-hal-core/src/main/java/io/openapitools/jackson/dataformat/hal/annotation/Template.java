package io.openapitools.jackson.dataformat.hal.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a {@link io.openapitools.jackson.dataformat.hal.HALTemplate} instance for inclusion in the _templates
 * section of a HAL-FORMS resource.
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Template {
    /**
     * Template relation name - if not set the property name will be used.
     *
     * @return relation name represented by the template.
     */
    String value() default "";
}

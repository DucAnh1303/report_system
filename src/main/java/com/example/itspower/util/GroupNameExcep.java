package com.example.itspower.util;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = GroupNameValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface GroupNameExcep {

    String message() default "groupName not is exits";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

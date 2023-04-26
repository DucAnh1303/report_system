
package com.example.itspower.util;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = IntegerValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Integer {
  String message() default "{number.invalid}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}

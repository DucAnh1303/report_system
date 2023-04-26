package com.example.itspower.util;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;


@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StartBeforeEndDateValidator.class)
@Documented
public @interface StartBeforeEndDate {
  String message() default "error.date.start-before-end-date";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  String startDate();

  String endDate();

  @Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
  @Retention(RetentionPolicy.RUNTIME)
  @Documented
  @interface List {
    StartBeforeEndDate[] value();
  }
}

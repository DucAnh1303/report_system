package com.example.itspower.util;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StartBeforeEndDateValidator implements ConstraintValidator<StartBeforeEndDate, Object> {
  private String startDate;
  private String endDate;
  private String message;
  @Override
  public void initialize(final StartBeforeEndDate constraintAnnotation) {
    startDate = constraintAnnotation.startDate();
    endDate = constraintAnnotation.endDate();
    message = constraintAnnotation.message();
  }

  @Override
  public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
    return false;
  }
}

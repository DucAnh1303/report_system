package com.example.itspower.util;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
public class IntegerValidator implements ConstraintValidator<Integer, String> {
  @Override
  public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
    if (s == null || s.isEmpty()) return true;
    try {
      Long.parseLong(s);
      return true;
    } catch (Exception e) {
      return false;
    }
  }
}

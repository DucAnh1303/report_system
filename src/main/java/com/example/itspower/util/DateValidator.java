package com.example.itspower.util;


import com.example.itspower.type.RegexType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class DateValidator implements ConstraintValidator<Date, String> {

    @Override
    public boolean isValid(String dateStr, ConstraintValidatorContext constraintValidatorContext) {
        if (dateStr == null || dateStr.isEmpty()) return true;

        var matcher = RegexType.DATE_PATTERN.matcher(dateStr);

        return matcher.matches();
    }
}

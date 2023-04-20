package com.example.itspower.util;

import com.example.itspower.repository.repositoryjpa.GroupJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class GroupNameValidator implements ConstraintValidator<GroupNameExcep, String> {
    @Autowired
    private GroupJpaRepository groupJpaRepository;

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (groupJpaRepository.findByGroupName(s).isPresent()) {
            return true;
        }
        return false;
    }
}

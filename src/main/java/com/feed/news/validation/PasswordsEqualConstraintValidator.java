package com.feed.news.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.feed.news.entity.db.XUser;

public class PasswordsEqualConstraintValidator implements
        ConstraintValidator<PasswordsEqualConstraint, Object> {

    @Override
    public void initialize(PasswordsEqualConstraint arg0) {
    }

    @Override
    public boolean isValid(Object candidate, ConstraintValidatorContext arg1) {
        XUser user = (XUser) candidate;
        return user.getPassword().equals(user.getConfirm_password());
    }
}

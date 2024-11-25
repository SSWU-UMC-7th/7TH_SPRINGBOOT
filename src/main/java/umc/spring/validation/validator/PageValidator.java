package umc.spring.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import umc.spring.validation.annotation.ValidatedPage;

public class PageValidator implements ConstraintValidator<ValidatedPage, Integer> {

    @Override
    public boolean isValid(Integer page, ConstraintValidatorContext context) {
        if (page == null || page < 1) {
            return false;
        }
        return true;
    }

    @Override
    public void initialize(ValidatedPage constraintAnnotation) {
        // 초기화 로직 필요 시 작성
    }
}


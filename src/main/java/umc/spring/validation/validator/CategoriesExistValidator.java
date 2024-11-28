package umc.spring.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import umc.spring.apiPayLoad.code.exception.handler.ErrorStatus;
import umc.spring.repository.FoodCategoryRepository.FoodCategoryRepository;
import umc.spring.validation.annotation.ExistCategories;

import java.lang.annotation.Annotation;
import java.util.List;

@Component
public class CategoriesExistValidator implements ConstraintValidator<ExistCategories, List<Long>> {

    private final FoodCategoryRepository foodCategoryRepository;

    public CategoriesExistValidator(FoodCategoryRepository foodCategoryRepository) {
        this.foodCategoryRepository = foodCategoryRepository;
    }

    @Override
    public boolean isValid(List<Long> values, ConstraintValidatorContext context) {
        System.out.println("검증 대상 값: " + values);

        // null 또는 빈 리스트는 유효한 값으로 처리
        if (values == null || values.isEmpty()) {
            return true; // 빈 리스트를 허용하지 않으려면 false로 변경
        }

        // ID가 모두 유효한지 확인
        return values.stream()
                .allMatch(id -> foodCategoryRepository.existsById(id));
    }
}
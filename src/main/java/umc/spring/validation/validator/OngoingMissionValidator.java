package umc.spring.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import umc.spring.repository.OngoingMissionRepository.OngoingMissionRepository;
import umc.spring.validation.annotation.OngoingMissionCheck;

@Component
@RequiredArgsConstructor
public class OngoingMissionValidator implements ConstraintValidator<OngoingMissionCheck, Long> {

    private final OngoingMissionRepository ongoingMissionRepository;

    @Override
    public boolean isValid(Long missionId, ConstraintValidatorContext context) {
        return missionId != null && !ongoingMissionRepository.existsByMissionId(missionId);
    }
}

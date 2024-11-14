package umc.spring.web.dto.mission;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import umc.spring.validation.annotation.OngoingMissionCheck;

@Getter
@Setter
public class MissionChallengeRequestDTO {

    @NotNull(message = "Mission ID는 필수입니다.")
    @OngoingMissionCheck  // 커스텀 애노테이션으로 도전 중 여부를 확인
    private Long missionId;

    @NotNull(message = "User ID는 필수입니다.")
    private Long userId;  // 사용자 ID는 하드코딩된 값으로 설정 예정
}


package umc.spring.web.dto.mission;

import lombok.Builder;
import lombok.Getter;
import umc.spring.domain.Mission;

import java.time.LocalDate;

@Getter
@Builder
public class MissionResponse {
    private Long id;              // 미션 ID
    private String missionSpec;   // 미션 설명
    private int reward;           // 보상
    private LocalDate deadline;   // 마감일
    private String status;        // 상태

    public static MissionResponse fromEntity(Mission mission) {
        return MissionResponse.builder()
                .id(mission.getId())
                .missionSpec(mission.getMissionSpec())
                .reward(mission.getReward())
                .deadline(mission.getDeadline())
                .status(mission.getStatus())
                .build();
    }
}

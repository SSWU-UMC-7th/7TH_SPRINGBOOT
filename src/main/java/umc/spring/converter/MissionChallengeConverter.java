package umc.spring.converter;

import org.springframework.stereotype.Component;
import umc.spring.domain.Mission;
import umc.spring.domain.OngoingMission;
import umc.spring.web.dto.mission.MissionChallengeRequestDTO;

@Component
public class MissionChallengeConverter {

    public OngoingMission toOngoingMission(MissionChallengeRequestDTO dto, Mission mission) {
        return OngoingMission.builder()
                .mission(mission)
                .userId(dto.getUserId())
                .status("IN_PROGRESS")  // 도전 중 상태로 설정
                .build();
    }
}

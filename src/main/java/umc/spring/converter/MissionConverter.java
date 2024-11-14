package umc.spring.converter;

import org.springframework.stereotype.Component;
import umc.spring.domain.Mission;
import umc.spring.domain.Store;
import umc.spring.web.dto.mission.MissionRequestDTO;

@Component
public class MissionConverter {

    public Mission toMission(MissionRequestDTO dto, Store store) {
        return Mission.builder()
                .store(store)
                .missionSpec(dto.getMissionSpec())
                .reward(dto.getReward())
                .deadline(dto.getDeadline())
                .build();
    }
}

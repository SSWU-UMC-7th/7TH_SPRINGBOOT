package umc.spring.service.MissionService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.spring.converter.MissionChallengeConverter;
import umc.spring.domain.Mission;
import umc.spring.domain.OngoingMission;
import umc.spring.repository.MissionRepository.MissionRepository;
import umc.spring.repository.OngoingMissionRepository.OngoingMissionRepository;
import umc.spring.web.dto.mission.MissionChallengeRequestDTO;

@Service
@RequiredArgsConstructor
public class MissionChallengeService {

    private final OngoingMissionRepository ongoingMissionRepository;
    private final MissionRepository missionRepository;
    private final MissionChallengeConverter missionChallengeConverter;

    @Transactional
    public void challengeMission(MissionChallengeRequestDTO dto) {
        Mission mission = missionRepository.findById(dto.getMissionId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 미션입니다."));

        OngoingMission ongoingMission = missionChallengeConverter.toOngoingMission(dto, mission);
        ongoingMissionRepository.save(ongoingMission);
    }
}

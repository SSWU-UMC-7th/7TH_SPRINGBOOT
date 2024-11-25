package umc.spring.service.MissionService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.spring.apiPayLoad.code.exception.InvalidMissionStatusException;
import umc.spring.apiPayLoad.code.exception.MissionAlreadyCompletedException;
import umc.spring.apiPayLoad.code.exception.MissionNotFoundException;
import umc.spring.converter.MissionConverter;
import umc.spring.domain.Mission;
import umc.spring.domain.Store;
import umc.spring.repository.MissionRepository.MissionRepository;
import umc.spring.repository.StoreRepository.StoreRepository;
import umc.spring.web.dto.mission.MissionRequestDTO;
import umc.spring.web.dto.mission.MissionResponse;
import umc.spring.web.dto.mission.MissionResponseDTO;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MissionService {

    private final MissionRepository missionRepository;
    private final StoreRepository storeRepository;
    private final MissionConverter missionConverter;

    @Transactional
    public void addMission(MissionRequestDTO dto) {
        Store store = storeRepository.findById(dto.getStoreId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 가게입니다."));

        Mission mission = missionConverter.toMission(dto, store);
        missionRepository.save(mission);
    }
    public Page<Mission> findMissionsByRegion(Long regionId, Pageable pageable) {
        return missionRepository.findMissionsByRegion(regionId, pageable);
    }

    public Page<MissionResponseDTO> getMissionsByStoreId(Long storeId, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size); // page는 1부터 시작한다고 가정
        Page<Mission> missions = missionRepository.findAllByStoreId(storeId, pageable);

        // Page<Mission> -> Page<MissionResponseDTO> 변환
        return missions.map(mission -> MissionResponseDTO.builder()
                .id(mission.getId())
                .missionSpec(mission.getMissionSpec())
                .status(mission.getStatus())
                .reward(mission.getReward())
                .deadline(mission.getDeadline())
                .build());
    }

    public Page<MissionResponse> getOngoingMissions(Pageable pageable) {
        return missionRepository.findOngoingMissions(pageable)
                .map(MissionResponse::fromEntity);
    }

    @Transactional
    public void completeMission(Long missionId) {
        // 미션 조회
        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(() -> new MissionNotFoundException("해당 미션을 찾을 수 없습니다."));

        // 이미 완료된 상태인지 확인
        if ("COMPLETED".equals(mission.getStatus())) {
            throw new MissionAlreadyCompletedException("이미 완료된 미션입니다.");
        }

        if (!"ONGOING".equalsIgnoreCase(mission.getStatus())) {
            throw new InvalidMissionStatusException("진행 중인 미션만 완료로 변경할 수 있습니다.");
        }
    }
}
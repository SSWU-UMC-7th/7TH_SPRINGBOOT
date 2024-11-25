package umc.spring.repository.MissionRepository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import umc.spring.domain.Mission;

public interface MissionRepositoryCustom {
    Page<Mission> findMissionsByRegion(Long regionId, Pageable pageable);
    Page<Mission> findOngoingMissions(Pageable pageable);
}
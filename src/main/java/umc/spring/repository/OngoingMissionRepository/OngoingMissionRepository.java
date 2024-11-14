package umc.spring.repository.OngoingMissionRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import umc.spring.domain.OngoingMission;

@Repository
public interface OngoingMissionRepository extends JpaRepository<OngoingMission, Long> {
    boolean existsByMissionId(Long missionId);  // 미션 ID로 도전 중 여부 확인
}


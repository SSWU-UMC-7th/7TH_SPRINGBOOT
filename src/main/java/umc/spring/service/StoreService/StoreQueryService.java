package umc.spring.service.StoreService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import umc.spring.domain.Store;
import umc.spring.domain.Mission;

import java.util.List;
import java.util.Optional;

public interface StoreQueryService {

    Optional<Store> findStore(Long id);
    List<Store> findStoresByNameAndScore(String name, Float score);
    Page<Mission> findMissionsByStatus(String status, Pageable pageable); // 상태별 미션 조회 메서드 추가
}

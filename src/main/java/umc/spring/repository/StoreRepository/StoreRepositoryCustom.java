package umc.spring.repository.StoreRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import umc.spring.domain.Store;
import umc.spring.domain.Mission;

import java.util.List;

public interface StoreRepositoryCustom {
    List<Store> dynamicQueryWithBooleanBuilder(String name, Float score);
    Page<Mission> findMissionsByStatus(String status, Pageable pageable);
}

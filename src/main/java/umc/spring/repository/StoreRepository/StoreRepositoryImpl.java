package umc.spring.repository.StoreRepository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import umc.spring.domain.Mission;
import umc.spring.domain.QMission;
import umc.spring.domain.QStore;
import umc.spring.domain.Store;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class StoreRepositoryImpl implements StoreRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
    private final QStore store = QStore.store;
    private final QMission mission = QMission.mission;

    @Override
    public List<Store> dynamicQueryWithBooleanBuilder(String name, Float score) {
        BooleanBuilder predicate = new BooleanBuilder();

        if (name != null) {
            predicate.and(store.name.eq(name));
        }

        if (score != null) {
            predicate.and(store.score.goe(score));
        }

        return jpaQueryFactory
                .selectFrom(store)
                .where(predicate)
                .fetch();
    }

    @Override
    public Page<Mission> findMissionsByStatus(String status, Pageable pageable) {
        List<Mission> missions = jpaQueryFactory
                .selectFrom(mission)
                .where(mission.status.eq(status))
                .orderBy(mission.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = jpaQueryFactory
                .selectFrom(mission)
                .where(mission.status.eq(status))
                .fetchCount();

        return new PageImpl<>(missions, pageable, total);
    }
}

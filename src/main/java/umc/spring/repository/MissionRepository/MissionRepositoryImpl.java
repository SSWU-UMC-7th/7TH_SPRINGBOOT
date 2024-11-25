package umc.spring.repository.MissionRepository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import umc.spring.domain.Mission;
import umc.spring.domain.QMission;

import java.util.List;

@Repository
public class MissionRepositoryImpl implements MissionRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Mission> findMissionsByRegion(Long regionId, Pageable pageable) {
        QMission mission = QMission.mission;

        // 지역 ID에 따른 필터링
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(mission.store.region.id.eq(regionId));

        // QueryDSL을 사용하여 미션 조회
        List<Mission> missions = queryFactory
                .selectFrom(mission)
                .where(builder)
                .orderBy(mission.deadline.asc())  // 마감일 오름차순으로 정렬 (필요에 따라 정렬 조건 변경 가능)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 전체 결과 개수
        long total = queryFactory
                .selectFrom(mission)
                .where(builder)
                .fetchCount();

        return new PageImpl<>(missions, pageable, total);
    }

    public MissionRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public Page<Mission> findOngoingMissions(Pageable pageable) {
        QMission mission = QMission.mission;

        List<Mission> missions = queryFactory
                .selectFrom(mission)
                .where(mission.status.eq("ONGOING"))
                .orderBy(mission.deadline.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(mission.count())
                .from(mission)
                .where(mission.status.eq("ONGOING"))
                .fetchOne();

        return new PageImpl<>(missions, pageable, total);
    }
}
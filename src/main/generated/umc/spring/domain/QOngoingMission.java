package umc.spring.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOngoingMission is a Querydsl query type for OngoingMission
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOngoingMission extends EntityPathBase<OngoingMission> {

    private static final long serialVersionUID = 736404797L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOngoingMission ongoingMission = new QOngoingMission("ongoingMission");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMission mission;

    public final StringPath status = createString("status");

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QOngoingMission(String variable) {
        this(OngoingMission.class, forVariable(variable), INITS);
    }

    public QOngoingMission(Path<? extends OngoingMission> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOngoingMission(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOngoingMission(PathMetadata metadata, PathInits inits) {
        this(OngoingMission.class, metadata, inits);
    }

    public QOngoingMission(Class<? extends OngoingMission> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.mission = inits.isInitialized("mission") ? new QMission(forProperty("mission"), inits.get("mission")) : null;
    }

}


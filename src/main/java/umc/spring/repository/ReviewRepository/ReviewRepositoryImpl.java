package umc.spring.repository.ReviewRepository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import umc.spring.domain.QReview;
import umc.spring.domain.Review;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Review> findReviewsByStoreId(Long storeId, Pageable pageable) {
        QReview review = QReview.review;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(review.store.id.eq(storeId));

        List<Review> reviews = queryFactory
                .selectFrom(review)
                .where(builder)
                .orderBy(review.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .selectFrom(review)
                .where(builder)
                .fetchCount();

        return new PageImpl<>(reviews, pageable, total);
    }
}
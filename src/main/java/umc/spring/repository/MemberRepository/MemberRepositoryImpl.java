package umc.spring.repository.MemberRepository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import umc.spring.domain.Member;
import umc.spring.domain.QMember;
import umc.spring.domain.QReview;
import umc.spring.domain.Review;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Member findMemberDetailsById(Long memberId) {
        QMember member = QMember.member;

        // Member 상세 정보 조회 쿼리
        return queryFactory
                .selectFrom(member)
                .where(member.id.eq(memberId))
                .fetchOne();
    }

    @Override
    public Page<Review> findReviewsByMemberId(Long memberId, Pageable pageable) {
        QReview review = QReview.review;

        // 사용자가 작성한 리뷰 목록 조회 쿼리
        List<Review> reviews = queryFactory
                .selectFrom(review)
                .where(review.member.id.eq(memberId))
                .orderBy(review.createdAt.desc()) // 작성일 내림차순 정렬
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 리뷰 총 개수 조회 (페이지네이션을 위해 필요)
        long total = queryFactory
                .selectFrom(review)
                .where(review.member.id.eq(memberId))
                .fetchCount();

        return new PageImpl<>(reviews, pageable, total);
    }
}
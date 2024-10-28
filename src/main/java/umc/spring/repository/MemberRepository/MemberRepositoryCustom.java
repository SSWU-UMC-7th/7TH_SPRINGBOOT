package umc.spring.repository.MemberRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import umc.spring.domain.Member;
import umc.spring.domain.Review;

public interface MemberRepositoryCustom {
    Member findMemberDetailsById(Long memberId);
    Page<Review> findReviewsByMemberId(Long memberId, Pageable pageable);
}
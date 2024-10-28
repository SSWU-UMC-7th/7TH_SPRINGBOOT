package umc.spring.service.MemberService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.spring.domain.Member;
import umc.spring.domain.Review;
import umc.spring.repository.MemberRepository.MemberRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    public Member getMemberDetails(Long memberId) {
        return memberRepository.findMemberDetailsById(memberId);
    }

    public Page<Review> getReviewsByMemberId(Long memberId, Pageable pageable) {
        return memberRepository.findReviewsByMemberId(memberId, pageable);
    }
}
package umc.spring.service.ReviewService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.spring.domain.Member;
import umc.spring.domain.Review;

import umc.spring.domain.Store;
import umc.spring.repository.MemberRepository.MemberRepository;
import umc.spring.repository.ReviewRepository.ReviewRepository;
import umc.spring.repository.StoreRepository.StoreRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final StoreRepository storeRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Review createReview(Long storeId, Long memberId, String content, Float score) {
        Store store = storeRepository.findById(storeId).orElseThrow(() -> new IllegalArgumentException("Invalid store ID"));
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("Invalid member ID"));

        Review review = Review.builder()
                .store(store)
                .member(member)
                .content(content)
                .score(score)
                .build();

        return reviewRepository.save(review);
    }

    public Page<Review> getReviewsByStoreId(Long storeId, Pageable pageable) {
        return reviewRepository.findByStoreId(storeId, pageable);
    }
}


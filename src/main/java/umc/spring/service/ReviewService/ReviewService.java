package umc.spring.service.ReviewService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.spring.converter.ReviewConverter;
import umc.spring.domain.Member;
import umc.spring.domain.Review;

import umc.spring.domain.Store;
import umc.spring.repository.MemberRepository.MemberRepository;
import umc.spring.repository.ReviewRepository.ReviewRepository;
import umc.spring.repository.StoreRepository.StoreRepository;
import umc.spring.web.dto.review.ReviewRequestDTO;
import umc.spring.web.dto.review.ReviewResponseDTO;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final StoreRepository storeRepository;
    private final MemberRepository memberRepository;
    private final ReviewConverter reviewConverter;

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

    @Transactional(readOnly = false)
    public void addReview(ReviewRequestDTO dto) {
        // Store 존재 여부는 커스텀 애노테이션에서 검증
        Review review = reviewConverter.toReview(dto);
        reviewRepository.save(review);
    }

    public Page<ReviewResponseDTO> getMyReviews(Integer page) {
        Long hardcodedUserId = 1L; // 하드코딩된 userId

        Pageable pageable = PageRequest.of(page - 1, 10); // Page 번호는 0부터 시작
        Page<Review> reviews = reviewRepository.findAllByUserId(hardcodedUserId, pageable);

        // Page<Review> -> Page<ReviewResponseDTO> 변환
        return reviews.map(review -> ReviewResponseDTO.builder()
                .nickname("Hardcoded User") // 예제 닉네임
                .score(review.getScore())
                .body(review.getBody())
                .createdAt(review.getCreatedAt().toLocalDate())
                .build());
    }

}


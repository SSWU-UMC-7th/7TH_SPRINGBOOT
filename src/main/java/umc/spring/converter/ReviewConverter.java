package umc.spring.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import umc.spring.domain.Review;
import umc.spring.domain.Store;
import umc.spring.repository.StoreRepository.StoreRepository;
import umc.spring.web.dto.ReviewRequestDTO;

@Component
@RequiredArgsConstructor
public class ReviewConverter {

    private final StoreRepository storeRepository;

    public Review toReview(ReviewRequestDTO dto) {
        Store store = storeRepository.findById(dto.getStoreId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 가게입니다."));

        return Review.builder()
                .store(store)  // Store 객체 설정
                .content(dto.getContent())
                .score(dto.getScore())
                .id(dto.getUserId())
                .build();
    }
}


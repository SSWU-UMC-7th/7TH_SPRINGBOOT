package umc.spring.repository.ReviewRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import umc.spring.domain.Review;
import umc.spring.domain.Store;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewRepositoryCustom {
    Page<Review> findByStoreId(Long storeId, Pageable pageable);
    Page<Review> findAllByStore(Store store, PageRequest pageRequest);
    Page<Review> findAllByUserId(Long userId, Pageable pageable);
}
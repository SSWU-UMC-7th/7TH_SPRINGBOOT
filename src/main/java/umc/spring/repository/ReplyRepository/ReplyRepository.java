package umc.spring.repository.ReplyRepository;


import org.springframework.data.jpa.repository.JpaRepository;
import umc.spring.domain.Reply;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findByReviewId(Long reviewId);
}
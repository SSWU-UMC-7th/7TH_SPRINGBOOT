package umc.spring.web.dto.review;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class ReviewResponseDTO {
    private String nickname;
    private Float score;
    private String body;
    private LocalDate createdAt;
}

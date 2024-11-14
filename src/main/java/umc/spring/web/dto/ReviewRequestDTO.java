package umc.spring.web.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import umc.spring.validation.annotation.ExistingStore;

@Getter
@Setter
public class ReviewRequestDTO {
    @ExistingStore
    private Long storeId;

    @NotBlank(message = "리뷰 내용이 필요합니다.")
    private String content;

    @NotNull(message = "리뷰 점수가 필요합니다.")
    @Min(value = 1, message = "점수는 1 이상이어야 합니다.")
    @Max(value = 5, message = "점수는 5 이하이어야 합니다.")
    private Float score;  // Integer 대신 Float 사용

    @NotNull(message = "User ID는 필수입니다.")
    private Long userId;
}


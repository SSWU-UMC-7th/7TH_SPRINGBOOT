package umc.spring.web.dto.mission;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MissionRequestDTO {

    @NotNull(message = "Store ID는 필수입니다.")
    private Long storeId;

    @NotBlank(message = "미션 설명이 필요합니다.")
    private String missionSpec;

    @NotNull(message = "미션 보상이 필요합니다.")
    private Integer reward;

    @NotNull(message = "미션 마감 기한이 필요합니다.")
    private LocalDate deadline;
}

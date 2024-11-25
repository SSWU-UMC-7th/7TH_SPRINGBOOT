package umc.spring.web.dto.mission;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class MissionResponseDTO {
    private Long id;
    private String missionSpec;
    private String status;
    private Integer reward;
    private LocalDate deadline;
}
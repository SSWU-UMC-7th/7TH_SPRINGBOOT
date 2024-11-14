package umc.spring.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreRequestDTO {
    @NotBlank(message = "Store name is required.")
    private String name;

    @NotNull(message = "Region ID is required.")
    private Long regionId;

    @NotNull(message = "User ID is required.")
    private Long userId;
}
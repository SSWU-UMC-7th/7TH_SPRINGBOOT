package umc.spring.web.dto.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import umc.spring.domain.Role;
import umc.spring.validation.annotation.ExistCategories;

import java.util.ArrayList;
import java.util.List;

public class MemberRequestDTO {

    @Getter
    public static class JoinDto{
        @NotBlank
        String name;
        @Email
        String email;    // 이메일 필드 추가
        @NotBlank
        String password;    // 비밀번호 필드 추가
        @NotNull(message = "성별은 필수 값입니다.")
        Integer gender;
        @NotNull
        Integer birthYear;
        @NotNull
        Integer birthMonth;
        @NotNull
        Integer birthDay;
        @Size(min = 5, max = 12)
        String address;
        @Size(min = 5, max = 12)
        String specAddress;
        @NotNull
        @ExistCategories
        private List<Long> preferCategory = new ArrayList<>();
        @NotNull
        Role role;
    }
}
package sparta.jeogiyo.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserSignInRequestDto {

    @NotBlank(message = "아이디는 필수 항목입니다.")
    @Size(min = 4, max = 10, message = "아이디는 최소 4자, 최대 10자 이어야 합니다.")
    @Pattern(regexp = "^[a-z0-9]+$", message = "아이디는 소문자와 숫자 조합만 가능합니다.")
    private String username;

    @NotBlank(message = "비밀번호는 필수 항목입니다.")
    @Size(min = 8, max = 15, message = "비밀번호는 최소 8자, 최대 15자 이어야 합니다.")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$",
            message = "비밀번호는 하나 이상의 대문자, 소문자, 숫자, 특수 문자를 포함해야 합니다."
    )
    private String password;

}

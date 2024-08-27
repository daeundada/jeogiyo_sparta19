package sparta.jeogiyo.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserUpdateRequestDto {

    @NotBlank(message = "닉네임은 필수 항목입니다.")
    @Pattern(regexp = "^[a-zA-Z0-9가-힣]{2,8}$", message = "닉네임은 특수문자를 제외한 2글자에서 8글자 사이여야 합니다.")
    private String nickname;

    @NotBlank(message = "비밀번호는 필수 항목입니다.")
    @Size(min = 8, max = 15, message = "비밀번호는 최소 8자, 최대 15자 이어야 합니다.")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$",
            message = "비밀번호는 하나 이상의 대문자, 소문자, 숫자, 특수 문자를 포함해야 합니다."
    )
    private String currentPassword;

    @Size(min = 8, max = 15, message = "비밀번호는 최소 8자, 최대 15자 이어야 합니다.")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$",
            message = "비밀번호는 하나 이상의 대문자, 소문자, 숫자, 특수 문자를 포함해야 합니다."
    )
    private String updatePassword;

    @NotBlank(message = "주소는 필수 항목입니다.")
    private String address;

    public void setUpdatePassword(String password) {
        this.updatePassword = password;
    }

}

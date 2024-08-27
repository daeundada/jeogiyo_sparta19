package sparta.jeogiyo.domain.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Getter;
import sparta.jeogiyo.domain.user.entity.User;
import sparta.jeogiyo.domain.user.entity.UserRoleEnum;

@Getter
public class UserSignUpRequestDto {

    @NotBlank(message = "아이디는 필수 항목입니다.")
    @Size(min = 4, max = 10, message = "아이디는 최소 4자, 최대 10자 이어야 합니다.")
    @Pattern(regexp = "^[a-z0-9]+$", message = "아이디는 소문자와 숫자 조합만 가능합니다.")
    private String username;

    @Email(message = "올바르지 않은 이메일 형식입니다.")
    private String email;

    @NotBlank(message = "닉네임은 필수 항목입니다.")
    @Pattern(regexp = "^[a-zA-Z0-9가-힣]{2,8}$", message = "닉네임은 특수문자를 제외한 2글자에서 8글자 사이여야 합니다.")
    private String nickname;

    @NotBlank(message = "비밀번호는 필수 항목입니다.")
    @Size(min = 8, max = 15, message = "비밀번호는 최소 8자, 최대 15자 이어야 합니다.")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$",
            message = "비밀번호는 하나 이상의 대문자, 소문자, 숫자, 특수 문자를 포함해야 합니다."
    )
    private String password;

    @NotBlank(message = "주소는 필수 항목입니다.")
    private String address;

    private List<UserRoleEnum> roles = List.of(UserRoleEnum.CUSTOMER);

    private Boolean isPublic = true;

    public User toUser(String encodedPassword) {
        return User.builder()
                .username(username)
                .nickname(nickname)
                .password(encodedPassword)
                .email(email)
                .address(address)
                .roles(roles)
                .isPublic(isPublic)
                .build();
    }

}

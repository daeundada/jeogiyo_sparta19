package sparta.jeogiyo.domain.user.dto.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import sparta.jeogiyo.domain.user.entity.User;
import sparta.jeogiyo.domain.user.entity.UserRoleEnum;

@Getter
@Builder
public class UserResponseDto {

    private String username;

    private String nickname;

    private String email;

    private String address;

    private List<UserRoleEnum> roles;

    public static UserResponseDto fromEntity(User user) {
        return UserResponseDto.builder()
                .username(user.getUsername())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .address(user.getAddress())
                .roles(user.getRoles())
                .build();
    }

}

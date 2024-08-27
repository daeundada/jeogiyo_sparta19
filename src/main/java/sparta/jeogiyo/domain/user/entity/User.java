package sparta.jeogiyo.domain.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import sparta.jeogiyo.domain.user.dto.request.UserUpdateRequestDto;
import sparta.jeogiyo.global.entity.BaseTimeEntity;

@Entity
@Table(name = "p_user")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long user_id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String address;

    @Column(name = "is_public")
    private boolean isPublic;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private List<UserRoleEnum> roles;

    public void update(UserUpdateRequestDto updateRequestDto) {
        this.nickname = updateRequestDto.getNickname();
        this.password = updateRequestDto.getUpdatePassword();
        this.address = updateRequestDto.getAddress();
    }

    public void delete() {
        this.isPublic = false;
        this.isDeleted = true;
        this.setDeletedAt(LocalDateTime.now());
        this.setDeletedBy(this.username);
    }

}
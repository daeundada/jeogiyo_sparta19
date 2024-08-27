package sparta.jeogiyo.global.entity;

import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import sparta.jeogiyo.domain.user.UserDetailsImpl;

/**
 * BaseTimeEntity 클래스에서  @CreatedBy와  @LastModifiedBy를
 * 자동으로 감지하기 위해 사용되는 클래스입니다.
 */
@Component
public class CustomAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetailsImpl userDetails) {
            return Optional.of(userDetails.getUsername());
        }
        return Optional.empty();
    }
}

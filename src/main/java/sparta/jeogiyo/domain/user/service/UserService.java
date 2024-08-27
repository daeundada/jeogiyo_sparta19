package sparta.jeogiyo.domain.user.service;

import java.time.LocalDateTime;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparta.jeogiyo.domain.user.UserDetailsImpl;
import sparta.jeogiyo.domain.user.dto.request.UserDeleteRequestDto;
import sparta.jeogiyo.domain.user.dto.request.UserUpdateRequestDto;
import sparta.jeogiyo.domain.user.dto.response.UserResponseDto;
import sparta.jeogiyo.domain.user.entity.User;
import sparta.jeogiyo.domain.user.repository.UserRepository;
import sparta.jeogiyo.global.response.CustomException;
import sparta.jeogiyo.global.response.ErrorCode;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public UserResponseDto getUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(
                ErrorCode.USER_NOT_FOUND));
        if (user.isDeleted() || !user.isPublic()) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }
        return UserResponseDto.fromEntity(user);
    }

    @Transactional(readOnly = true)
    public Page<User> getAllUsers(int page, int size, String sortBy, boolean isAsc) {
        if (size != 10 && size != 30 && size != 50) {
            size = 10;
        }

        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);

        Pageable pageable = PageRequest.of(page, size, sort);

        return userRepository.findAll(pageable);
    }

    @Transactional
    public UserResponseDto updateUser(Long updateUserId, UserUpdateRequestDto updateRequestDto) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        User user = userDetails.getUser();

        validateUserRequestDto(user, updateUserId, updateRequestDto.getCurrentPassword());

        if (updateRequestDto.getUpdatePassword() == null || updateRequestDto.getUpdatePassword()
                .isBlank()) {
            String encodedUpdatePassword = passwordEncoder.encode(
                    updateRequestDto.getUpdatePassword());
            updateRequestDto.setPassword(encodedUpdatePassword);
        } else {
            updateRequestDto.setPassword(user.getPassword());
        }

        user.update(updateRequestDto);
        userRepository.save(user);
        return UserResponseDto.fromEntity(user);
    }

    @Transactional
    public void deleteUser(Long deleteUserId, UserDeleteRequestDto deleteRequestDto) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        User user = userDetails.getUser();

        validateUserRequestDto(user, deleteUserId, deleteRequestDto.getPassword());

        user.delete();
        user.setDeletedAt(LocalDateTime.now());
        user.setDeletedBy(user.getUsername());
        userRepository.save(user);
    }

    public void validateUserRequestDto(User user, Long requestId, String requestUserPassword) {
        // 이미 soft 삭제된 User인 경우
        if (user.isDeleted()) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        // 토큰으로 인증된 User Id와 요청하는 User의 Id와 다른 경우
        if (!Objects.equals(requestId, user.getUser_id())) {
            throw new CustomException(ErrorCode.USER_UNAUTHORIZED);
        }

        // User의 비밀번호가 요청하는 User의 비밀번호와 다른 경우
        if (!passwordEncoder.matches(requestUserPassword, user.getPassword())) {
            throw new CustomException(ErrorCode.PASSWORD_NOT_MATCH);
        }
    }
}
package sparta.jeogiyo.domain.user.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparta.jeogiyo.domain.user.dto.request.UserSignUpRequestDto;
import sparta.jeogiyo.domain.user.dto.response.UserResponseDto;
import sparta.jeogiyo.domain.user.entity.User;
import sparta.jeogiyo.domain.user.repository.UserRepository;
import sparta.jeogiyo.global.response.CustomException;
import sparta.jeogiyo.global.response.ErrorCode;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    @Transactional
    public UserResponseDto signUp(UserSignUpRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());

        validateUserSignUp(requestDto);

        User user = requestDto.toUser(password);
        user.setCreatedBy(username);
        userRepository.save(user);

        log.info("회원가입 성공: username={}, email={}", username, requestDto.getEmail());
        return UserResponseDto.fromEntity(user);
    }

    private final PasswordEncoder passwordEncoder;

    private void validateUserSignUp(UserSignUpRequestDto requestDto) {
        Optional<User> checkUsername = userRepository.findByUsername(requestDto.getUsername());
        if (checkUsername.isPresent()) {
            log.warn("회원가입 실패 - 중복된 아이디: {}", requestDto.getUsername());
            throw new CustomException(ErrorCode.DUPLICATE_USERNAME);
        }

        String email = requestDto.getEmail();
        Optional<User> checkEmail = userRepository.findByEmail(email);
        if (checkEmail.isPresent()) {
            log.warn("회원가입 실패 - 중복된 이메일: {}", email);
            throw new CustomException(ErrorCode.DUPLICATE_EMAIL);
        }

        String nickname = requestDto.getNickname();
        Optional<User> checkNickname = userRepository.findByNickname(nickname);
        if (checkNickname.isPresent()) {
            log.warn("회원가입 실패 - 중복된 닉네임: {}", nickname);
            throw new CustomException(ErrorCode.DUPLICATE_NICKNAME);
        }
    }

}

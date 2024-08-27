package sparta.jeogiyo.domain.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sparta.jeogiyo.domain.user.dto.request.UserDeleteRequestDto;
import sparta.jeogiyo.domain.user.dto.request.UserSignUpRequestDto;
import sparta.jeogiyo.domain.user.dto.request.UserUpdateRequestDto;
import sparta.jeogiyo.domain.user.dto.response.UserResponseDto;
import sparta.jeogiyo.domain.user.entity.User;
import sparta.jeogiyo.domain.user.service.AuthService;
import sparta.jeogiyo.domain.user.service.UserService;
import sparta.jeogiyo.global.response.ApiResponse;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final AuthService authService;

    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<ApiResponse<Object>> signUp(
            @RequestBody @Valid UserSignUpRequestDto requestDto) {
        UserResponseDto userResponseDto = authService.signUp(requestDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.of("회원가입을 성공하였습니다.", userResponseDto));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse<Object>> updateUser(@PathVariable Long userId,
            @Valid @RequestBody UserUpdateRequestDto updateRequest) {
        UserResponseDto userResponseDto = userService.updateUser(userId, updateRequest);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.of("수정되었습니다.", userResponseDto));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long userId) {
        UserResponseDto user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Object>> getAllUsers(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("isAsc") boolean isAsc
    ) {
        Page<User> pageUser = userService.getAllUsers(page, size, sortBy, isAsc);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.of("회원 전체 목록", pageUser));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId,
            @Valid @RequestBody UserDeleteRequestDto deleteRequestDto) {
        userService.deleteUser(userId, deleteRequestDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
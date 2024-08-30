package sparta.jeogiyo.domain.chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sparta.jeogiyo.domain.chat.dto.request.ChatRequestDTO;
import sparta.jeogiyo.domain.chat.dto.response.ChatResponseDTO;
import sparta.jeogiyo.domain.chat.service.ChatService;
import sparta.jeogiyo.domain.user.UserDetailsImpl;
import sparta.jeogiyo.global.response.ApiResponse;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/api/chats")
    public ResponseEntity<ApiResponse<ChatResponseDTO>> CreateChat(
            @RequestBody ChatRequestDTO requestDTO,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        ChatResponseDTO responseDTO = chatService.CreateChat(requestDTO, userDetails);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.of("채팅 성공", responseDTO));

    }

}

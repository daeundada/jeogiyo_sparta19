package sparta.jeogiyo.domain.chat.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import sparta.jeogiyo.domain.chat.dto.request.ChatRequestDTO;
import sparta.jeogiyo.domain.chat.dto.response.ChatResponseDTO;
import sparta.jeogiyo.domain.chat.entity.Chat;
import sparta.jeogiyo.domain.chat.repository.ChatRepository;
import sparta.jeogiyo.domain.user.UserDetailsImpl;

@Slf4j
@Service
public class ChatService {

    private final ChatRepository chatRepository;
    private final WebClient webClient;
    @Value("${api.key}")
    private String apiKey;


    @Autowired
    public ChatService(ChatRepository chatRepository, Builder webClientBuilder) {
        this.chatRepository = chatRepository;
        this.webClient = webClientBuilder.baseUrl(
                        "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent")
                .build();
    }

    @Transactional
    public ChatResponseDTO createChat(ChatRequestDTO chatRequestDTO, UserDetailsImpl userDetails) {


        String apiResponse = getAnswer(chatRequestDTO);

        ChatResponseDTO apiAnswer = parseResponse(apiResponse);

        Chat chat = new Chat();

        chat.setUser(userDetails.getUser());
        chat.setAnswer(apiAnswer.getAnswer());
        chat.setQuestion(chatRequestDTO.getContents().getParts().getText());

        chatRepository.save(chat);

        return apiAnswer;
    }

    public String getAnswer(ChatRequestDTO chatRequestDTO) {

        String requestBody = String.format("{\"contents\":[{\"parts\":[{\"text\":\"%s 100자 안으로 대답해줘\"}]}]}",
                chatRequestDTO.getContents().getParts().getText());

        try {
            Mono<String> response = webClient.post().uri(
                            uriBuilder -> uriBuilder.queryParam("key", apiKey).build())
                    .header("Content-Type", "application/json")
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(String.class);

            return response.block();

        } catch (WebClientResponseException e) {

            log.error("응답 오류, 상태 코드: {}, 응답 본문: {}", e.getStatusCode(), e.getResponseBodyAsString());
            throw new RuntimeException("서버로부터 답변을 가져오는 데 실패했습니다: " + e.getMessage(), e);

        } catch (Exception e) {

            log.error("서버로 답변 요청 중 예상치 못한 오류가 발생했습니다.", e);
            throw new RuntimeException("예상치 못한 오류가 발생했습니다: " + e.getMessage(), e);

        }
    }

    private ChatResponseDTO parseResponse(String jsonResponse) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonResponse);

            JsonNode textNode = rootNode.path("candidates").get(0)
                    .path("content").path("parts").get(0).path("text");

            return new ChatResponseDTO(textNode.asText());

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}


package sparta.jeogiyo.domain.chat.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
public class ChatResponseDTO {

    @Setter
    private String answer;

    public ChatResponseDTO(String answer) {
        this.answer = answer;
    }
}
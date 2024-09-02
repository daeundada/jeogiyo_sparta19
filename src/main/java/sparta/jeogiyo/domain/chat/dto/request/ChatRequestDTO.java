package sparta.jeogiyo.domain.chat.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

public class ChatRequestDTO {

    @Getter
    private Contents contents;

    public static class Contents {

        @Getter
        private Parts parts;

        public static class Parts {

            @Getter
            @NotBlank
            @Size(max = 100)
            private String text;
        }
    }
}
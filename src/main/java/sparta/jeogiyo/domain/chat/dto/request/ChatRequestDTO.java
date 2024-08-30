package sparta.jeogiyo.domain.chat.dto.request;

import lombok.Getter;

public class ChatRequestDTO {

    @Getter
    private Contents contents;

    public static class Contents {

        @Getter
        private Parts parts;

        public static class Parts {

            @Getter
            private String text;
        }
    }
}
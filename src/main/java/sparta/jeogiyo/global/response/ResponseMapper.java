package sparta.jeogiyo.global.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResponseMapper {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void writeErrorCodeResponse(HttpServletResponse response, ErrorCode errorCode)
            throws IOException {
        response.setStatus(errorCode.getStatus());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String jsonResponse = objectMapper.writeValueAsString(
                ApiResponse.of(errorCode.getMessage(), null));

        response.getWriter().write(jsonResponse);
    }

}

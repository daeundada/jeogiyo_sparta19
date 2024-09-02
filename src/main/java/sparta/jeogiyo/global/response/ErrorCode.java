package sparta.jeogiyo.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    //공통
    NOT_FOUND(404, "Resource not found."),
    BAD_REQUEST(400, "Bad request."),
    UNAUTHORIZED(401, "Unauthorized access."),
    FORBIDDEN(403, "Forbidden."),
    INTERNAL_SERVER_ERROR(500, "Internal server error."),
    INVALID_REQUEST_BODY(400, "잘못된 요청 입니다."),

    // User 관련
    DUPLICATE_EMAIL(409, "중복된 이메일입니다."),
    DUPLICATE_USERNAME(409, "중복된 아이디입니다."),
    DUPLICATE_NICKNAME(409, "중복된 닉네임입니다."),
    USER_NOT_FOUND(404, "존재하지 않는 사용자입니다."),
    INVALID_USERNAME_PASSWORD(401, "아이디 또는 비밀번호가 틀렸습니다."),
    PASSWORD_NOT_MATCH(401, "비밀번호가 틀렸습니다."),
    USER_UNAUTHORIZED(401, "권한이 없는 사용자입니다."),

    // Store 관련
    STORE_ID_NOT_FOUND(404, "존재하지 않는 가게 ID 입니다."),
    DUPLICATE_STORE_NUMBER(409, "중복된 가게 번호입니다."),
    DUPLICATE_STORE_NAME(409, "중복된 가게 이름입니다."),

    // Product 관련
    PRODUCT_NOT_FOUND(404, "존재하지 않는 상품입니다."),
    PRODUCT_ID_NOT_FOUND(404, "존재하지 않는 상품입니다."),

    // Cart 관련
    CART_IS_EMPTY(404, "장바구니가 비어있습니다."),
    CART_PRODUCT_ALREADY_EXIST(409, "이미 존재하는 상품입니다."),

    //Order 관련
    ORDER_NOT_FOUND(404, "존재하지 않는 주문입니다."),

    // JWT 관련
    INVALID_JWT_TOKEN(401, "유효하지 않은 토큰입니다."),
    UNSUPPORTED_JWT_TOKEN(401, "지원되지 않는 JWT 토큰입니다.");

    private final int status;
    private final String message;
}

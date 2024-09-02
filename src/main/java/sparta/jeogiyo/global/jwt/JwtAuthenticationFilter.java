package sparta.jeogiyo.global.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import sparta.jeogiyo.domain.user.UserDetailsImpl;
import sparta.jeogiyo.domain.user.dto.request.UserSignInRequestDto;
import sparta.jeogiyo.domain.user.dto.response.UserResponseDto;
import sparta.jeogiyo.domain.user.entity.User;
import sparta.jeogiyo.global.response.ApiResponse;
import sparta.jeogiyo.global.response.ErrorCode;
import sparta.jeogiyo.global.response.ResponseMapper;

@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtUtil jwtUtil;

    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        setFilterProcessesUrl("/api/users/sign-in");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
            HttpServletResponse response) throws AuthenticationException {

        UserSignInRequestDto requestDto = null;
        try {
            requestDto = new ObjectMapper().readValue(request.getInputStream(),
                    UserSignInRequestDto.class);
        } catch (IOException e) {
            throw new UsernameNotFoundException(ErrorCode.INVALID_REQUEST_BODY.getMessage());
        }

        log.info("사용자 로그인 시도: 아이디 {}", requestDto.getUsername());

        UserDetails userDetails = userDetailsService.loadUserByUsername(requestDto.getUsername());
        return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(
                        userDetails,
                        requestDto.getPassword(),
                        userDetails.getAuthorities()
                )
        );
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
            HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
        User user = ((UserDetailsImpl) authResult.getPrincipal()).getUser();
        String token = jwtUtil.createToken(user.getUsername(), user.getRoles());
        response.addHeader("Authorization", token);

        String jsonResponse = new ObjectMapper().writeValueAsString(
                ApiResponse.of("로그인에 성공하였습니다.", UserResponseDto.fromEntity(user)));
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse);

//        jwtUtil.addJwtToCookie(token, response);

        log.info("사용자 로그인 성공: 아이디 {}, 권한 {}", user.getUsername(), user.getRoles());
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
            HttpServletResponse response, AuthenticationException failed)
            throws IOException, ServletException {
        log.warn("사용자 로그인 실패: {}", failed.getMessage());
        if (failed.getMessage().equals(ErrorCode.INVALID_REQUEST_BODY.getMessage())) {
            ResponseMapper.writeErrorCodeResponse(response, ErrorCode.INVALID_REQUEST_BODY);
        } else {
            ResponseMapper.writeErrorCodeResponse(response, ErrorCode.INVALID_USERNAME_PASSWORD);
        }
    }
}
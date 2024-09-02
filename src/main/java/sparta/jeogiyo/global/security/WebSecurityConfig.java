package sparta.jeogiyo.global.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import sparta.jeogiyo.domain.user.entity.UserRoleEnum;
import sparta.jeogiyo.global.jwt.JwtAuthenticationFilter;
import sparta.jeogiyo.global.jwt.JwtAuthorizationFilter;
import sparta.jeogiyo.global.jwt.JwtUtil;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtUtil jwtUtil;

    private final UserDetailsService userDetailsService;

    private final AuthenticationConfiguration authenticationConfiguration;

    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
            throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        final String MASTER = UserRoleEnum.MASTER.getRoleName();
        final String CUSTOMER = UserRoleEnum.CUSTOMER.getRoleName();
        final String OWNER = UserRoleEnum.OWNER.getRoleName();

        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize

                        // 유저 관련
                        // 로그인, 회원가입
                        .requestMatchers(HttpMethod.POST, "/api/users/sign-up",
                                "/api/users/sign-in").permitAll()

                        // 유저 전체 조회
                        .requestMatchers(HttpMethod.GET, "/api/users").hasAuthority(MASTER)

                        // 유저 정보 수정
                        .requestMatchers(HttpMethod.PUT, "/api/users/**").permitAll()

                        // 유저 삭제
                        .requestMatchers(HttpMethod.DELETE, "/api/users/**").permitAll()

                        // 유저 단일 조회
                        .requestMatchers(HttpMethod.GET, "/api/users/**").permitAll()

                        // 가게 관련
                        // 가게 등록
                        .requestMatchers(HttpMethod.POST, "/api/stores").hasAnyAuthority(OWNER, MASTER)

                        // 가게 수정
                        .requestMatchers(HttpMethod.PATCH, "/api/stores/**")
                        .hasAnyAuthority(OWNER, MASTER)

                        // 가게 삭제
                        .requestMatchers(HttpMethod.DELETE, "/api/stores/**")
                        .hasAnyAuthority(OWNER, MASTER)

                        // 가게 단일 조회
                        .requestMatchers(HttpMethod.GET, "/api/stores/**").permitAll()

                        // 가게 전체 조회
                        .requestMatchers(HttpMethod.GET, "/api/stores").permitAll()

                        // 주문 관련
                        // 주문 등록
                        .requestMatchers(HttpMethod.POST, "/api/orders/cart/**")
                        .hasAnyAuthority(CUSTOMER, MASTER)

                        // 주문 수정
                        .requestMatchers(HttpMethod.PATCH, "/api/stores/**")
                        .hasAnyAuthority(OWNER, MASTER)

                        // 주문 삭제
                        .requestMatchers(HttpMethod.DELETE, "/api/orders/**").permitAll()

                        // 주문 상세 조회
                        .requestMatchers(HttpMethod.GET, "/api/orders/**").permitAll()

                        // 결제 관련
                        // 결제 요청
                        .requestMatchers(HttpMethod.POST, "/api/payments/order/**").permitAll()

                        // 결제(주문) 취소
                        .requestMatchers(HttpMethod.POST, "/api/payments/order/{orderId}/cancel")
                        .permitAll()

                        // 결제 내역 상세 조회
                        .requestMatchers(HttpMethod.GET, "/api/payments/**").permitAll()

                        // 결제 내역 전체 조회
                        .requestMatchers(HttpMethod.GET, "/api/payments").permitAll()

                        // 상품 관련
                        // 상품 생성
                        .requestMatchers(HttpMethod.POST, "/api/products").hasAuthority(OWNER)

                        // 상품 수정
                        .requestMatchers(HttpMethod.PUT, "/api/products/**").hasAuthority(OWNER)

                        // 상품 삭제
                        .requestMatchers(HttpMethod.DELETE, "/api/products/**")
                        .hasAnyAuthority(OWNER, MASTER)

                        // 상품 단일 조회
                        .requestMatchers(HttpMethod.GET, "/api/products/**").permitAll()

                        // 상품 전체 조회
                        .requestMatchers(HttpMethod.GET, "/api/products").permitAll()

                        // 장바구니 관련
                        // 장바구니 메뉴 담기
                        .requestMatchers(HttpMethod.POST, "/api/carts/products")
                        .hasAnyAuthority(CUSTOMER, MASTER)

                        // 장바구니 수량 수정
                        .requestMatchers(HttpMethod.PUT, "/api/carts/products")
                        .hasAnyAuthority(CUSTOMER, MASTER)

                        // 장바구니 메뉴 전체 삭제
                        .requestMatchers(HttpMethod.DELETE, "/api/carts/products")
                        .hasAnyAuthority(CUSTOMER, MASTER)

                        // 장바구니 메뉴 삭제
                        .requestMatchers(HttpMethod.DELETE, "/api/carts/products/**")
                        .hasAnyAuthority(CUSTOMER, MASTER)

                        // 장바구니 조회
                        .requestMatchers(HttpMethod.GET, "/api/carts").hasAnyAuthority(CUSTOMER, MASTER)

                        // Chatbot 관련
                        .requestMatchers(HttpMethod.POST, "/api/chats").hasAnyAuthority(OWNER, MASTER));

        http.sessionManagement(
                session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(createJwtAuthorizationFilter(), JwtAuthenticationFilter.class);
        http.addFilterBefore(createJwtAuthenticationFilter(),
                UsernamePasswordAuthenticationFilter.class);

        http.exceptionHandling((exceptionHandling) ->
                exceptionHandling.authenticationEntryPoint(customAuthenticationEntryPoint)
                        .accessDeniedHandler(customAccessDeniedHandler));

        return http.build();
    }

    public JwtAuthenticationFilter createJwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtUtil, userDetailsService);
        filter.setAuthenticationManager(authenticationManager(authenticationConfiguration));
        return filter;
    }

    public JwtAuthorizationFilter createJwtAuthorizationFilter() {
        return new JwtAuthorizationFilter(jwtUtil, userDetailsService);
    }

}

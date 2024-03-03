package acs.b3o.config;

import org.springframework.core.convert.converter.Converter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    // @Value 주입을 통한 URI 설정
    private final String jwkSetUri;

    public SecurityConfig(@Value("${jwt.keyset.uri}") String jwkSetUri) {
        this.jwkSetUri = jwkSetUri;
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authconfig) throws Exception {
        return authconfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable) // 이렇게 변경합니다.
            .cors(corsCustomizer -> corsCustomizer.configurationSource(corsConfigurationSource()))
            .authorizeRequests(authorizeRequests ->
                authorizeRequests
                    .requestMatchers("/").permitAll() // 인증 없이 접근 허용
                    .anyRequest().authenticated() // 그 외 모든 요청은 인증 필요
            )
            .oauth2ResourceServer(oauth2 -> oauth2
            .bearerTokenResolver(customBearerTokenResolver())
            .jwt(jwt -> jwt.decoder(jwtDecoder())
                .jwtAuthenticationConverter(jwtAuthenticationConverter()))); // JWT 인증 컨버터 설정);
        return http.build();
    }

    // CORS 설정을 별도의 메서드로 추출하여 가독성 향상
    public CorsConfigurationSource corsConfigurationSource() {
        return request -> {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
            config.setAllowedMethods(Collections.singletonList("*"));
            config.setAllowCredentials(true);
            config.setAllowedHeaders(Collections.singletonList("*"));
            config.setMaxAge(3600L); // 1시간
            return config;
        };
    }

    // JwtDecoder 빈 정의
    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withJwkSetUri(jwkSetUri).build();
    }

    @Bean
    public BearerTokenResolver customBearerTokenResolver() {
        return new BearerTokenResolver() {
            @Override
            public String resolve(HttpServletRequest request) {
                // 'accessToken'이라는 이름의 쿠키에서 토큰 추출
                    if (request.getCookies() != null) {
                        for (Cookie cookie : request.getCookies()) {
                            if (cookie.getName().contains("accessToken")) {
                                System.out.println(cookie.getValue());
                                return cookie.getValue();
                            }
                        }
                    }
                // 쿠키에 accessToken이 없는 경우 null 반환
                return null;
            }
        };
    }

    public Converter<Jwt, ? extends AbstractAuthenticationToken> jwtAuthenticationConverter() {
        return jwt -> {
            String username = jwt.getClaimAsString("username"); // 'sub' 클레임에서 사용자 이름 추출
            System.out.println(username);
            Collection<GrantedAuthority> authorities = Collections.emptyList(); // 권한 설정
            return new UsernamePasswordAuthenticationToken(username, null, authorities);
        };
    }

}

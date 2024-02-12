package acs.b3o.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authconfig) throws Exception {
        return authconfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(Customizer.withDefaults()) // 이렇게 변경합니다.
            .authorizeRequests(authorizeRequests ->
                authorizeRequests
                    .requestMatchers("/").permitAll() // 인증 없이 접근 허용
                    .anyRequest().authenticated() // 그 외 모든 요청은 인증 필요
            )
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt.decoder(jwtDecoder()))); // JWT 디코더 설정
        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        String uri = "";

        NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withJwkSetUri(uri).build();
        return jwtDecoder;
        // JWT 디코더 설정을 위한 코드를 여기에 추가하세요.
        // 예를 들어, `NimbusJwtDecoder`를 사용할 수 있습니다.
    }
}

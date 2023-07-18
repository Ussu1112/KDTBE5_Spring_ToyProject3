package fastcampus.group9.toyproject3._core.security;


import fastcampus.group9.toyproject3._core.errors.exception.Exception401;
import fastcampus.group9.toyproject3._core.errors.exception.Exception403;
import fastcampus.group9.toyproject3._core.utils.FilterResponseUtils;
import fastcampus.group9.toyproject3.user.UserRole;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static fastcampus.group9.toyproject3._core.utils.FilterResponseUtils.*;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public class CustomSecurityFilterManager extends AbstractHttpConfigurer<CustomSecurityFilterManager, HttpSecurity> {
        @Override
        public void configure(HttpSecurity builder) throws Exception {
            AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);
            builder.addFilter(new JwtAuthenticationFilter(authenticationManager));
            super.configure(builder);
        }
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. CSRF 해제
                .csrf().disable()
                // 2. iframe 거부
                .headers().frameOptions().sameOrigin()
                // 3. cors 재설정
                .and().cors().configurationSource(configurationSource())
                // 4. jSessionId가 응답이 될 때 세션영 역에서 사라진다 (이게 stateless)
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                // 5. form 로긴 해제 (UsernamePasswordAuthenticationFilter 비활성화)
                .and().formLogin().disable()
                // 6. 로그인 인증창이 뜨지 않게 비활성화
                .httpBasic().disable()
                // 7. 커스텀 필터 적용 (시큐리티 필터 교환)
                .apply(new CustomSecurityFilterManager());

                // 8. 인증 실패 처리
        http.exceptionHandling().authenticationEntryPoint((request, response, authException) -> {
            unAuthorized(response, new Exception401("인증되지 않았습니다"));
        });
        // 9. 권한 실패 처리
        http.exceptionHandling().accessDeniedHandler((request, response, accessDeniedException) -> {
            forbidden(response, new Exception403("권한이 없습니다"));
        });

        // 11. 인증, 권한 필터 설정
        http
                .authorizeRequests()
                .antMatchers("/","/h2-console/**").permitAll()
                .antMatchers("/board/**").authenticated()
                .antMatchers("/admin/**").hasRole(UserRole.ADMIN.getName())
                .anyRequest().permitAll();

        return http.build();
    }

    public CorsConfigurationSource configurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*"); // GET, POST, PUT, DELETE (Javascript 요청 허용)
        // localhost:8080 백엔드, localhost:3000 프론트엔드
        configuration.addAllowedOriginPattern("*"); // 모든 IP 주소 허용 (프론트 앤드 IP만 허용 react)
        configuration.setAllowCredentials(true); // 클라이언트에서 쿠키 요청 허용
        configuration.addExposedHeader("Authorization"); // 옛날에는 디폴트 였다. 지금은 아닙니다.
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // /login, /board, /product/
        return source;
    }
}

package fastcampus.group9.toyproject3._core.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
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
//                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                // 5. form Login 설정 -  POST + X-WWW-Form-urlEncoded
                .and().formLogin()
                .usernameParameter("username")
                .passwordParameter("password")
                .loginProcessingUrl("/loginForm")
                .defaultSuccessUrl("/")
                .failureHandler(
                        (request, response, exception) -> {
                            System.out.println("exception = " + exception);
                            response.sendRedirect("/login");
                        }
                );

        // 인증, 권한 필터 설정
        http
                .authorizeRequests()
                .antMatchers("/","/h2-console/**").permitAll()
                .antMatchers("/board/**").authenticated()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().permitAll();

        return http.build();
    }

    public CorsConfigurationSource configurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.addAllowedOriginPattern("*");
        configuration.setAllowCredentials(true);
        configuration.addExposedHeader("Authorization");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}

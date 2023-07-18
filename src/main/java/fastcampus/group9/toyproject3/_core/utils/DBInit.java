package fastcampus.group9.toyproject3._core.utils;

import fastcampus.group9.toyproject3.user.User;
import fastcampus.group9.toyproject3.user.UserRepository;
import fastcampus.group9.toyproject3.user.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@RequiredArgsConstructor
@Component
public class DBInit {

    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner initDB(UserRepository userRepository){
        return args -> {
            User user = User.builder()
                    .username("ssar")
                    .password(passwordEncoder.encode("1234"))
                    .email("ssar@nate.com")
                    .nickname("쌀")
                    .role(UserRole.SPROUT)
                    .build();
            User admin = User.builder()
                    .username("테스트")
                    .password(passwordEncoder.encode("1234"))
                    .email("test@admin.com")
                    .nickname("test")
                    .role(UserRole.ADMIN)
                    .build();
            userRepository.saveAll(Arrays.asList(user, admin));
        };
    }
}

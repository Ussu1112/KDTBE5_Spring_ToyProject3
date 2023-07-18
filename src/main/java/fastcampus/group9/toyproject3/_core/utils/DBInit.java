package fastcampus.group9.toyproject3._core.utils;

import fastcampus.group9.toyproject3.user.User;
import fastcampus.group9.toyproject3.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@RequiredArgsConstructor
@Component
public class DBInit {

    @Bean
    CommandLineRunner initDB(UserRepository userRepository){
        return args -> {
            User user = User.builder()
                    .username("ssar")
                    .password("1234")
                    .email("ssar@nate.com")
                    .nickname("쌀")
                    .build();
            User admin = User.builder()
                    .username("admin")
                    .password("1234")
                    .email("admin@admin.com")
                    .nickname("관리자계정")
                    .build();
            userRepository.saveAll(Arrays.asList(user, admin));
        };
    }
}

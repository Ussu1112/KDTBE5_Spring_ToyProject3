package fastcampus.group9.toyproject3._core.util;

import fastcampus.group9.toyproject3.user.User;
import fastcampus.group9.toyproject3.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DBInit {

    @Bean
    CommandLineRunner initDB(UserRepository userRepository){
        return args -> {
            User ssar = User.builder()
                    .username("ssar")
                    .password("1234")
                    .email("ssar@nate.com")
                    .nickname("쌀")
                    .build();
            userRepository.save(ssar);
        };
    }
}

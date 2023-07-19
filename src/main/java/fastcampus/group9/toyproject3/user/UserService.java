package fastcampus.group9.toyproject3.user;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponse.JoinDTO join(UserRequest.JoinDTO joinDTO) {
        System.out.println("service join 실행");

        boolean isExistUser = userRepository.existsByUsername(joinDTO.getUsername());

        if(isExistUser){
            throw new RuntimeException("이미 사용중인 ID 입니다.");
        }
        joinDTO.setPassword(passwordEncoder.encode(joinDTO.getPassword()));

        User userPS = userRepository.save(joinDTO.toEntity());
        return new UserResponse.JoinDTO(userPS);
    }

    public User findById(Long id) {
        Optional<User> userOP = userRepository.findById(id);
        if (userOP.isPresent()) {
            return userOP.get();
        } else {
            return null;
        }

    }

}

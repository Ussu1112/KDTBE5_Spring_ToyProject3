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

    public User findByUsername(String username) {
        Optional<User> userOP = userRepository.findByUsername(username);
        if (userOP.isPresent()) {
            return userOP.get();
        } else {
            return null;
        }
    }

    public User updateForm(Long id) {
        Optional<User> userOP = userRepository.findById(id);
        if (userOP.isPresent()) {
            return userOP.get();
        } else {
            return null;
        }
    }

    @Transactional
    public void update(UserRequest.UpdateDTO updateDTO) {
        Optional<User> userOP = userRepository.findById(updateDTO.getId());
        User user = userOP.get();
        user.updateEmail(updateDTO.getEmail());
        user.updateNickname(updateDTO.getNickname());

    }

    public String usernameCheck(String username) {

//        Optional<User> userOP = userRepository.findByUsername(username);
//        if (userOP.isPresent()) {
//            return null;
//        } else {
//            return "ok";
//        }
//
        boolean isExistUser = userRepository.existsByUsername(username);
        if (isExistUser) {
            // 조회결과가 있다 -> 사용할 수 없다.
            return null;
        } else {
            // 조회결과가 없다 -> 사용할 수 있다.
            return "ok";
        }
    }

}

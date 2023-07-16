package fastcampus.group9.toyproject3.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void join(UserRequest.JoinDTO joinDTO) {

        System.out.println("service join 실행");

        Optional<User> userOP = userRepository.findByUsername(joinDTO.getUsername());

        if(userOP.isPresent()){
            throw new RuntimeException("이미 사용중인 ID 입니다.");
        }

        User userPS = userRepository.save(joinDTO.toEntity());

        System.out.println("service/join 성공");
        System.out.println("userPS = " + userPS);
    }

    @Transactional
    public UserRequest.LoginDTO login(UserRequest.LoginDTO loginDTO) {

        System.out.println("service login 실행");


        Optional<User> userOP = userRepository.findByUsername(loginDTO.getUsername());
        if (userOP.isPresent()) {
            User user = userOP.get();

            if (user.getPassword().equals(loginDTO.getPassword())) {
                UserRequest.LoginDTO loginUser = UserRequest.LoginDTO.toLoginDTO(user);
                return loginUser;
            } else {
                throw new RuntimeException("아이디 혹은 패스워드가 틀렸습니다");
            }

        } else {
            throw new RuntimeException("아이디 혹은 패스워드가 틀렸습니다");
        }
    }

}

package fastcampus.group9.toyproject3._core.utils;

import fastcampus.group9.toyproject3.user.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class DummyObjectUtils {

    protected static User newUser(String username, String fullName) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encPassword = passwordEncoder.encode("1234");
        return User.builder()
                .username(username)
                .password(encPassword)
                .email(username + "@nate.com")
                .fullName(fullName)
                .status(true)
                .roles("ROLE_USER")
                .build();
    }


//    protected Account newAccount(Integer number, User user) {
//        return Account.builder()
//                .number(number)
//                .password(1234)
//                .balance(1000L)
//                .user(user)
//                .build();
//    }
}

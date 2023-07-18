package fastcampus.group9.toyproject3.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

public class UserRequest {

    @ToString
    @Setter
    @Getter
    public static class JoinDTO {

        @NotEmpty (message = "이름을 입력해주세요.")
        private String username;

        @NotEmpty (message = "비밀번호를 입력해주세요.")
        private String password;

        @NotEmpty (message = "이메일을 입력해주세요.")
        private String email;

        @NotEmpty (message = "닉네임을 입력해주세요.")
        private String nickName;

        public User toEntity() {
            return User.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .nickName(nickName)
                    .role(UserRole.SPROUT)
                    .build();
        }
    }

    @ToString
    @Setter
    @Getter
    public static class LoginDTO {
        @NotEmpty
        private String username;
        @NotEmpty
        private String password;

        public static LoginDTO toLoginDTO(User user) {
            LoginDTO loginDTO = new LoginDTO();
            loginDTO.setUsername(user.getUsername());
            loginDTO.setPassword(user.getPassword());
            return loginDTO;
        }
    }
}

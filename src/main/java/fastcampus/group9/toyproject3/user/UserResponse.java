package fastcampus.group9.toyproject3.user;

import lombok.Getter;
import lombok.Setter;

public class UserResponse {

    @Getter @Setter
    public static class JoinDTO {
        private Long id;
        private String username;
        private String email;
        private String nickname;

        public JoinDTO(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.email = user.getEmail();
            this.nickname = user.getNickname();
        }
    }
}

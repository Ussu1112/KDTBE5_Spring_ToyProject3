package fastcampus.group9.toyproject3.board;

import fastcampus.group9.toyproject3.user.User;
import fastcampus.group9.toyproject3.user.UserRole;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

public class BoardRequest {

    @Setter
    @Getter
    public static class CreateDTO {

        private Long id;
        private String title;
        private String content;
        private String thumbnail;
        private String author;
        private User user;
        private UserRole userRole;
        private boolean isReported;

        //파일관리
        private MultipartFile boardFile;
        private String originalFileName;
        private String storedFileName;

        public Board toEntity() {
            return Board.builder()
                    .id(this.id)
                    .title(this.title)
                    .content(this.content)
                    .thumbnail(this.thumbnail)
                    .author(this.author)
                    .user(this.user)
                    .userRole(this.userRole)
                    .isReported(this.isReported)
                    .build();
        }
    }
}

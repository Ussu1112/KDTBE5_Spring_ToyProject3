package fastcampus.group9.toyproject3.board;

import fastcampus.group9.toyproject3.user.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

public class BoardRequest {

    @Setter
    @Getter
    public static class CreateDTO {

        private Long id;
        private String title;
        private String content;
        private String thumbnail;
        private User user;
        private LocalDateTime createdAt;
        private String role;
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
                    .user(this.user)
                    .createdAt(LocalDateTime.now())
                    .role(this.role)
                    .isReported(this.isReported)
                    .build();
        }
    }
}

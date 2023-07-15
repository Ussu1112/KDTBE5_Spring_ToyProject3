package fastcampus.group9.toyproject3.board;

import fastcampus.group9.toyproject3.user.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class BoardResponse {

    @Getter @Setter
    public static class SelectDTO {
        private Long id;
        private String title;
        private String content;
        private String thumbnail;
        private User user;
        private LocalDateTime createdAt;
        private String role;
        private boolean isReported;

        public SelectDTO(Board board) {
            this.id = board.getId();
            this.title = board.getTitle();
            this.content = board.getContent();
            this.thumbnail = board.getThumbnail();
            this.user = board.getUser();
            this.createdAt = board.getCreatedAt();
            this.role = board.getRole();
            this.isReported = board.isReported();
        }
    }

}

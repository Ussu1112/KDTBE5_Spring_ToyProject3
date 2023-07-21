package fastcampus.group9.toyproject3.board;

import fastcampus.group9.toyproject3.user.User;
import fastcampus.group9.toyproject3.user.UserRole;
import lombok.Getter;
import lombok.Setter;

public class BoardResponse {

    @Getter @Setter
    public static class SelectDTO {
        private Long id;
        private String title;
        private String content;
        private String thumbnail;
        private String author;
        private User user;
        private UserRole userRole;
        private boolean isReported;

        public SelectDTO(Board board) {
            this.id = board.getId();
            this.title = board.getTitle();
            this.content = board.getContent();
            this.thumbnail = board.getThumbnail();
            this.author = board.getAuthor();
            this.user = board.getUser();
            this.userRole = board.getUserRole();
            this.isReported = board.isReported();
        }
    }

}

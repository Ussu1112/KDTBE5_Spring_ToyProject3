package fastcampus.group9.toyproject3.board.comment;

import fastcampus.group9.toyproject3.user.User;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
public class CommentResponse {
    public static class SelectDTO {
        private Long id;
        private User user;
        private String content;
        private Comment parentComment;
        private LocalDateTime createdAt;

        public SelectDTO(Comment comment) {
            this.id = comment.getId();
            this.user = comment.getUser();
            this.content = comment.getContent();
            this.parentComment = comment.getParentComment();
            this.createdAt = comment.getCreatedAt();
        }
    }

}

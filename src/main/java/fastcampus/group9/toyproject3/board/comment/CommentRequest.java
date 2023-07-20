package fastcampus.group9.toyproject3.board.comment;

import fastcampus.group9.toyproject3.board.Board;
import fastcampus.group9.toyproject3.user.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class CommentRequest {
    @Getter
    @Setter
    @ToString
    public static class CreateDTO {
        private Board board;
        private User user;
        private String content;
        private Comment parentComment;

        public Comment toEntity(User user) {
            return Comment.builder()
                    .board(this.board)
                    .user(user)
                    .content(this.content)
                    .parentComment(this.parentComment)
                    .build();
        }
    }
}

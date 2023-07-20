package fastcampus.group9.toyproject3.board.comment;

import fastcampus.group9.toyproject3.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;


public class CommentResponse {
    @Builder
    @Getter
    public static class SelectDTO {
        private Long id;
        private User user;
        private String content;
        private Comment parentComment;
        private LocalDateTime createdAt;
    }
}

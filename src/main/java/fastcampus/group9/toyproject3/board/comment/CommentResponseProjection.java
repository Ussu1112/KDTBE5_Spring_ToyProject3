package fastcampus.group9.toyproject3.board.comment;

import fastcampus.group9.toyproject3.user.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;


public interface CommentResponseProjection {
        Long getId();
        User getUser();
        String getContent();
        Comment getParentComment();
        LocalDateTime getCreatedAt();
    }

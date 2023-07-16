package fastcampus.group9.toyproject3.board.comment;

import fastcampus.group9.toyproject3.board.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}

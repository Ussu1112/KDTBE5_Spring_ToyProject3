package fastcampus.group9.toyproject3.board.comment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<CommentResponseProjection> findAllByBoard_Id(Long boardId);
}

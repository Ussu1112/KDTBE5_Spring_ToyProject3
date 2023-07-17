package fastcampus.group9.toyproject3.board.comment;

import fastcampus.group9.toyproject3.board.comment.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    @Transactional
    public List<CommentResponse.SelectDTO> findCommentList(Long boardId) {
        List<CommentResponse.SelectDTO> commentList = commentRepository.findAllByBoard_Id(boardId);
        return commentList;
    }
}

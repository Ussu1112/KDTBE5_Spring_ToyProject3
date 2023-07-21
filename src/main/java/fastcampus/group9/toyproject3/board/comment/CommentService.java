package fastcampus.group9.toyproject3.board.comment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {
    private final CommentRepository commentRepository;

    @Transactional
    public List<Comment> findCommentList(Long boardId) {
        return commentRepository.findAllByBoard_Id(boardId);
    }

    @Transactional
    public List<Comment> findAll(){
        return commentRepository.findAll();
    }

    @Transactional
    public void deleteComment(Long id){
        commentRepository.deleteById(id);
    }

    @Transactional
    public void saveComment(Comment comment){
        commentRepository.save(comment);
    }

}

package fastcampus.group9.toyproject3.board.comment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {
    private final CommentRepository commentRepository;

    @Transactional
    public List<CommentResponse.SelectDTO> findCommentList(Long boardId) {
        //todo 스트림으로 수정
        List<CommentResponseProjection> commentProjections = commentRepository.findAllByBoard_Id(boardId);
        List<CommentResponse.SelectDTO> commentList = new ArrayList<>();
        for(CommentResponseProjection p : commentProjections){
            CommentResponse.SelectDTO comment = CommentResponse.SelectDTO.builder()
                    .id(p.getId())
                    .user(p.getUser())
                    .content(p.getContent())
                    .parentComment(p.getParentComment())
                    .createdAt(p.getCreatedAt())
                    .build();
            commentList.add(comment);
        }
        log.info("List<CommentResponse.SelectDTO>: {}", commentList);
        return commentList;
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

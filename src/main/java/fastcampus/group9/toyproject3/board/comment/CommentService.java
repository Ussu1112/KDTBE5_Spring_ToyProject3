package fastcampus.group9.toyproject3.board.comment;

import fastcampus.group9.toyproject3.board.comment.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

}

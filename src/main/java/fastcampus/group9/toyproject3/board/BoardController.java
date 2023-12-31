package fastcampus.group9.toyproject3.board;

import fastcampus.group9.toyproject3._core.security.CustomUserDetails;
import fastcampus.group9.toyproject3.board.comment.Comment;
import fastcampus.group9.toyproject3.board.comment.CommentRequest;
import fastcampus.group9.toyproject3.board.comment.CommentService;
import fastcampus.group9.toyproject3.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
@Slf4j
public class BoardController {

    private final BoardService boardService;
    private final CommentService commentService;

    @GetMapping("/write")
    public String boardSave() {

        return "boardWrite";
    }

    @PostMapping("/save")
    @ResponseBody
    public String boardSave(BoardRequest.CreateDTO board) throws IOException {
        boardService.save(board, getCurrentUser());

        return responseAlert("등록", "/board/list");
    }

    @GetMapping("/list")
    public String boardList(
            String category,
            Model model,
            @PageableDefault(sort = "id", size = 6, direction = Sort.Direction.DESC) Pageable pageable) {
        Page<BoardResponse.SelectDTO> boardList = boardService.pageList(category, pageable);
        List<Long> indexes;
        long index = (boardList.getTotalElements() - 1) / boardList.getSize();
        indexes = LongStream.rangeClosed(0, index).boxed().collect(Collectors.toList());

        model.addAttribute("list", boardList);
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());
        model.addAttribute("index", indexes);
        model.addAttribute("max", boardList.hasNext());
        model.addAttribute("min", boardList.hasPrevious());

        return "boardList";
    }

    @GetMapping("/searchlist")
    public String serachList(@RequestParam("type") String type, @RequestParam("question") String question, Model model,
                             @PageableDefault(sort = "id", size = 6, direction = Sort.Direction.DESC) Pageable pageable) {
        Page<BoardResponse.SelectDTO> boardList = boardService.pageSearchList(pageable, type, question);

        model.addAttribute("list", boardList);
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());
        model.addAttribute("max", boardList.hasNext());
        model.addAttribute("min", boardList.hasPrevious());

        return "boardList";
    }

    @GetMapping("/view/{id}")
    public String boardView(@PathVariable Long id, Model model) {
        model.addAttribute("board", boardService.findBoard(id));
        model.addAttribute("comments", readComments(id));
        model.addAttribute("currentUser", getCurrentUser());
        return "boardView";
    }

    private List<Comment> readComments(Long boardId) {
        return commentService.findCommentList(boardId);
    }

    @GetMapping("/edit/{id}")
    public String boardModify(@PathVariable Long id, Model model) {
        BoardResponse.SelectDTO board = boardService.findBoardById(id);
        model.addAttribute("board", board);

        return "boardEdit";
    }

    @PostMapping("/update/{id}")
    @ResponseBody
    public String boardModify(@PathVariable Long id, BoardRequest.CreateDTO board) {
        boardService.update(id, board);
        String link = "/board/view/" + id;

        return responseAlert("수정", link);
    }


    @GetMapping("/delete/{id}")
    @ResponseBody
    public String boardDelete(@PathVariable Long id) {
        boardService.delete(id);

        return responseAlert("삭제", "/board/list");
    }

    @PostMapping("/view/{boardId}/delete/{commentId}")
    public String deleteComment(@PathVariable Long boardId, @PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return "redirect:/board/view/{boardId}";
    }

    @PostMapping("view/{boardId}/write")
    public String saveComment(@PathVariable Long boardId, CommentRequest.CreateDTO comment) {
        log.info("CommentRequest.CreateDTO: {}", comment);
        commentService.saveComment(comment.toEntity(getCurrentUser()));
        return "redirect:/board/view/{boardId}";
    }

    private User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CustomUserDetails userDetails = (CustomUserDetails) principal;
        return userDetails.getUser();
    }

    @GetMapping("/comment/all/test")
    public void findAllComment() {
        List<Comment> all = commentService.findAll();
        all.forEach(comment -> log.info("comment: {}", comment));
    }

    private String responseAlert(String content, String link) {

        return "<script>alert('" + content + "되었습니다.');location.href='" + link + "'</script>";
    }
}
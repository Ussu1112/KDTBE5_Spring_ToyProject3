package fastcampus.group9.toyproject3.board;

import fastcampus.group9.toyproject3.board.comment.Comment;
import fastcampus.group9.toyproject3.board.comment.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final CommentService commentService;

    @GetMapping("/write")
    public String boardSave() {

        return "boardWrite";
    }

    @PostMapping("/save")
    public String boardSave(BoardRequest.CreateDTO board) throws IOException {
        board.setCreatedAt(LocalDateTime.now());
        boardService.save(board);

        return "redirect:list";
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
        return "boardView";
    }

    private List<Comment> readComments(Long boardId){
        return commentService.findCommentList(boardId);
    }
    @GetMapping("/edit/{id}")
    public String boardModify(@PathVariable Long id, BoardRequest.CreateDTO board, Model model) {
        model.addAttribute("board", board);

        return "boardList";
    }

    @PutMapping("/update/{id}")
    public String boardModify(@PathVariable Long id, BoardRequest.CreateDTO board) {
        boardService.update(id, board);

        return "redirect:board/view/{id}";
    }

    @DeleteMapping("/delete/{id}")
    public String boardDelete(@PathVariable Long id) {
        boardService.delete(id);

        return "boardList";
    }

}
package fastcampus.group9.toyproject3.report;

import fastcampus.group9.toyproject3.board.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;
    private final BoardService boardService;

    @RequestMapping("/{boardId}")
    public String reportBoard(@PathVariable Long boardId, ReportRequest.CreateDTO report){
//        Board board = boardService.findBoard(boardId);
        reportService.saveReport(report.toEntity());
        return "redirect: /board/list";
    }

}

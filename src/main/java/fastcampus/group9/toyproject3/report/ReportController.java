package fastcampus.group9.toyproject3.report;

import fastcampus.group9.toyproject3.board.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @PostMapping("")
    public String reportBoard(ReportRequest.CreateDTO report){
        reportService.saveReport(report.toEntity());
        return "redirect:/board/list";
    }

}

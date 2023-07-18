package fastcampus.group9.toyproject3.report;

import fastcampus.group9.toyproject3.board.Board;
import lombok.Getter;
import lombok.Setter;

public class ReportRequest {
    @Getter
    @Setter
    public static class CreateDTO {
        private Board board;
        private String reportContent;

        public Report toEntity(){
            return Report.builder()
                    .board(this.board)
                    .reportContent(this.reportContent)
                    .build();
        }
    }
}

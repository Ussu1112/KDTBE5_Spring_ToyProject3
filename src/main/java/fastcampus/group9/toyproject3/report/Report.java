package fastcampus.group9.toyproject3.report;

import fastcampus.group9.toyproject3._core.utils.BaseTimeEntity;
import fastcampus.group9.toyproject3.board.Board;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.print.attribute.standard.MediaSize;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "report_tb")
@Entity
@Getter
public class Report extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Board board;

    @Column(nullable = false)
    private boolean reportFlag;

    private String reportContent;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}

package fastcampus.group9.toyproject3.report;

import fastcampus.group9.toyproject3.board.Board;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class ReportList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @Column(nullable = false)
    private Board board;

    @Column(nullable = false)
    private boolean reportFlag;

    private String reportContent;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    private void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt=createdAt;
    }

    @PreUpdate
    private void preUpdate(){
        this.updatedAt = LocalDateTime.now();
    }
}

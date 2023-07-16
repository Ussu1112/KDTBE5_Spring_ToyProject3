package fastcampus.group9.toyproject3.board;

import fastcampus.group9.toyproject3.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @Column(nullable = false)
    private Board board;

    @ManyToOne
    @Column(nullable = false)
    private User user;

    @Size(max = 50)
    private String content;

    @Column(nullable = false)
    private int groupNumber;

    @Column(nullable = false)
    @ColumnDefault("0")
    private int depth;

    @Column(nullable = false)
    @ColumnDefault("0")
    private int order;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    //데이터 생성 전 작업
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

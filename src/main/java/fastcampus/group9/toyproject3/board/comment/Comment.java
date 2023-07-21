package fastcampus.group9.toyproject3.board.comment;

import fastcampus.group9.toyproject3._core.utils.BaseTimeEntity;
import fastcampus.group9.toyproject3.board.Board;
import fastcampus.group9.toyproject3.user.User;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Getter
@ToString
@Table(name = "comment_tb")
public class Comment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Board board;

    @ManyToOne
    private User user;

    @Size(max = 50)
    private String content;

    @Column(nullable = false)
    @ColumnDefault("0")
    private int depth;

    @ManyToOne
    @JoinColumn(name = "parent_comment_id")
    private Comment parentComment;

    //Todo FetchTpye.EAGER를 사용해야만 대댓글까지 표시할 수 있는 문제 해결 필요함
    @OneToMany(mappedBy = "parentComment", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> replyComments;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}

package fastcampus.group9.toyproject3.board;

import fastcampus.group9.toyproject3._core.utils.BaseTimeEntity;
import fastcampus.group9.toyproject3.board.Thumbnail.Thumbnail;
import fastcampus.group9.toyproject3.user.User;
import fastcampus.group9.toyproject3.user.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "board_tb")
@Entity
@Builder
public class Board extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(length = 50000, columnDefinition = "TEXT")
    private String content;
    private String thumbnail;
    private String author;
    @Enumerated(EnumType.STRING)
    private UserRole userRole;
    private boolean isReported;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @OneToOne(mappedBy = "board", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private Thumbnail thumbnailEntity;

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}

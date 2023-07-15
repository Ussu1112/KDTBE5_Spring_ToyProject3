package fastcampus.group9.toyproject3.board;

import fastcampus.group9.toyproject3.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "board_tb")
@Entity
@Builder
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private String thumbnail;
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "fullName")
    private User user;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean isReported;
    private String role;

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}

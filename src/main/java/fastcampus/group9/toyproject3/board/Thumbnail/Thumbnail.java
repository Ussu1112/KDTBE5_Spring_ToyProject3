package fastcampus.group9.toyproject3.board.Thumbnail;

import fastcampus.group9.toyproject3.board.Board;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "thumbnail_tb")
@Entity
@Builder
public class Thumbnail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String originalFileName;
    private String storedFileName;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    //TODO, Setter 빼고 DTO로 설계하기 vs 그냥 냅두기
    public static Thumbnail toEntity(Board board, String originalFileName,String storedFileName) {
        Thumbnail thumbnail = new Thumbnail();
        thumbnail.setOriginalFileName(originalFileName);
        thumbnail.setStoredFileName(storedFileName);
        thumbnail.setBoard(board);
        return thumbnail;
    }
}

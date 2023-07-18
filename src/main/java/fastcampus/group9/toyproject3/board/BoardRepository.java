package fastcampus.group9.toyproject3.board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query(value = "SELECT b FROM Board b WHERE b.title LIKE %:title%")
    Page<BoardResponse.SelectDTO> findByTitle(String title, Pageable pageable);

    @Query(value = "SELECT b FROM Board b WHERE b.author = :author")
    Page<BoardResponse.SelectDTO> findByFullName(@Param("author") String fullName, Pageable pageable);

    @Query(value = "SELECT b FROM Board b WHERE b.content LIKE %:content%")
    Page<BoardResponse.SelectDTO> findByContent(@Param("content") String content, Pageable pageable);
}

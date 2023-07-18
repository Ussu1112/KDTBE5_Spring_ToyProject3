package fastcampus.group9.toyproject3.board;

import fastcampus.group9.toyproject3.board.Thumbnail.Thumbnail;
import fastcampus.group9.toyproject3.board.Thumbnail.ThumbnailRepository;
import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final ThumbnailRepository thumbnailRepository;
    private final EntityManager entityManager;

    @Transactional
    public void save(BoardRequest.CreateDTO parameter) throws IOException {
        //게시글 정보 삽입 - html에서 넣어도 되긴 함
        //TODO Member의 Principal값 넣어서 저장
        parameter.setRole("user");
        parameter.setReported(false);

        //파일처리 메소드
        //TODO, 파일경로 설정방법 찾기
        if (parameter.getBoardFile().isEmpty()) {
            parameter.setThumbnail("defaultThumbnail.png");
            boardRepository.save(parameter.toEntity());
        } else {
            MultipartFile boardFile = parameter.getBoardFile();

            String originalFilename = boardFile.getOriginalFilename();
            UUID uuid = UUID.randomUUID();
            String storedFileName = uuid + "_" + originalFilename;
            String path = "C:/KDTBE5_Board_thumbnails/" + storedFileName;
            parameter.setThumbnail(storedFileName);
            boardFile.transferTo(new File(path));
            Thumbnails.of(new File(path)).size(450, 300).toFile(new File(path));
            Board board = parameter.toEntity();
            boardRepository.save(board);

            Thumbnail thumbnail = Thumbnail.toEntity(board, originalFilename, storedFileName);
            thumbnailRepository.save(thumbnail);
        }

    }

    @Transactional
    public Board findBoard(Long id){
        Optional<Board> board = boardRepository.findById(id);
        if(board.isPresent()){
            return board.get();
        }
        //Todo 검색결과가 없음
        throw new NoSuchElementException();
    }


    @Transactional
    public List<BoardResponse.SelectDTO> findBoardList() {
        List<Board> list = boardRepository.findAll();
        return list.stream().map(BoardResponse.SelectDTO::new).collect(Collectors.toList());
    }

    @Transactional
    public Long update(Long id, BoardRequest.CreateDTO board) {
        Board origin = boardRepository.findById(id).orElseThrow(() -> new NullPointerException("exception"));
        origin.update(board.getTitle(), board.getContent());
        entityManager.flush();
        entityManager.clear();

        return id;
    }

    @Transactional
    public Page<BoardResponse.SelectDTO> pageList(String category, Pageable pageable) {
        return boardRepository.findByUser_Roles(category, pageable);
//        return boardRepository.findAll(pageable).map(BoardResponse.SelectDTO::new);
    }

    @Transactional
    public Page<BoardResponse.SelectDTO> pageSearchList(Pageable pageable, String type, String question) {
        //TODO, 분기문 말고 구현할 수 있는 방법 찾기
        switch (type) {
            case "title":
                return boardRepository.findByTitle(question, pageable);
            case "content":
                return boardRepository.findByContent(question, pageable);
            case "nickName":
                return boardRepository.findByFullName(question, pageable);
        }
        //TODO 예외처리, 검색결과가 없음
        throw new IllegalArgumentException();
    }

    @Transactional
    public void delete(Long id) {
        boardRepository.deleteById(id);
    }

}


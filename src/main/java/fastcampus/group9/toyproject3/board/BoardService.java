package fastcampus.group9.toyproject3.board;

import fastcampus.group9.toyproject3.board.Thumbnail.Thumbnail;
import fastcampus.group9.toyproject3.board.Thumbnail.ThumbnailRepository;
import fastcampus.group9.toyproject3.user.User;
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final ThumbnailRepository thumbnailRepository;
    private final EntityManager entityManager;

    private static final String FOLDER_PATH = "C:/KDTBE5_Board_thumbnails/";

    private void mkdir() throws IOException {
        boolean folderExist = Files.exists(Paths.get(FOLDER_PATH));
        if (!folderExist) {
            Path path = Paths.get(FOLDER_PATH);
            Files.createDirectories(path);
        }
    }

    @Transactional
    public void save(BoardRequest.CreateDTO parameter, User user) throws IOException {
        parameter.setUser(user);
        //user_role 0?
        parameter.setAuthor(user.getNickname());
        parameter.setUserRole(user.getRole());
        parameter.setReported(false);

        //파일처리 메소드
        if (parameter.getBoardFile().isEmpty()) {
            boardRepository.save(parameter.toEntity());
        } else {
            MultipartFile boardFile = parameter.getBoardFile();

            String originalFilename = boardFile.getOriginalFilename();
            UUID uuid = UUID.randomUUID();
            String storedFileName = uuid + "_" + originalFilename;
            String path = FOLDER_PATH + storedFileName;
            mkdir();
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
    public Page<BoardResponse.SelectDTO> pageList(Pageable pageable) {
        return boardRepository.findAll(pageable).map(BoardResponse.SelectDTO::new);
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
    public BoardResponse.SelectDTO findBoard(Long id) {
        return boardRepository.findById(id).map(BoardResponse.SelectDTO::new).orElseThrow(() -> new NoSuchElementException("게시글 없음"));
    }

    @Transactional
    public void delete(Long id) {
        boardRepository.deleteById(id);
    }

}


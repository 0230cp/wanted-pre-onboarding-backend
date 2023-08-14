package jaechan.subject.board;

import jaechan.subject.user.User.User;
import jaechan.subject.user.User.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BoardRepositoryTest {

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName(value = "게시판 전체 조회 테스트")
    public void findAllTest(){
        //given
        User user = User.builder()
                .username("고")
                .email("rhwocks69@gmail.com")
                .pw("1234")
                .build();
        userRepository.save(user);

        Board board1 = Board.builder()
                .user(user)
                .title("게시글 1")
                .contents("안녕하세요 고재찬입니다.")
                .build();
        Board board2 = Board.builder()
                .user(user)
                .title("게시글 2")
                .contents("반갑습니다 고재찬입니다.")
                .build();
        boardRepository.save(board1);
        boardRepository.save(board2);
        List<Board> boards = new ArrayList<>();
        boards.add(board1);
        boards.add(board2);

        //when
        List<Board> findBoards = boardRepository.findAll();

        //then
        Assertions.assertThat(boards).isEqualTo(findBoards);
    }

    @Test
    @DisplayName(value = "게시글 눌렀을 때 올바른 게시글 가져오는지 테스트")
    public void findByIdTest(){
        //given
        User user = User.builder()
                .username("고")
                .email("rhwocks69@gmail.com")
                .pw("1234")
                .build();
        userRepository.save(user);

        Board board1 = Board.builder()
                .user(user)
                .title("게시글 1")
                .contents("안녕하세요 고재찬입니다.")
                .build();
        boardRepository.save(board1);

        Long id = board1.getId();

        //when
        Optional<Board> find = boardRepository.findById(id);
        Board findBoard = find.get();

        //then
        Assertions.assertThat(board1).isEqualTo(findBoard);
    }
}

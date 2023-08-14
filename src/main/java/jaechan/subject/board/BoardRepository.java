package jaechan.subject.board;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findAll(); //test 완료
    Optional<Board> findById(Long id); //test 완료
}

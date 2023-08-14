package jaechan.subject.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepsitory extends JpaRepository<Board, Long> {

}

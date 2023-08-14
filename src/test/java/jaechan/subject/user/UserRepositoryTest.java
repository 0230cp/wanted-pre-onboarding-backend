package jaechan.subject.user;

import jaechan.subject.user.User.User;
import jaechan.subject.user.User.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName(value = "이메일로 유저찾기 테스트")
    public void findByIdTest(){
        //given
        User user = User.builder()
                .email("jaychan1124@naver.com")
                .pw("0000")
                .username("고재찬")
                .build();
        userRepository.save(user);

        //when
        User findUser = userRepository.findByEmail("jaychan1124@naver.com").get();

        //then
        Assertions.assertThat(user).isEqualTo(findUser);
    }
}

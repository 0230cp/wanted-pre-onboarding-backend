package jaechan.subject.user.User;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Getter
public class UserService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    /**
     *  회원가입 서비스
     */

    @Transactional
    public String join(UserRequest.JoinDTO joinDto){
        joinDto.setPw(passwordEncoder.encode(joinDto.getPw()));
        User user = joinDto.toEntity();
        try {
            validateDuplicateUser(user);
        } catch(Exception e){
           return e.getMessage();
        }
        try {
            userRepository.save(user);
        } catch (Exception e){
            return "sever error";
        }
        return "회원가입에 성공했습니다.";
    }


    /**
     *  아이디 중복 체크
     */
    public void validateDuplicateUser(User user){
        Optional<User> findUser = userRepository.findByEmail(user.getEmail());

        findUser.ifPresent(m -> {
            new Exception("중복된 아이디입니다.");
        });
    }


    /**
     * 로그인 서비스
     */

    @Override
    public CustomUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isEmpty())
            throw new UsernameNotFoundException("아이디를 확인해 주세요.");
        return new CustomUserDetails(user.get());
    }
}

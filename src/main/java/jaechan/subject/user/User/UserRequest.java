package jaechan.subject.user.User;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserRequest {
    @Setter
    @Getter
    public static class JoinDTO {

        @NotEmpty
        @Pattern (regexp = ".*@.*", message = "이메일에 @가 포함되어 있지 않습니다.")
        private String email;
        private String username;

        @NotEmpty
        @Size(min = 8, message = "비밀번호는 최소 8자 이상입니다.")
        private String pw;

        public User toEntity() {
            return User.builder()
                    .email(email)
                    .username(username)
                    .pw(pw)
                    .build();
        }
    }
}
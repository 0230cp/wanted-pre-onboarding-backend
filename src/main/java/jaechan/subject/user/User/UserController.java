package jaechan.subject.user.User;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {

    @PostMapping("/user/test")
    public Map userResponseTest() {
        Map<String, String> result = new HashMap<>();
        result.put("result","user ok");
        return result;
    }

    @PostMapping("/admin/test")
    public Map adminResponseTest() {
        Map<String, String> result = new HashMap<>();
        result.put("result","admin ok");
        return result;
    }
}

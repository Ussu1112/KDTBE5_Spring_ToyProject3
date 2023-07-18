package fastcampus.group9.toyproject3.user;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;


@RequiredArgsConstructor
@RequestMapping("/user")
@Controller
public class UserController {

    private final UserService userService;

    private final HttpSession session;

    @GetMapping("/joinForm")
    public String joinForm() {
        return "/joinForm";
    }

    @PostMapping("/join")
    public String join(UserRequest.JoinDTO joinDTO) {
        System.out.println("controller join 실행");
        userService.join(joinDTO);
        return "/loginForm";
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "/loginForm";
    }

    @PostMapping("/login")
    public String login(UserRequest.LoginDTO loginDTO) {
        System.out.println("controller login 실행");

        UserRequest.LoginDTO loginResult = userService.login(loginDTO);

        if (loginResult != null) {
            session.setAttribute("username", loginResult.getUsername());
            return "main";
        } else {
            return "loginForm";
        }

    }


}

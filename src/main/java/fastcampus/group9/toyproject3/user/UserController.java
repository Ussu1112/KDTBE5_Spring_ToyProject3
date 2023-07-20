package fastcampus.group9.toyproject3.user;

import fastcampus.group9.toyproject3._core.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/user")
@Controller
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/joinForm")
    public String joinForm() {
        return "joinForm";
    }

    @PostMapping("/join")
    public String join(@Valid UserRequest.JoinDTO joinDTO, Errors errors) {
        userService.join(joinDTO);
        return "redirect:/";
    }

    @GetMapping("/main")
    public String main(@AuthenticationPrincipal CustomUserDetails cud, Model model) {
        model.addAttribute("user", cud);
        return "main";
    }

    @GetMapping("/details/")
    public String findById(@AuthenticationPrincipal CustomUserDetails cud, Model model) {
        model.addAttribute("user", cud);
        return "detailsForm";
    }

    @GetMapping("/details/{username}")
    public String findById(@PathVariable String username, Model model) {
        User user = userService.findByUsername(username);
        model.addAttribute("user", user);
        return "detailsForm";
    }


    @GetMapping("/updateForm/{id}")
    public String updateForm(@PathVariable Long id, Model model) {
        User user = userService.updateForm(id);
        model.addAttribute("user", user);
        return "updateForm";
    }

    @PostMapping("/update")
    public String update(@Valid UserRequest.UpdateDTO updateDTO, Errors errors) {
        userService.update(updateDTO);
        return "redirect:/user/details/" + updateDTO.getUsername();
    }

    @PostMapping("/checkUsername")
    public @ResponseBody String checkUsername(@RequestParam("username") String username) {
        String checkResult = userService.usernameCheck(username);
        System.out.println("checkResult = " + checkResult);
        return checkResult;
    }


}

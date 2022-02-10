package io.security.corespringsecurity.controller.user;


import io.security.corespringsecurity.controller.DummyDto;
import io.security.corespringsecurity.domain.AccountDto;
import io.security.corespringsecurity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping(value="/mypage")
    public String mypage() throws Exception {

        return "user/mypage";
    }

    @ResponseBody
    @GetMapping(value="/api/mypage")
    public ResponseEntity<DummyDto> apiMypage(){
        return new ResponseEntity<>(new DummyDto(), HttpStatus.OK);
    }

    @GetMapping("/users")
    public String createUser(){
        return "user/login/register";
    }

    @PostMapping("/users")
    public String createUser(AccountDto accountDto){
        userService.createUser(accountDto);
        return "redirect:/";
    }
}
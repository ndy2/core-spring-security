package io.security.corespringsecurity.controller.user;


import io.security.corespringsecurity.controller.DummyDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MessageController {

    @GetMapping(value="/messages")
    public String messages() throws Exception {

        return "user/messages";
    }

    @ResponseBody
    @GetMapping("/api/messages")
    public ResponseEntity<DummyDto> apiMessages(){
        System.out.println("MessageController.apiMypage");
        return new ResponseEntity<>(new DummyDto(), HttpStatus.OK);
    }
}
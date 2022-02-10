package io.security.corespringsecurity.controller.admin;

import io.security.corespringsecurity.controller.DummyDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ConfigController {

    @GetMapping("/config")
    public String config(){
        return "admin/config";
    }

    @ResponseBody
    @GetMapping("/api/config")
    public ResponseEntity<DummyDto> apiConfig(){
        return new ResponseEntity<>(new DummyDto(), HttpStatus.OK);
    }
}
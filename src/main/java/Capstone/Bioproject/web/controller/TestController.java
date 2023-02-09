package Capstone.Bioproject.web.controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class TestController {
    @GetMapping("/main/test")
    public String loginSuccess(@RequestParam(value="hello") String hello){
        return hello;
    }

}

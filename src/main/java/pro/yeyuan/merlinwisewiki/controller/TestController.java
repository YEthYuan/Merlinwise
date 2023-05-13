package pro.yeyuan.merlinwisewiki.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.yeyuan.merlinwisewiki.domain.Test;
import pro.yeyuan.merlinwisewiki.service.TestService;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class TestController {

    @Value("${test.hello:TEST}")
    private String testValue;

    @Resource
    private TestService testService;

    @GetMapping("/hello")
    public String hello() {
        return "Hello World!" + testValue;
    }

    @PostMapping("/hello/post")
    public String helloPost(String name) {
        return "Hello World! Post. " + name;
    }

    @GetMapping("/test/list")
    public List<Test> list() {
        return testService.list();
    }
}

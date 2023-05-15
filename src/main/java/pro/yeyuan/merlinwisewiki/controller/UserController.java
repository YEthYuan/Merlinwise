package pro.yeyuan.merlinwisewiki.controller;

import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import pro.yeyuan.merlinwisewiki.req.UserQueryReq;
import pro.yeyuan.merlinwisewiki.req.UserSaveReq;
import pro.yeyuan.merlinwisewiki.resp.CommonResp;
import pro.yeyuan.merlinwisewiki.resp.UserQueryResp;
import pro.yeyuan.merlinwisewiki.resp.PageResp;
import pro.yeyuan.merlinwisewiki.service.UserService;

import javax.annotation.Resource;
import javax.validation.Valid;


@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/list")
    public CommonResp<PageResp<UserQueryResp>> list(@Valid UserQueryReq req) {
        CommonResp<PageResp<UserQueryResp>> resp = new CommonResp<>();
        PageResp<UserQueryResp> list = userService.list(req);
        resp.setContent(list);
        return resp;
    }
    @PostMapping("/save")
    public CommonResp save(@Valid @RequestBody UserSaveReq req) {
        req.setPassword(DigestUtils.md5DigestAsHex(req.getPassword().getBytes()));
        CommonResp resp = new CommonResp<>();
        userService.save(req);
        return resp;
    }

    @DeleteMapping ("/delete/{id}")
    public CommonResp delete(@PathVariable Long id) {
        CommonResp resp = new CommonResp<>();
        userService.delete(id);
        return resp;
    }
}

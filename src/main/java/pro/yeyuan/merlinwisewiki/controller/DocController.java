package pro.yeyuan.merlinwisewiki.controller;

import org.springframework.web.bind.annotation.*;
import pro.yeyuan.merlinwisewiki.req.DocQueryReq;
import pro.yeyuan.merlinwisewiki.req.DocSaveReq;
import pro.yeyuan.merlinwisewiki.resp.DocQueryResp;
import pro.yeyuan.merlinwisewiki.resp.CommonResp;
import pro.yeyuan.merlinwisewiki.resp.PageResp;
import pro.yeyuan.merlinwisewiki.service.DocService;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/doc")
public class DocController {

    @Resource
    private DocService docService;

    @GetMapping("/all")
    public CommonResp<List<DocQueryResp>> all() {
        CommonResp<List<DocQueryResp>> resp = new CommonResp<>();
        List<DocQueryResp> list = docService.all();
        resp.setContent(list);
        return resp;
    }
    @GetMapping("/list")
    public CommonResp<PageResp<DocQueryResp>> list(@Valid DocQueryReq req) {
        CommonResp<PageResp<DocQueryResp>> resp = new CommonResp<>();
        PageResp<DocQueryResp> list = docService.list(req);
        resp.setContent(list);
        return resp;
    }
    @PostMapping("/save")
    public CommonResp save(@Valid @RequestBody DocSaveReq req) {
        CommonResp resp = new CommonResp<>();
        docService.save(req);
        return resp;
    }

//    @DeleteMapping ("/delete/{id}")
//    public CommonResp delete(@PathVariable Long id) {
//        CommonResp resp = new CommonResp<>();
//        docService.delete(id);
//        return resp;
//    }
    @DeleteMapping ("/delete/{idsStr}")
    public CommonResp delete(@PathVariable String idsStr) {
        CommonResp resp = new CommonResp<>();
        List<String> list = Arrays.asList(idsStr.split(","));
        List<Long> longList = list.stream()
                        .map(Long::parseLong)
                                .collect(Collectors.toList());
        docService.delete(longList);
        return resp;
    }
}

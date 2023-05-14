package pro.yeyuan.merlinwisewiki.controller;

import org.springframework.web.bind.annotation.*;
import pro.yeyuan.merlinwisewiki.req.EbookQueryReq;
import pro.yeyuan.merlinwisewiki.req.EbookSaveReq;
import pro.yeyuan.merlinwisewiki.resp.CommonResp;
import pro.yeyuan.merlinwisewiki.resp.EbookQueryResp;
import pro.yeyuan.merlinwisewiki.resp.PageResp;
import pro.yeyuan.merlinwisewiki.service.EbookService;

import javax.annotation.Resource;
import javax.validation.Valid;


@RestController
@RequestMapping("/ebook")
public class EbookController {

    @Resource
    private EbookService ebookService;

    @GetMapping("/list")
    public CommonResp<PageResp<EbookQueryResp>> list(@Valid EbookQueryReq req) {
        CommonResp<PageResp<EbookQueryResp>> resp = new CommonResp<>();
        PageResp<EbookQueryResp> list = ebookService.list(req);
        resp.setContent(list);
        return resp;
    }
    @PostMapping("/save")
    public CommonResp save(@Valid @RequestBody EbookSaveReq req) {
        CommonResp resp = new CommonResp<>();
        ebookService.save(req);
        return resp;
    }

    @PostMapping("/delete/{id}")
    public CommonResp delete(@PathVariable Long id) {
        CommonResp resp = new CommonResp<>();
        ebookService.delete(id);
        return resp;
    }
}

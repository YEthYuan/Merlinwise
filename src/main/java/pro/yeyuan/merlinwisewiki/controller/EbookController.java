package pro.yeyuan.merlinwisewiki.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.yeyuan.merlinwisewiki.req.EbookReq;
import pro.yeyuan.merlinwisewiki.resp.CommonResp;
import pro.yeyuan.merlinwisewiki.resp.EbookResp;
import pro.yeyuan.merlinwisewiki.resp.PageResp;
import pro.yeyuan.merlinwisewiki.service.EbookService;

import javax.annotation.Resource;

@RestController
public class EbookController {

    @Resource
    private EbookService ebookService;

    @GetMapping("/ebook/list")
    public CommonResp<PageResp<EbookResp>> list(EbookReq req) {
        CommonResp<PageResp<EbookResp>> resp = new CommonResp<>();
        PageResp<EbookResp> list = ebookService.list(req);
        resp.setContent(list);
        return resp;
    }
}

package pro.yeyuan.merlinwisewiki.service;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import pro.yeyuan.merlinwisewiki.domain.Ebook;
import pro.yeyuan.merlinwisewiki.domain.EbookExample;
import pro.yeyuan.merlinwisewiki.mapper.EbookMapper;
import pro.yeyuan.merlinwisewiki.req.EbookReq;
import pro.yeyuan.merlinwisewiki.resp.EbookResp;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class EbookService {

    @Resource
    private EbookMapper ebookMapper;

    public List<EbookResp> list(EbookReq req) {
        EbookExample ebookExample = new EbookExample();
        EbookExample.Criteria criteria = ebookExample.createCriteria();
        criteria.andNameLike("%" + req.getName() + "%");

        List<Ebook> ebooks = ebookMapper.selectByExample(ebookExample);

        List<EbookResp> resp = new ArrayList<>();
        for (Ebook ebook : ebooks) {
            EbookResp ebookResp = new EbookResp();
            BeanUtils.copyProperties(ebook, ebookResp);
            resp.add(ebookResp);
        }

        return resp;
    }
}

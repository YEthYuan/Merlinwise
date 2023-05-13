package pro.yeyuan.merlinwisewiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import pro.yeyuan.merlinwisewiki.domain.Ebook;
import pro.yeyuan.merlinwisewiki.domain.EbookExample;
import pro.yeyuan.merlinwisewiki.mapper.EbookMapper;
import pro.yeyuan.merlinwisewiki.req.EbookReq;
import pro.yeyuan.merlinwisewiki.resp.EbookResp;
import pro.yeyuan.merlinwisewiki.resp.PageResp;
import pro.yeyuan.merlinwisewiki.utils.CopyUtil;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EbookService {

    private static final Logger LOG = LoggerFactory.getLogger(EbookService.class);

    @Resource
    private EbookMapper ebookMapper;

    public PageResp<EbookResp> list(EbookReq req) {

        EbookExample ebookExample = new EbookExample();
        EbookExample.Criteria criteria = ebookExample.createCriteria();

        if (!ObjectUtils.isEmpty(req.getName())) {
            criteria.andNameLike("%" + req.getName() + "%");
        }

        PageHelper.startPage(req.getPage(), req.getSize());
        List<Ebook> ebooks = ebookMapper.selectByExample(ebookExample);

        PageInfo<Ebook> pageInfo = new PageInfo<>(ebooks);
        LOG.info("总行数: {}", pageInfo.getTotal());
        LOG.info("总页数: {}", pageInfo.getPages());

//        List<EbookResp> resp = new ArrayList<>();
//        for (Ebook ebook : ebooks) {
//            EbookResp ebookResp = new EbookResp();
//            BeanUtils.copyProperties(ebook, ebookResp);
//            resp.add(ebookResp);
//        }

        List<EbookResp> ebookResps = CopyUtil.copyList(ebooks, EbookResp.class);
        PageResp<EbookResp> resp = new PageResp<>();
        resp.setTotal(pageInfo.getTotal());
        resp.setList(ebookResps);

        return resp;
    }
}

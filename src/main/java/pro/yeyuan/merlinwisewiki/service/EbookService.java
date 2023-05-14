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
import pro.yeyuan.merlinwisewiki.req.EbookQueryReq;
import pro.yeyuan.merlinwisewiki.req.EbookSaveReq;
import pro.yeyuan.merlinwisewiki.resp.EbookQueryResp;
import pro.yeyuan.merlinwisewiki.resp.PageResp;
import pro.yeyuan.merlinwisewiki.utils.CopyUtil;
import pro.yeyuan.merlinwisewiki.utils.SnowFlake;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EbookService {

    private static final Logger LOG = LoggerFactory.getLogger(EbookService.class);

    @Resource
    private EbookMapper ebookMapper;

    @Resource
    private SnowFlake snowFlake;

    public PageResp<EbookQueryResp> list(EbookQueryReq req) {

        EbookExample ebookExample = new EbookExample();
        EbookExample.Criteria criteria = ebookExample.createCriteria();

        if (!ObjectUtils.isEmpty(req.getName())) {
            criteria.andNameLike("%" + req.getName() + "%");
        }

        if (!ObjectUtils.isEmpty(req.getCategoryId2())) {
            criteria.andCategory2IdEqualTo(req.getCategoryId2());
        }

        PageHelper.startPage(req.getPage(), req.getSize());
        List<Ebook> ebooks = ebookMapper.selectByExample(ebookExample);

        PageInfo<Ebook> pageInfo = new PageInfo<>(ebooks);
        LOG.info("总行数: {}", pageInfo.getTotal());
        LOG.info("总页数: {}", pageInfo.getPages());

//        List<EbookQueryResp> resp = new ArrayList<>();
//        for (Ebook ebook : ebooks) {
//            EbookQueryResp ebookResp = new EbookQueryResp();
//            BeanUtils.copyProperties(ebook, ebookResp);
//            resp.add(ebookResp);
//        }

        List<EbookQueryResp> ebookQueryResps = CopyUtil.copyList(ebooks, EbookQueryResp.class);
        PageResp<EbookQueryResp> resp = new PageResp<>();
        resp.setTotal(pageInfo.getTotal());
        resp.setList(ebookQueryResps);

        return resp;
    }

    public void save(EbookSaveReq req) {
        Ebook ebook = CopyUtil.copy(req, Ebook.class);
        if(ObjectUtils.isEmpty(req.getId())) {
            // 新增
            ebook.setId(snowFlake.nextId());
            ebookMapper.insert(ebook);
        } else {
            // 更新
            ebookMapper.updateByPrimaryKey(ebook);
        }
    }

    public void delete(Long id) {
        ebookMapper.deleteByPrimaryKey(id);
    }
}

package pro.yeyuan.merlinwisewiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import pro.yeyuan.merlinwisewiki.domain.Doc;
import pro.yeyuan.merlinwisewiki.domain.DocExample;
import pro.yeyuan.merlinwisewiki.mapper.DocMapper;
import pro.yeyuan.merlinwisewiki.req.DocQueryReq;
import pro.yeyuan.merlinwisewiki.req.DocSaveReq;
import pro.yeyuan.merlinwisewiki.resp.DocQueryResp;
import pro.yeyuan.merlinwisewiki.resp.PageResp;
import pro.yeyuan.merlinwisewiki.utils.CopyUtil;
import pro.yeyuan.merlinwisewiki.utils.SnowFlake;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DocService {

    private static final Logger LOG = LoggerFactory.getLogger(DocService.class);

    @Resource
    private DocMapper docMapper;

    @Resource
    private SnowFlake snowFlake;

    public List<DocQueryResp> all() {

        DocExample docExample = new DocExample();
        docExample.setOrderByClause("sort asc");
        List<Doc> docs = docMapper.selectByExample(docExample);

        return CopyUtil.copyList(docs, DocQueryResp.class);
    }

    public PageResp<DocQueryResp> list(DocQueryReq req) {

        DocExample docExample = new DocExample();
        docExample.setOrderByClause("sort asc");
        PageHelper.startPage(req.getPage(), req.getSize());
        List<Doc> docs = docMapper.selectByExample(docExample);

        PageInfo<Doc> pageInfo = new PageInfo<>(docs);
        LOG.info("总行数: {}", pageInfo.getTotal());
        LOG.info("总页数: {}", pageInfo.getPages());

//        List<DocQueryResp> resp = new ArrayList<>();
//        for (Doc doc : docs) {
//            DocQueryResp docResp = new DocQueryResp();
//            BeanUtils.copyProperties(doc, docResp);
//            resp.add(docResp);
//        }

        List<DocQueryResp> docQueryResps = CopyUtil.copyList(docs, DocQueryResp.class);
        PageResp<DocQueryResp> resp = new PageResp<>();
        resp.setTotal(pageInfo.getTotal());
        resp.setList(docQueryResps);

        return resp;
    }

    public void save(DocSaveReq req) {
        Doc doc = CopyUtil.copy(req, Doc.class);
        if(ObjectUtils.isEmpty(req.getId())) {
            // 新增
            doc.setId(snowFlake.nextId());
            docMapper.insert(doc);
        } else {
            // 更新
            docMapper.updateByPrimaryKey(doc);
        }
    }

    public void delete(Long id) {
        docMapper.deleteByPrimaryKey(id);
    }
}

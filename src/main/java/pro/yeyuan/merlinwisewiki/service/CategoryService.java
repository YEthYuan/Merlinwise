package pro.yeyuan.merlinwisewiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import pro.yeyuan.merlinwisewiki.domain.Category;
import pro.yeyuan.merlinwisewiki.domain.CategoryExample;
import pro.yeyuan.merlinwisewiki.mapper.CategoryMapper;
import pro.yeyuan.merlinwisewiki.req.CategoryQueryReq;
import pro.yeyuan.merlinwisewiki.req.CategorySaveReq;
import pro.yeyuan.merlinwisewiki.resp.CategoryQueryResp;
import pro.yeyuan.merlinwisewiki.resp.PageResp;
import pro.yeyuan.merlinwisewiki.utils.CopyUtil;
import pro.yeyuan.merlinwisewiki.utils.SnowFlake;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CategoryService {

    private static final Logger LOG = LoggerFactory.getLogger(CategoryService.class);

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private SnowFlake snowFlake;

    public List<CategoryQueryResp> all() {

        CategoryExample categoryExample = new CategoryExample();
        categoryExample.setOrderByClause("sort asc");
        List<Category> categorys = categoryMapper.selectByExample(categoryExample);

        return CopyUtil.copyList(categorys, CategoryQueryResp.class);
    }

    public PageResp<CategoryQueryResp> list(CategoryQueryReq req) {

        CategoryExample categoryExample = new CategoryExample();
        categoryExample.setOrderByClause("sort asc");
        PageHelper.startPage(req.getPage(), req.getSize());
        List<Category> categorys = categoryMapper.selectByExample(categoryExample);

        PageInfo<Category> pageInfo = new PageInfo<>(categorys);
        LOG.info("总行数: {}", pageInfo.getTotal());
        LOG.info("总页数: {}", pageInfo.getPages());

//        List<CategoryQueryResp> resp = new ArrayList<>();
//        for (Category category : categorys) {
//            CategoryQueryResp categoryResp = new CategoryQueryResp();
//            BeanUtils.copyProperties(category, categoryResp);
//            resp.add(categoryResp);
//        }

        List<CategoryQueryResp> categoryQueryResps = CopyUtil.copyList(categorys, CategoryQueryResp.class);
        PageResp<CategoryQueryResp> resp = new PageResp<>();
        resp.setTotal(pageInfo.getTotal());
        resp.setList(categoryQueryResps);

        return resp;
    }

    public void save(CategorySaveReq req) {
        Category category = CopyUtil.copy(req, Category.class);
        if(ObjectUtils.isEmpty(req.getId())) {
            // 新增
            category.setId(snowFlake.nextId());
            categoryMapper.insert(category);
        } else {
            // 更新
            categoryMapper.updateByPrimaryKey(category);
        }
    }

    public void delete(Long id) {
        categoryMapper.deleteByPrimaryKey(id);
    }
}

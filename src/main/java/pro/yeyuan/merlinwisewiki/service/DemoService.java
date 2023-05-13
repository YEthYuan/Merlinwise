package pro.yeyuan.merlinwisewiki.service;

import org.springframework.stereotype.Service;
import pro.yeyuan.merlinwisewiki.domain.Demo;
import pro.yeyuan.merlinwisewiki.mapper.DemoMapper;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DemoService {

    @Resource
    private DemoMapper demoMapper;

    public List<Demo> list() {
        return demoMapper.selectByExample(null);
    }
}

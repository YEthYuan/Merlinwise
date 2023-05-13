package pro.yeyuan.merlinwisewiki.service;

import org.springframework.stereotype.Service;
import pro.yeyuan.merlinwisewiki.domain.Test;
import pro.yeyuan.merlinwisewiki.mapper.TestMapper;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TestService {

    @Resource
    private TestMapper testMapper;

    public List<Test> list() {
        return testMapper.list();
    }
}

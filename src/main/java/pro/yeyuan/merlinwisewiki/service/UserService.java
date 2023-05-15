package pro.yeyuan.merlinwisewiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import pro.yeyuan.merlinwisewiki.domain.User;
import pro.yeyuan.merlinwisewiki.domain.UserExample;
import pro.yeyuan.merlinwisewiki.exception.BusinessException;
import pro.yeyuan.merlinwisewiki.exception.BusinessExceptionCode;
import pro.yeyuan.merlinwisewiki.mapper.UserMapper;
import pro.yeyuan.merlinwisewiki.req.UserLoginReq;
import pro.yeyuan.merlinwisewiki.req.UserQueryReq;
import pro.yeyuan.merlinwisewiki.req.UserResetPasswordReq;
import pro.yeyuan.merlinwisewiki.req.UserSaveReq;
import pro.yeyuan.merlinwisewiki.resp.UserLoginResp;
import pro.yeyuan.merlinwisewiki.resp.UserQueryResp;
import pro.yeyuan.merlinwisewiki.resp.PageResp;
import pro.yeyuan.merlinwisewiki.utils.CopyUtil;
import pro.yeyuan.merlinwisewiki.utils.SnowFlake;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    @Resource
    private UserMapper userMapper;

    @Resource
    private SnowFlake snowFlake;

    public PageResp<UserQueryResp> list(UserQueryReq req) {

        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();

        if(!ObjectUtils.isEmpty(req.getLoginName())) {
            criteria.andLoginNameEqualTo(req.getLoginName());
        }

        PageHelper.startPage(req.getPage(), req.getSize());
        List<User> users = userMapper.selectByExample(userExample);

        PageInfo<User> pageInfo = new PageInfo<>(users);
        LOG.info("总行数: {}", pageInfo.getTotal());
        LOG.info("总页数: {}", pageInfo.getPages());

//        List<UserQueryResp> resp = new ArrayList<>();
//        for (User user : users) {
//            UserQueryResp userResp = new UserQueryResp();
//            BeanUtils.copyProperties(user, userResp);
//            resp.add(userResp);
//        }

        List<UserQueryResp> userQueryResps = CopyUtil.copyList(users, UserQueryResp.class);
        PageResp<UserQueryResp> resp = new PageResp<>();
        resp.setTotal(pageInfo.getTotal());
        resp.setList(userQueryResps);

        return resp;
    }

    public void save(UserSaveReq req) {
        User user = CopyUtil.copy(req, User.class);
        if(ObjectUtils.isEmpty(req.getId())) {
            User userDb = selectByLoginName(req.getLoginName());
            if (ObjectUtils.isEmpty(userDb)) {
                // 新增
                user.setId(snowFlake.nextId());
                userMapper.insert(user);
            } else {
                // Username already exist!
                throw new BusinessException(BusinessExceptionCode.USER_LOGIN_NAME_EXIST);
            }
        } else {
            // 更新
            user.setLoginName(null);
            user.setPassword(null);
            userMapper.updateByPrimaryKeySelective(user);
        }
    }

    public void delete(Long id) {
        userMapper.deleteByPrimaryKey(id);
    }

    public User selectByLoginName(String loginName) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andLoginNameEqualTo(loginName);
        List<User> users = userMapper.selectByExample(userExample);
        if (CollectionUtils.isEmpty(users)) {
            return null;
        } else {
            return users.get(0);
        }
    }
    public void resetPassword(UserResetPasswordReq req) {
        User user = CopyUtil.copy(req, User.class);
        userMapper.updateByPrimaryKeySelective(user);
    }
    public UserLoginResp login(UserLoginReq req) {
        User userDb = selectByLoginName(req.getLoginName());
        if(ObjectUtils.isEmpty(userDb)) {
            // Username doesn't exist
            LOG.info("Username doesn't exist! {}", req.getLoginName());
            throw new BusinessException(BusinessExceptionCode.LOGIN_USER_ERROR);
        } else {
            if (userDb.getPassword().equals(req.getPassword())) {
                // Login successfully
                UserLoginResp loginResp = CopyUtil.copy(userDb, UserLoginResp.class);
                return loginResp;
            } else {
                // Password incorrect
                LOG.info("Password incorrect, input password={}, correct password={}", req.getPassword(), userDb.getPassword());
                throw new BusinessException(BusinessExceptionCode.LOGIN_USER_ERROR);
            }
        }
    }
}

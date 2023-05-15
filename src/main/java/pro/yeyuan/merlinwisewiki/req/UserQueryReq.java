package pro.yeyuan.merlinwisewiki.req;

import javax.validation.constraints.NotEmpty;

public class UserQueryReq extends PageReq {

    @NotEmpty(message = "Username shouldn't be none!")
    private String loginName;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    @Override
    public String toString() {
        return "UserQueryReq{" +
                "loginName='" + loginName + '\'' +
                "} " + super.toString();
    }
}
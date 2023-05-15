package pro.yeyuan.merlinwisewiki.req;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class UserLoginReq {

    @NotEmpty(message = "Username shouldn't be none!")
    private String loginName;

    @NotEmpty(message = "Password shouldn't be none!")
    @Pattern(regexp = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,32}$", message = "Invalid password format!")
    private String password;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserLoginReq{" +
                "loginName='" + loginName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
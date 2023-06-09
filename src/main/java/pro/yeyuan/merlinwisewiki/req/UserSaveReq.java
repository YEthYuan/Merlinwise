package pro.yeyuan.merlinwisewiki.req;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class UserSaveReq {
    private Long id;

    @NotEmpty(message = "Username shouldn't be none!")
    private String loginName;

    @NotEmpty(message = "Nickname shouldn't be none!")
    private String name;

    @NotEmpty(message = "Password shouldn't be none!")
    @Pattern(regexp = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,32}$", message = "Valid password should include both digits and characters, length between 6-32!")
    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", loginName=").append(loginName);
        sb.append(", name=").append(name);
        sb.append(", password=").append(password);
        sb.append("]");
        return sb.toString();
    }
}
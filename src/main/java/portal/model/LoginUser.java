package portal.model;

import java.io.Serializable;

public class LoginUser implements Serializable {

    private String userId;
    private String userName;
    private String department;

    public LoginUser(String userId, String userName, String department) {
        this.userId = userId;
        this.userName = userName;
        this.department = department;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getDepartment() {
        return department;
    }
}
package model.entity;

public class User {
    private int userID;
    private String userLogin;
    private String password;
    private UserRole role;


    public User() {
    }

    public User(int userID,String userLogin, String password, UserRole role,String accountId) {
        this.userID = userID;
        this.userLogin = userLogin;
        this.password = password;
        this.role = role;
    }

    public int getuserId(){
        return userID;
    }

    public void setuserId(int userID) {
        this.userLogin = userLogin;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

}

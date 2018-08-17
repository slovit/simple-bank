package view;

public interface DbConstants {
    String URL = "jdbc:mysql://localhost:3306/catalog?useSSL=false";
    String USERNAME = "AlexSLo";
    String PASSWORD = "zzz123";

    String INSERT = "insert into users (userId, userLogin, password, role)" +
            " values (?,?,?,?)";
    String UPDATE = "UPDATE users SET userLogin = ?, password = ?, userRole = ? WHERE userId = ?";
    String DELETE = "DELETE FROM users WHERE userId = ?";
}

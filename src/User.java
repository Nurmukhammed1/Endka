import java.net.ConnectException;
import java.sql.*;

public class User {

    Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres" , "postgres" , "0202");
    UserDB userdb = new UserDB();

    private String name;
    private String email;
    private String password;

    public User(String name, String email, String password) throws Exception
    {
        this.name = name;
        this.email = email;
        this.password = password;
        userdb.createUser(name, email, password, conn);
    }
}

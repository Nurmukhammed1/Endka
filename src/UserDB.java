import java.net.ConnectException;
import java.sql.*;
import java.util.Scanner;

public class UserDB
{
    public void createUser(String name, String email, String password, Connection conn) throws Exception
    {
        PreparedStatement ps = conn.prepareStatement("insert into users (name, email, password) values (?, ?, ?)");
        ps.setString(1, name);
        ps.setString(2, email);
        ps.setString(3, password);
        ps.executeUpdate();
    }

    public boolean checkUser(String email, String password, Connection conn) throws Exception
    {
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM users");

        while (rs.next())
        {
            if (rs.getString("email").equals(email) && rs.getString("password").equals(password))
            {
                System.out.println("Success! Welcome back!");
                return true;
            }
        }

        System.out.println("Incorrect email or password! Try again.");
        return false;
    }

}

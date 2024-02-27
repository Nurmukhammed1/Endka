import java.net.ConnectException;
import java.sql.*;

public class UserDB
{
    public void createUser(String name, String email, String password, Connection conn) throws Exception
    {
        PreparedStatement ps = conn.prepareStatement("INSERT INTO users VALUES (?, ?, ?)");
        ps.setString(1, name);
        ps.setString(2, email);
        ps.setString(3, password);
        ps.executeUpdate();
    }

    public boolean chechUser(String email, String password, Connection conn) throws Exception
    {
        Statement statement = conn.createStatement();
        ResultSet result = statement.executeQuery("SELECT * FROM users");

        String emailFromDB = "";
        String passwordFromDB = "";

        while (result.next())
        {
            emailFromDB = result.getString("email");
            passwordFromDB = result.getString("password");

            if (emailFromDB.equals(email) && passwordFromDB.equals(password))
            {
                return true;
            }
            else
            {
                System.out.println("Error");
                return false;
            }
        }

        System.out.println("DD Error");
        return false;
    }

}

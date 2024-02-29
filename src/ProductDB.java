import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class ProductDB {
    public static void showProducts(String typeOfProduct, Connection conn) throws Exception
    {
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM products");

        while (rs.next())
        {
            if (rs.getString("type").equals(typeOfProduct) == true)
            {
                System.out.println(rs.getString("name") + ' ' + rs.getString("cost")+'T'
                        + ' ' + rs.getString("type") + ' ' + rs.getString("description") + ' ' + rs.getString("seller"));
            }
        }

        System.out.println();
    }

    public static void addItem(Connection con) throws Exception
    {
        Scanner scanner = new Scanner(System.in);
        PreparedStatement pst = con.prepareStatement("insert into products (name, type, description, seller, cost) values (?,?,?,?,?)");

        System.out.println("Enter name of item: ");
        String name = scanner.nextLine();

        System.out.println("Enter type of item: ");
        String type = scanner.nextLine();

        System.out.println("Enter description of item: ");
        String description = scanner.nextLine();

        System.out.println("Enter contact information: ");
        String seller = scanner.nextLine();

        System.out.println("Enter cost of item: ");
        int cost = scanner.nextInt();

        pst.setString(1, name);
        pst.setString(2, type);
        pst.setString(3, description);
        pst.setString(4, seller);
        pst.setInt(5, cost);
        pst.executeUpdate();

        System.out.println("Success!");
        System.out.println();
    }

    public static int giveCost(String name_product, Connection conn) throws Exception
    {
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM products");

        while (rs.next())
        {
            if (rs.getString("name").equals(name_product))
            {
                return rs.getInt("cost");
            }
        }

        return 0;
    }

    public static void delItem(String name_product , Connection con) throws Exception
    {
        PreparedStatement pst = con.prepareStatement("delete from products where name = ?;");
        pst.setString(1, name_product);
        pst.executeUpdate();
        System.out.println("Success!");
        System.out.println();
    }
}

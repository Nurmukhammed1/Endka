import Payment.PaymentByCreditCard;
import Payment.PaymentByPayPal;
import Payment.PaymentService;

import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception
    {
        Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres" , "postgres" , "0202");

        UserDB userdb = new UserDB();
        PaymentService paymentService = new PaymentService();
        PaymentByCreditCard paymentByCreditCard;
        PaymentByPayPal paymentByPayPal;

        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome to e-commerce platform for gamers!");
        String name_product;
        int ch;

        while (true) {
            System.out.println("1. Sign Up \n" +
                               "2. Log in \n" +
                               "3. Exit");

            System.out.println("Enter your choice: ");
            ch = sc.nextInt();
            if (ch == 1)
            {
                System.out.println("Enter your name: ");
                String name = sc.next();
                System.out.println("Enter your email: ");
                String email = sc.next();
                System.out.println("Enter new password: ");
                String password = sc.next();

                userdb.createUser(name, email, password, conn);
                System.out.println("Success!");
                continue;
            }
            else if (ch == 2)
            {
                System.out.println("Enter your email: ");
                String email = sc.next();
                System.out.println("Enter password: ");
                String password = sc.next();

                while (userdb.checkUser(email, password, conn))
                {
                    System.out.println("1. Buy product \n" +
                            "2. Sell product \n" +
                            "3. Log out");

                    System.out.println("Enter your choice: ");
                    ch = sc.nextInt();

                    if (ch == 1)
                    {
                        System.out.println("1. Disks \n" +
                                "2. PC accessories \n" +
                                "3. Consoles");

                        System.out.println("Enter your choice: ");
                        ch = sc.nextInt();

                        if (ch == 1) {
                            showProducts("Disk", conn);
                        }
                        else if (ch == 2) {
                            showProducts("PC accessory", conn);
                        }
                        else {
                            showProducts("Console", conn);
                        }

                        System.out.println("Enter name of product that you want to bay:");
                        name_product = sc.next();

                        System.out.println("1. Pay by credit card \n" +
                                           "2. Pay by Paypal");

                        System.out.println("Choose payment method: ");
                        ch = sc.nextInt();

                        System.out.println("Include delivery? (yes = 1 | no = 0)");
                        ch = sc.nextInt();
                        if (ch == 1){ paymentService.setIncludeDelivery(true); }
                        else { paymentService.setIncludeDelivery(false); }

                        if (ch == 1)
                        {
                            paymentByCreditCard = new PaymentByCreditCard();
                            paymentService.setStrategy(paymentByCreditCard);
                            paymentService.processOrder(giveCost(name_product, conn));
                            delItem(name_product, conn);
                        }
                        else
                        {
                            paymentByPayPal = new PaymentByPayPal();
                            paymentService.setStrategy(paymentByPayPal);
                            paymentService.processOrder(giveCost(name_product, conn));
                            delItem(name_product, conn);
                        }


                    }
                    else if (ch == 2)
                    {
                        addItem(conn);
                        continue;
                    }
                    else {
                        break;
                    }
                }

            }
            else {
                break;
            }

        }

        System.out.println("You have successfully exited the application!");

    }

    public static void showProducts(String typeOfProduct, Connection conn) throws Exception
    {
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM products");

        while (rs.next())
        {
            if (rs.getString("type").equals(typeOfProduct) == true)
            {
                System.out.println(rs.getString("name") + ' ' + rs.getString("cost")+'T'
                        + ' ' + rs.getString("type") + ' ' + rs.getString("description") + rs.getString("seller"));
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
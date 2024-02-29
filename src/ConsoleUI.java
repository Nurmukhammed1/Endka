import Payment.PaymentByCreditCard;
import Payment.PaymentByPayPal;
import Payment.PaymentService;

import java.sql.*;
import java.util.Scanner;

public class ConsoleUI {
    public void consoleRun() throws Exception
    {
        Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres" , "postgres" , "0202");

        UserDB userdb = new UserDB();
        ProductDB productDB = new ProductDB();
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
                            productDB.showProducts("Disk", conn);
                        }
                        else if (ch == 2) {
                            productDB.showProducts("PC accessory", conn);
                        }
                        else {
                            productDB.showProducts("Console", conn);
                        }

                        System.out.println("Enter name of product that you want to bay:");
                        name_product = sc.nextLine();
                        sc.next();
                        System.out.println("Include delivery? (yes = 1 | no = 0)");
                        ch = sc.nextInt();
                        if (ch == 1){ paymentService.setIncludeDelivery(true); }
                        else { paymentService.setIncludeDelivery(false); }

                        System.out.println("1. Pay by credit card \n" +
                                "2. Pay by Paypal");

                        System.out.println("Choose payment method: ");
                        ch = sc.nextInt();

                        if (ch == 1)
                        {
                            paymentByCreditCard = new PaymentByCreditCard();
                            paymentService.setStrategy(paymentByCreditCard);
                            paymentService.processOrder(productDB.giveCost(name_product, conn));
                            productDB.delItem(name_product, conn);
                        }
                        else
                        {
                            paymentByPayPal = new PaymentByPayPal();
                            paymentService.setStrategy(paymentByPayPal);
                            paymentService.processOrder(productDB.giveCost(name_product, conn));
                            productDB.delItem(name_product, conn);
                        }


                    }
                    else if (ch == 2)
                    {
                        productDB.addItem(conn);
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
}

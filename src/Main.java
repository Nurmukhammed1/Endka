import java.util.Scanner;

public class Main
{
    public static void main(String[] args) throws Exception
    {
        Scanner scanner = new Scanner(System.in);
        PaymentService paymentService = new PaymentService();
        int ch1, ch2;

        System.out.println("Welkome to Book Store!");
        System.out.println();

        while (true)
        {
            System.out.println("1. Buy with delivery");
            System.out.println("2. Buy without delivery");
            System.out.println("3. Exit");

            System.out.println("Enter your choice: ");
            ch1 = scanner.nextInt();

            if (ch1 == 1)
            {
                paymentService.setIncludeDelivery(true);
                System.out.println("1. Credit card for payment");
                System.out.println("2. PayPal for payment");
                System.out.println("Enter your choice: ");
                ch2 = scanner.nextInt();

                switch (ch2)
                {
                    case 1:
                        paymentService.setStrategy(new PaymentByCreditCard());
                        paymentService.processOrder(100);
                        break;
                    case 2:
                        paymentService.setStrategy(new PaymentByPayPal());
                        paymentService.processOrder(100);
                        break;
                }
            }
            else if (ch1 == 2)
            {
                paymentService.setIncludeDelivery(false);
                System.out.println("1. Credit card for payment");
                System.out.println("2. PayPal for payment");
                System.out.println("Enter your choice: ");
                ch2 = scanner.nextInt();

                switch (ch2)
                {
                    case 1:
                        paymentService.setStrategy(new PaymentByCreditCard());
                        paymentService.processOrder(100);
                        break;
                    case 2:
                        paymentService.setStrategy(new PaymentByPayPal());
                        paymentService.processOrder(100);
                        break;
                }

            }
            else if (ch1 == 3)
            {
                System.out.println("You have successfully exited the application!");
                break;
            }
            else
            {
                System.out.println("Error");
            }

        }

    }


}
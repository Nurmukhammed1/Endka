package Payment;

import java.util.Scanner;

public class PaymentByPayPal implements PaymentStrategy
{
    private String email;
    private String password;
    Scanner scanner = new Scanner(System.in);

    @Override
    public void collectPaymentDetails()
    {
        System.out.println("Enter email: ");
        email = scanner.nextLine();
        System.out.println("Enter password: ");
        password = scanner.nextLine();
        System.out.println("Collecting PayPal Account Details...");
    }

    @Override
    public boolean validatePaymentDetails()
    {
        System.out.printf("Validating PayPal Info: %s | %s%n", email, password);
        return true;
    }

    @Override
    public void pay(int amount)
    {
        System.out.println("Paying using PayPal");
    }

}

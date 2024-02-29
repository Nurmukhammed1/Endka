package Payment;
import java.util.Scanner;

public class PaymentByCreditCard implements PaymentStrategy
{
    private String number;
    private String date;
    private String cvv;

    Scanner sc = new Scanner(System.in);

    @Override
    public void collectPaymentDetails()
    {
        System.out.println("Enter card number: ");
        number = sc.nextLine();

        System.out.println("Enter expiracy date: ");
        date = sc.nextLine();

        System.out.println("Enter cvv: ");
        cvv = sc.nextLine();

        System.out.println("Collecting Card Details...");
    }

    @Override
    public boolean validatePaymentDetails()
    {
        System.out.println("Validating Card Info: " + number);
        return true;
    }

    @Override
    public void pay(int amount)
    {
        System.out.println("Paying using Credit Card");
    }

}
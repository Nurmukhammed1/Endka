public class PaymentByPayPal implements PaymentStrategy
{
    private String email;
    private String password;

    @Override
    public void collectPaymentDetails()
    {
        email = "PayPal Mail";
        password = "PayPal Password";
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
        System.out.println("Paying " + amount + " using PayPal");
    }

}

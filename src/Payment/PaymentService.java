package Payment;

import Payment.PaymentByCreditCard;
import Payment.PaymentByPayPal;

public class PaymentService
{

    private int cost = 100;
    private boolean includeDelivery;

    private PaymentStrategy strategy;

    public void setIncludeDelivery(boolean includeDelivery)
    {
        this.includeDelivery = includeDelivery;
    }

    public void processOrder(int cost)
    {
        this.cost = cost;
        strategy.collectPaymentDetails();
        if (strategy.validatePaymentDetails())
        {
            strategy.pay(getTotal());
        }
    }

    private int getTotal()
    {
        return includeDelivery ? cost + 10 : cost;
    }

    public void setStrategy(PaymentByCreditCard byCard)
    {
        this.strategy = byCard;
    }

    public void setStrategy(PaymentByPayPal byPaypal)
    {
        this.strategy = byPaypal;
    }
}

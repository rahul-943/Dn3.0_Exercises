package Strategy_Pattern_Example;

public class StrategyPatternExample {
    public static void main(String[] args) {
        // Create a payment context with CreditCardPayment strategy
        PaymentStrategy creditCardPayment = new CreditCardPayment("John Doe", "1234-5678-9876-5432");
        PaymentContext paymentContext = new PaymentContext(creditCardPayment);
        paymentContext.executePayment(250.00);

        // Change strategy to PayPalPayment
        PaymentStrategy payPalPayment = new PayPalPayment("john.doe@example.com");
        paymentContext = new PaymentContext(payPalPayment);
        paymentContext.executePayment(150.00);
    }

    // Define Strategy Interface
    public interface PaymentStrategy {
        void pay(double amount);
    }

    // Implement Concrete Strategies
    public static class CreditCardPayment implements PaymentStrategy {
        private String name;
        private String cardNumber;

        public CreditCardPayment(String name, String cardNumber) {
            this.name = name;
            this.cardNumber = cardNumber;
        }

        @Override
        public void pay(double amount) {
            System.out.println("Paid " + amount + " using Credit Card.");
        }
    }

    public static class PayPalPayment implements PaymentStrategy {
        private String email;

        public PayPalPayment(String email) {
            this.email = email;
        }

        @Override
        public void pay(double amount) {
            System.out.println("Paid " + amount + " using GooglePay.");
        }
    }

    // Implement Context Class
    public static class PaymentContext {
        private PaymentStrategy paymentStrategy;

        public PaymentContext(PaymentStrategy paymentStrategy) {
            this.paymentStrategy = paymentStrategy;
        }

        public void executePayment(double amount) {
            paymentStrategy.pay(amount);
        }
    }
}


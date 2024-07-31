package Adapter_Pattern_Example;

public class AdapterPatternExample {
    public static void main(String[] args) {
        // Create PayPal adapter and process payment
        PaymentProcessor payPalProcessor = new PayPalAdapter(new PayPalPaymentGateway());
        payPalProcessor.processPayment(100.0);

        // Create Stripe adapter and process payment
        PaymentProcessor stripeProcessor = new StripeAdapter(new StripePaymentGateway());
        stripeProcessor.processPayment(200.0);
    }

    // Define the Target Interface
    public interface PaymentProcessor {
        void processPayment(double amount);
    }

    // Implement Adaptee Classes
    public static class PayPalPaymentGateway {
        public void makePayment(double amount) {
            System.out.println("Processing payment of $" + amount + " through PayPal.");
        }
    }

    public static class StripePaymentGateway {
        public void charge(double amount) {
            System.out.println("Charging $" + amount + " through Stripe.");
        }
    }

    // Implement Adapter Classes
    public static class PayPalAdapter implements PaymentProcessor {
        private PayPalPaymentGateway payPalPaymentGateway;

        public PayPalAdapter(PayPalPaymentGateway payPalPaymentGateway) {
            this.payPalPaymentGateway = payPalPaymentGateway;
        }

        @Override
        public void processPayment(double amount) {
            payPalPaymentGateway.makePayment(amount);
        }
    }

    public static class StripeAdapter implements PaymentProcessor {
        private StripePaymentGateway stripePaymentGateway;

        public StripeAdapter(StripePaymentGateway stripePaymentGateway) {
            this.stripePaymentGateway = stripePaymentGateway;
        }

        @Override
        public void processPayment(double amount) {
            stripePaymentGateway.charge(amount);
        }
    }
}


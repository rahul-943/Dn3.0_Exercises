package Decorator_Pattern_Example;

public class DecoratorPatternExample {
    public static void main(String[] args) {
        // Create an email notifier
        Notifier emailNotifier = new EmailNotifier();
        emailNotifier.send("Hello via Email!");

        // Decorate with SMS notifier
        Notifier smsNotifier = new SMSNotifierDecorator(new EmailNotifier());
        smsNotifier.send("Hello via Email and SMS!");

        // Decorate with Slack notifier
        Notifier slackNotifier = new SlackNotifierDecorator(new EmailNotifier());
        slackNotifier.send("Hello via Email and Slack!");

        // Combine decorators
        Notifier combinedNotifier = new SlackNotifierDecorator(new SMSNotifierDecorator(new EmailNotifier()));
        combinedNotifier.send("Hello via Email, SMS, and Slack!");
    }

    // Define Component Interface
    public interface Notifier {
        void send(String message);
    }

    // Implement Concrete Component
    public static class EmailNotifier implements Notifier {
        @Override
        public void send(String message) {
            System.out.println("Sending email with message: " + message);
        }
    }

    // Implement Decorator Classes
    public static abstract class NotifierDecorator implements Notifier {
        protected Notifier wrappedNotifier;

        public NotifierDecorator(Notifier notifier) {
            this.wrappedNotifier = notifier;
        }

        @Override
        public void send(String message) {
            wrappedNotifier.send(message);
        }
    }

    public static class SMSNotifierDecorator extends NotifierDecorator {
        public SMSNotifierDecorator(Notifier notifier) {
            super(notifier);
        }

        @Override
        public void send(String message) {
            super.send(message);
            sendSMS(message);
        }

        private void sendSMS(String message) {
            System.out.println("Sending SMS with message: " + message);
        }
    }

    public static class SlackNotifierDecorator extends NotifierDecorator {
        public SlackNotifierDecorator(Notifier notifier) {
            super(notifier);
        }

        @Override
        public void send(String message) {
            super.send(message);
            sendSlack(message);
        }

        private void sendSlack(String message) {
            System.out.println("Sending Slack message with message: " + message);
        }
    }
}

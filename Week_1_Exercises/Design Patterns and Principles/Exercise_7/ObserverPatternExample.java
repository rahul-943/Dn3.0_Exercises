package Observer_Pattern_Example;

import java.util.ArrayList;
import java.util.List;

public class ObserverPatternExample {
    public static void main(String[] args) {
        // Create a stock market
        StockMarket stockMarket = new StockMarket("AAPL", 150.00);

        // Create observers
        Observer mobileApp = new MobileApp();
        Observer webApp = new WebApp();

        // Register observers
        stockMarket.registerObserver(mobileApp);
        stockMarket.registerObserver(webApp);

        // Update stock price and notify observers
        stockMarket.setStockPrice(155.00);
        stockMarket.setStockPrice(160.00);

        // Deregister an observer
        stockMarket.deregisterObserver(mobileApp);

        // Update stock price and notify remaining observers
        stockMarket.setStockPrice(165.00);
    }

    // Define Subject Interface
    public interface Stock {
        void registerObserver(Observer observer);
        void deregisterObserver(Observer observer);
        void notifyObservers();
    }

    // Implement Concrete Subject
    public static class StockMarket implements Stock {
        private List<Observer> observers = new ArrayList<>();
        private String stockName;
        private double stockPrice;

        public StockMarket(String stockName, double stockPrice) {
            this.stockName = stockName;
            this.stockPrice = stockPrice;
        }

        @Override
        public void registerObserver(Observer observer) {
            observers.add(observer);
        }

        @Override
        public void deregisterObserver(Observer observer) {
            observers.remove(observer);
        }

        @Override
        public void notifyObservers() {
            for (Observer observer : observers) {
                observer.update(stockName, stockPrice);
            }
        }

        public void setStockPrice(double stockPrice) {
            this.stockPrice = stockPrice;
            notifyObservers();
        }
    }

    // Define Observer Interface
    public interface Observer {
        void update(String stockName, double stockPrice);
    }

    // Implement Concrete Observers
    public static class MobileApp implements Observer {
        @Override
        public void update(String stockName, double stockPrice) {
            System.out.println("Mobile App: Stock " + stockName + " price updated to " + stockPrice);
        }
    }

    public static class WebApp implements Observer {
        @Override
        public void update(String stockName, double stockPrice) {
            System.out.println("Web App: Stock " + stockName + " price updated to " + stockPrice);
        }
    }
}

package Proxy_Pattern_Example;

public class ProxyPatternExample {
    public static void main(String[] args) {
        Image image1 = new ProxyImage("image1.jpg");
        Image image2 = new ProxyImage("image2.jpg");

        // Image will be loaded from server and displayed
        image1.display();

        // Image will be displayed from cache (not loaded again)
        image1.display();

        // Image will be loaded from server and displayed
        image2.display();

        // Image will be displayed from cache (not loaded again)
        image2.display();
    }

    // Define Subject Interface
    public interface Image {
        void display();
    }

    // Implement Real Subject Class
    public static class RealImage implements Image {
        private String filename;

        public RealImage(String filename) {
            this.filename = filename;
            loadImageFromServer();
        }

        private void loadImageFromServer() {
            System.out.println("Loading image: " + filename);
        }

        @Override
        public void display() {
            System.out.println("Displaying image: " + filename);
        }
    }

    // Implement Proxy Class
    public static class ProxyImage implements Image {
        private RealImage realImage;
        private String filename;

        public ProxyImage(String filename) {
            this.filename = filename;
        }

        @Override
        public void display() {
            if (realImage == null) {
                realImage = new RealImage(filename);
            }
            realImage.display();
        }
    }
}


package BuilderPattern_Example;

public class BuilderPatternExample {
    public static void main(String[] args) {
        // Create a basic computer
        Computer basicComputer = new Computer.Builder("Intel i5", "8GB", "256GB SSD").build();
        System.out.println(basicComputer);

        // Create a high-end computer with graphics card and Bluetooth
        Computer highEndComputer = new Computer.Builder("Intel i7", "16GB", "1TB SSD")
                .setGraphicsCardEnabled(true)
                .setBluetoothEnabled(true)
                .build();
        System.out.println(highEndComputer);
    }
}

class Computer {
    // Required parameters
    private String CPU;
    private String RAM;
    private String storage;

    // Optional parameters
    private boolean isGraphicsCardEnabled;
    private boolean isBluetoothEnabled;

    private Computer(Builder builder) {
        this.CPU = builder.CPU;
        this.RAM = builder.RAM;
        this.storage = builder.storage;
        this.isGraphicsCardEnabled = builder.isGraphicsCardEnabled;
        this.isBluetoothEnabled = builder.isBluetoothEnabled;
    }

    public static class Builder {
        // Required parameters
        private String CPU;
        private String RAM;
        private String storage;

        // Optional parameters
        private boolean isGraphicsCardEnabled;
        private boolean isBluetoothEnabled;

        public Builder(String CPU, String RAM, String storage) {
            this.CPU = CPU;
            this.RAM = RAM;
            this.storage = storage;
        }

        public Builder setGraphicsCardEnabled(boolean isGraphicsCardEnabled) {
            this.isGraphicsCardEnabled = isGraphicsCardEnabled;
            return this;
        }

        public Builder setBluetoothEnabled(boolean isBluetoothEnabled) {
            this.isBluetoothEnabled = isBluetoothEnabled;
            return this;
        }

        public Computer build() {
            return new Computer(this);
        }
    }

    @Override
    public String toString() {
        return "Computer [CPU=" + CPU + ", RAM=" + RAM + ", storage=" + storage +
                ", isGraphicsCardEnabled=" + isGraphicsCardEnabled +
                ", isBluetoothEnabled=" + isBluetoothEnabled + "]";
    }
}


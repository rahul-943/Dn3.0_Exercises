package Command_Pattern_Example;

public class CommandPatternExample {
    public static void main(String[] args) {
        // Create the receiver
        Light light = new Light();

        // Create concrete commands
        Command lightOn = new LightOnCommand(light);
        Command lightOff = new LightOffCommand(light);

        // Create invoker
        RemoteControl remote = new RemoteControl();

        // Set and execute LightOnCommand
        remote.setCommand(lightOn);
        remote.pressButton();

        // Set and execute LightOffCommand
        remote.setCommand(lightOff);
        remote.pressButton();
    }

    // Define Command Interface
    public interface Command {
        void execute();
    }

    // Implement Receiver Class
    public static class Light {
        public void turnOn() {
            System.out.println("The light is ON");
        }

        public void turnOff() {
            System.out.println("The light is OFF");
        }
    }

    // Implement Concrete Commands
    public static class LightOnCommand implements Command {
        private Light light;

        public LightOnCommand(Light light) {
            this.light = light;
        }

        @Override
        public void execute() {
            light.turnOn();
        }
    }

    public static class LightOffCommand implements Command {
        private Light light;

        public LightOffCommand(Light light) {
            this.light = light;
        }

        @Override
        public void execute() {
            light.turnOff();
        }
    }

    // Implement Invoker Class
    public static class RemoteControl {
        private Command command;

        public RemoteControl() {
        }

        public void setCommand(Command command) {
            this.command = command;
        }

        public void pressButton() {
            command.execute();
        }
    }
}


package commands;

public abstract class IdCommand implements Command {
    protected int parseId(String value) {
        try {
            int id = Integer.parseInt(value);
            if (id < 1) return -1;
            return id;
        } catch (NumberFormatException e) {
            System.err.println("Invalid ID! Must be a number.");
            return -1;
        }
    }
}

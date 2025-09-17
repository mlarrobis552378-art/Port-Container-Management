package port;
import java.util.ArrayDeque;
import java.util.InputMismatchException;
import java.util.Scanner;

// === Container Class ===
class Container {
    private String id;
    private String description;
    private int weight;

    public Container(String id, String description, int weight) {
        this.id = id;
        this.description = description;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return id + " | " + description + " | " + weight + "kg";
    }
}

// === Ship Class ===
class Ship {
    private String name;
    private String captain;

    public Ship(String name, String captain) {
        this.name = name;
        this.captain = captain;
    }

    @Override
    public String toString() {
        return name + " | Capt. " + captain;
    }
}

// === Main Port Management System ===
public class PortContainerManagementSystem {

    private static ArrayDeque<Container> containerStack = new ArrayDeque<>();
    private static ArrayDeque<Ship> shipQueue = new ArrayDeque<>();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;

        do {
            showMenu();
            choice = getIntInput("Enter choice: ");

            switch (choice) {
                case 1: storeContainer(); break;
                case 2: viewContainers(); break;
                case 3: registerShip(); break;
                case 4: viewShips(); break;
                case 5: loadNextShip(); break;
                case 0: System.out.println("Exiting system..."); break;
                default: System.out.println("Invalid choice, try again.");
            }
        } while (choice != 0);
    }

    private static void showMenu() {
        System.out.println("\n=== Port Container Management System ===");
        System.out.println("[1] Store container (push)");
        System.out.println("[2] View port containers");
        System.out.println("[3] Register arriving ship (enqueue)");
        System.out.println("[4] View waiting ships");
        System.out.println("[5] Load next ship");
        System.out.println("[0] Exit");
    }

    // === Safe Integer Input ===
    private static int getIntInput(String prompt) {
        int value;
        while (true) {
            try {
                System.out.print(prompt);
                value = Integer.parseInt(sc.nextLine().trim());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    // === [1] Store container ===
    private static void storeContainer() {
        System.out.print("Enter container ID: ");
        String id = sc.nextLine().trim();
        while (id.isEmpty()) {
            System.out.print("ID cannot be empty. Enter container ID: ");
            id = sc.nextLine().trim();
        }

        System.out.print("Enter description: ");
        String description = sc.nextLine().trim();
        while (description.isEmpty()) {
            System.out.print("Description cannot be empty. Enter description: ");
            description = sc.nextLine().trim();
        }

        int weight = getIntInput("Enter weight (kg): ");

        Container container = new Container(id, description, weight);
        containerStack.push(container);
        System.out.println("Stored: " + container);
    }

    // === [2] View containers ===
    private static void viewContainers() {
        if (containerStack.isEmpty()) {
            System.out.println("No containers at the port.");
            return;
        }

        System.out.println("TOP →");
        for (Container c : containerStack) {
            System.out.println("  " + c);
        }
        System.out.println("← BOTTOM");
    }

    // === [3] Register ship ===
    private static void registerShip() {
        System.out.print("Enter ship name: ");
        String name = sc.nextLine().trim();
        while (name.isEmpty()) {
            System.out.print("Ship name cannot be empty. Enter ship name: ");
            name = sc.nextLine().trim();
        }

        System.out.print("Enter captain's name: ");
        String captain = sc.nextLine().trim();
        while (captain.isEmpty()) {
            System.out.print("Captain name cannot be empty. Enter captain's name: ");
            captain = sc.nextLine().trim();
        }

        Ship ship = new Ship(name, captain);
        shipQueue.offer(ship);
        System.out.println("Registered: " + ship);
    }

    // === [4] View ships ===
    private static void viewShips() {
        if (shipQueue.isEmpty()) {
            System.out.println("No ships waiting.");
            return;
        }

        System.out.println("FRONT →");
        for (Ship s : shipQueue) {
            System.out.println("  " + s);
        }
        System.out.println("← REAR");
    }

    // === [5] Load next ship ===
    private static void loadNextShip() {
        if (containerStack.isEmpty()) {
            System.out.println("No containers available to load.");
            return;
        }
        if (shipQueue.isEmpty()) {
            System.out.println("No ships waiting to be loaded.");
            return;
        }

        Container container = containerStack.pop();
        Ship ship = shipQueue.poll();

        System.out.println("Loaded: " + container + " → " + ship);
        System.out.println("Remaining containers: " + containerStack.size());
        System.out.println("Remaining ships waiting: " + shipQueue.size());
    }
}
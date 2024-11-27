import java.util.Scanner;

public class SerenitySuitesHotelManagementSystem {
    private Room[] rooms = new Room[15];
    private Booking[] bookings = new Booking[10];
    private Scanner scanner = new Scanner(System.in);
    private int bookingCount = 0;
    private boolean isFirstRun = true;

    public SerenitySuitesHotelManagementSystem() {
        for (int i = 0; i < rooms.length; i++) {
            int roomNumber = 101 + i;
            String type = (roomNumber % 3 == 0) ? "Suite" : (roomNumber % 2 == 0) ? "Double" : "Single";
            rooms[i] = new Room(roomNumber, type);
        }
    }

    public void bookRoom() {
        System.out.print("Enter customer name: ");
        String name = scanner.nextLine();
        System.out.print("Enter contact: ");
        String contact = scanner.nextLine();
        System.out.print("Enter address: ");
        String address = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        System.out.println("Available rooms:");
        for (Room room : rooms) {
            if (room.isAvailable()) {
                System.out.println(room);
            }
        }

        Room[] bookedRooms = new Room[5];
        double totalAmount = 0;
        int roomCount = 0;

        while (true) {
            System.out.print("Enter room number to book (or 0 to finish): ");
            int roomNumber = scanner.nextInt();
            if (roomNumber == 0) break;

            Room room = null;
            for (Room r : rooms) {
                if (r.getRoomNumber() == roomNumber && r.isAvailable()) {
                    room = r;
                    break;
                }
            }

            if (room != null) {
                bookedRooms[roomCount++] = room;
                room.setAvailable(false);
                totalAmount += room.getPrice();
                System.out.println("Room " + roomNumber + " booked successfully.");
            } else {
                System.out.println("Room not available or invalid room number.");
            }
        }

        if (roomCount > 0) {
            bookings[bookingCount++] = new Booking(bookedRooms, new Customer(name, contact, address, email));
            System.out.println("\nBooking Complete!");
            System.out.println("Customer Details: " + name + ", Contact: " + contact + ", Address: " + address + ", Email: " + email);
            System.out.println("Booked Rooms: ");
            for (int i = 0; i < roomCount; i++) {
                System.out.println("Room " + bookedRooms[i].getRoomNumber() + " (" + bookedRooms[i].getType() + ") - Price: " + bookedRooms[i].getPrice());
            }
            System.out.println("Total MRP: " + totalAmount);
        } else {
            System.out.println("No rooms booked. Please book at least one room.");
        }
        scanner.nextLine();
    }

    public void viewAvailableRooms() {
        System.out.println("Available rooms:");
        for (Room room : rooms) {
            if (room.isAvailable()) {
                System.out.println(room);
            }
        }
    }

    public void cancelBooking() {
        System.out.print("Enter customer name to cancel booking: ");
        String name = scanner.nextLine();
        boolean bookingFound = false;

        for (int i = 0; i < bookingCount; i++) {
            if (bookings[i].getCustomer().getName().equalsIgnoreCase(name)) {
                for (Room room : bookings[i].getRooms()) {
                    if (room != null) {
                        room.setAvailable(true);
                    }
                }
                System.out.println("Booking canceled.");
                bookings[i] = null;
                bookingFound = true;
                break;
            }
        }

        if (!bookingFound) {
            System.out.println("Booking not found for customer: " + name);
        }
    }

    public void searchBookingByCustomerName() {
        System.out.print("Enter customer name: ");
        String name = scanner.nextLine();
        boolean bookingFound = false;

        for (int i = 0; i < bookingCount; i++) {
            if (bookings[i].getCustomer().getName().equalsIgnoreCase(name)) {
                System.out.println(bookings[i]);
                bookingFound = true;
            }
        }

        if (!bookingFound) {
            System.out.println("No booking found for customer: " + name);
        }
    }

    public void updateCustomerDetails() {
        System.out.print("Enter customer name to update details: ");
        String name = scanner.nextLine();
        boolean customerFound = false;

        for (int i = 0; i < bookingCount; i++) {
            if (bookings[i].getCustomer().getName().equalsIgnoreCase(name)) {
                Customer customer = bookings[i].getCustomer();
                System.out.print("New contact: ");
                customer.setContact(scanner.nextLine());
                System.out.print("New address: ");
                customer.setAddress(scanner.nextLine());
                System.out.print("New email: ");
                customer.setEmail(scanner.nextLine());
                System.out.println("Details updated.");
                customerFound = true;
                break;
            }
        }

        if (!customerFound) {
            System.out.println("Booking not found.");
        }
    }

    public void viewRoomDetails() {
        System.out.print("Enter room number: ");
        int roomNumber = scanner.nextInt();
        scanner.nextLine();
        boolean roomFound = false;

        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                System.out.println(room);
                roomFound = true;
                break;
            }
        }

        if (!roomFound) {
            System.out.println("Room not found.");
        }
    }

    public void updateRoomType() {
        System.out.print("Enter room number: ");
        int roomNumber = scanner.nextInt();
        scanner.nextLine();
        boolean roomFound = false;

        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                System.out.print("New room type: ");
                room.setType(scanner.nextLine());
                System.out.println("Room type updated.");
                roomFound = true;
                break;
            }
        }

        if (!roomFound) {
            System.out.println("Room not found.");
        }
    }

    public void generateInvoice() {
        System.out.print("Enter customer name for invoice: ");
        String name = scanner.nextLine();

        for (int i = 0; i < bookingCount; i++) {
            if (bookings[i].getCustomer().getName().equalsIgnoreCase(name)) {
                System.out.println("\nINVOICE for Serenity Suites Hotel\n" + bookings[i]);
                return;
            }
        }

        System.out.println("Booking not found for customer: " + name);
    }

    public void listAllRooms() {
        for (Room room : rooms) {
            System.out.println(room);
        }
    }

    public static void main(String[] args) {
        SerenitySuitesHotelManagementSystem hms = new SerenitySuitesHotelManagementSystem();
        Scanner scanner = new Scanner(System.in);
        int choice;

        if (hms.isFirstRun) {
            System.out.println("Welcome to Serenity Suites Hotel");
            hms.isFirstRun = false;
        }

        do {
            System.out.println("\n1. Book Room\n2. View Available Rooms\n3. Cancel Booking\n4. Search Booking by Customer\n5. Update Customer Details\n6. View Room Details\n7. Update Room Type\n8. Generate Invoice\n9. List All Rooms\n10. Exit");
            System.out.print("Choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1: hms.bookRoom(); break;
                case 2: hms.viewAvailableRooms(); break;
                case 3: hms.cancelBooking(); break;
                case 4: hms.searchBookingByCustomerName(); break;
                case 5: hms.updateCustomerDetails(); break;
                case 6: hms.viewRoomDetails(); break;
                case 7: hms.updateRoomType(); break;
                case 8: hms.generateInvoice(); break;
                case 9: hms.listAllRooms(); break;
                case 10: 
                    System.out.println("Thank you for visiting Serenity Suites Hotel!"); 
                    break;
                default: System.out.println("Invalid choice.");
            }
        } while (choice != 10);
        scanner.close();
    }
}

class Room {
    private int roomNumber;
    private String type;
    private boolean available;
    private double price;

    public Room(int roomNumber, String type) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.available = true;
        switch (type) {
            case "Single":
                this.price = 1000;
                break;
            case "Double":
                this.price = 1700;
                break;
            case "Suite":
                this.price = 3000;
                break;
            default:
                this.price = 0;
        }
    }

    public int getRoomNumber() { return roomNumber; }
    public String getType() { return type; }
    public boolean isAvailable() { return available; }
    public double getPrice() { return price; }

    public void setAvailable(boolean available) { this.available = available; }
    public void setType(String type) { this.type = type; }

    @Override
    public String toString() {
        return "Room " + roomNumber + " (" + type + ") - " + (available ? "Available" : "Booked");
    }
}

class Customer {
    private String name;
    private String contact;
    private String address;
    private String email;

    public Customer(String name, String contact, String address, String email) {
        this.name = name;
        this.contact = contact;
        this.address = address;
        this.email = email;
    }

    public String getName() { return name; }
    public String getContact() { return contact; }
    public String getAddress() { return address; }
    public String getEmail() { return email; }

    public void setContact(String contact) { this.contact = contact; }
    public void setAddress(String address) { this.address = address; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString() {
        return name + " (" + contact + ", " + address + ", " + email + ")";
    }
}

class Booking {
    private Room[] rooms;
    private Customer customer;

    public Booking(Room[] rooms, Customer customer) {
        this.rooms = rooms;
        this.customer = customer;
    }

    public Room[] getRooms() { return rooms; }
    public Customer getCustomer() { return customer; }

    @Override
    public String toString() {
        StringBuilder details = new StringBuilder("Customer: " + customer + "\nRooms: ");
        for (Room room : rooms) {
            if (room != null) details.append(room.getRoomNumber()).append(" ");
        }
        return details.toString();
    }
}

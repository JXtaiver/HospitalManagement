import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class ApScheduling {
    private static class Appointment {
        String name;
        String doctorId;
        String timeSlot;

        public Appointment(String name, String doctorId, String timeSlot) {
            this.name = name;
            this.doctorId = doctorId;
            this.timeSlot = timeSlot;
        }
    }

    private Appointment[] normalQueue;
    private Queue<Appointment> emergencyQueue;
    private int front;
    private int back;
    private int size;
    private int capacity;

    public ApScheduling(int capacity) {
        this.capacity = capacity;
        this.normalQueue = new Appointment[capacity];
        this.emergencyQueue = new LinkedList<>();
        this.front = 0;
        this.back = -1;
        this.size = 0;
    }

    // Book normal appointment
    public void book(String name, String doctorId, String timeSlot) {
        if (size == capacity) {
            System.out.println("No openings. Cannot book more normal appointments.");
            return;
        }
        back = (back + 1) % capacity;
        normalQueue[back] = new Appointment(name, doctorId, timeSlot);
        size++;
        System.out.println("Appointment booked for: " + name);
    }

    // Book emergency appointment
    public void bookEmergency(String name, String doctorId, String timeSlot) {
        emergencyQueue.add(new Appointment(name, doctorId, timeSlot));
        System.out.println("EMERGENCY appointment booked for: " + name);
    }

    // Cancel the earliest appointment
    public void cancel() {
        if (!emergencyQueue.isEmpty()) {
            Appointment a = emergencyQueue.poll();
            System.out.println("Cancelled EMERGENCY appointment for: " + a.name);
            return;
        }
        if (size == 0) {
            System.out.println("No appointments to cancel.");
            return;
        }
        Appointment a = normalQueue[front];
        normalQueue[front] = null;
        front = (front + 1) % capacity;
        size--;
        System.out.println("Cancelled appointment for: " + a.name);
    }

    // Cancel by name 
    public void cancelByName(String name) {

        for (Appointment a : emergencyQueue) {
            if (a.name.equals(name)) {
                emergencyQueue.remove(a);
                System.out.println("Cancelled EMERGENCY appointment for: " + name);
                return;
            }
        }

        // normal queue
        int index = -1;
        for (int i = 0; i < size; i++) {
            int current = (front + i) % capacity;
            if (normalQueue[current] != null && normalQueue[current].name.equals(name)) {
                index = current;
                break;
            }
        }

        if (index == -1) {
            System.out.println("Appointment for " + name + " not found.");
            return;
        }

        for (int i = index; i != back; i = (i + 1) % capacity) {
            int next = (i + 1) % capacity;
            normalQueue[i] = normalQueue[next];
        }
        normalQueue[back] = null;
        back = (back - 1 + capacity) % capacity;
        size--;

        System.out.println("Cancelled appointment for: " + name);
    }

    // priority queue
    public void showAppointments() {
        if (emergencyQueue.isEmpty() && size == 0) {
            System.out.println("No appointments scheduled.");
            return;
        }

        System.out.println("Current appointments:");

        int count = 1;
        for (Appointment a : emergencyQueue) {
            System.out.println(count++ + ". [EMERGENCY] " + a.name +
                " with Doctor ID: " + a.doctorId + " at " + a.timeSlot);
        }

        for (int i = 0; i < size; i++) {
            int index = (front + i) % capacity;
            Appointment a = normalQueue[index];
            System.out.println(count++ + ". " + a.name +
                " with Doctor ID: " + a.doctorId + " at " + a.timeSlot);
        }
    }
     static Scanner scan = new Scanner(System.in);

     public void ApScheduling_menu() {
        while (true) {
            System.out.println("\nAppointment Scheduling Menu:");
            System.out.println("1. Book appointment");
            System.out.println("2. Show current appointments");
            System.out.println("3. Cancel appointment by earliest");
            System.out.println("4. Cancel appointment by name");
            System.out.println("5. Return to main menu");
            System.out.print("Enter your choice: ");

            int choice = scan.nextInt();
            scan.nextLine(); // Consume newline
    
            switch (choice) {
                case 1:
                System.out.print("Enter patient name: ");
                String name = scan.nextLine();
                System.out.print("Enter doctor ID: ");
                String doctorId = scan.nextLine();
                System.out.print("Enter time slot: ");
                String timeSlot = scan.nextLine();
                System.out.print("Is this an emergency? (yes/no): ");
                String emergency = scan.nextLine();
                
                if (emergency.equalsIgnoreCase("yes")) {
                    bookEmergency(name, doctorId, timeSlot);
                } else {
                    book(name, doctorId, timeSlot);
                }
                break;
                case 2:
                    showAppointments();
                    break;
                case 3:
                    cancel();
                    break;
                case 4:
                    System.out.print("Enter the patient's name to cancel: ");
                    String cancelName = scan.nextLine();
                    cancelByName(cancelName);

                    break;
                case 5:
                    System.out.println("Returning to main menu...");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
                    break;
            }
        }
    }
}    


import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.HashMap;

public class ApScheduling {
    private static class Appointment {
        int patientId;
        int doctorId;
        String timeSlot;

        public Appointment(int patientId, int doctorId, String timeSlot) {
            this.patientId = patientId;
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
    private HashMap<Integer, DoctorInfo> doctors;

    public ApScheduling(int capacity, HashMap<Integer, DoctorInfo> doctors) {
        this.capacity = capacity;
        this.normalQueue = new Appointment[capacity];
        this.emergencyQueue = new LinkedList<>();
        this.front = 0;
        this.back = -1;
        this.size = 0;
        this.doctors = doctors;
    }

    private boolean hasAppointment(int patientId) {
        for (Appointment a : emergencyQueue) {
            if (a.patientId == patientId) return true;
        }
        for (int i = 0; i < size; i++) {
            int index = (front + i) % capacity;
            if (normalQueue[index].patientId == patientId) return true;
        }
        return false;
    }

    private boolean isTimeSlotTaken(String timeSlot) {
        for (int i = 0; i < size; i++) {
            int index = (front + i) % capacity;
            if (normalQueue[index].timeSlot != null && normalQueue[index].timeSlot.equals(timeSlot)) {
                return true;
            }
        }
        return false;
    }

    private boolean isDoctorBooked(int doctorId, String timeSlot) {
        for (int i = 0; i < size; i++) {
            int index = (front + i) % capacity;
            Appointment a = normalQueue[index];
            if (a.doctorId == doctorId && a.timeSlot.equals(timeSlot)) {
                return true;
            }
        }
        return false;
    }

    public void book(int patientId, int doctorId, String timeSlot,HashMap<Integer, DoctorInfo> doctors) {
        if (!doctors.containsKey(doctorId)) {
            System.out.println("Doctor ID not found.");
            return;
        }
        
        if (hasAppointment(patientId)) {
            System.out.println("Patient already has an appointment.");
            return;
        }
        
        if (isTimeSlotTaken(timeSlot)) {
            System.out.println("Time slot already taken. Choose another.");
            return;
        }
        
        if (isDoctorBooked(doctorId, timeSlot)) {
            System.out.println("Doctor is already booked for this time slot.");
            return;
        }

        DoctorInfo doc = doctors.get(doctorId);
        boolean available = false;
        for (String slot : doc.availability) {
            if (slot.equals(timeSlot)) {
                available = true;
                break;
            }
        }
        if (!available) {
            System.out.println("Doctor is not available at this time slot.");
            return;
        }

        if (size == capacity) {
            System.out.println("No openings. Cannot book more normal appointments.");
            return;
        }
        
        back = (back + 1) % capacity;
        normalQueue[back] = new Appointment(patientId, doctorId, timeSlot);
        size++;
        System.out.println("Appointment booked for Patient ID: " + patientId);
    }

    public void bookEmergency(int patientId, int doctorId) {
        if (!doctors.containsKey(doctorId)) {
            System.out.println("Doctor ID not found.");
            return;
        }

        if (hasAppointment(patientId)) {
            System.out.println("Patient already has an appointment.");
            return;
        }
        emergencyQueue.add(new Appointment(patientId, doctorId, "EMERGENCY"));
        System.out.println("EMERGENCY appointment booked for Patient ID: " + patientId);
    }

    public void cancel() {
        if (!emergencyQueue.isEmpty()) {
            Appointment a = emergencyQueue.poll();
            System.out.println("Cancelled EMERGENCY appointment for Patient ID: " + a.patientId);
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
        System.out.println("Cancelled appointment for Patient ID: " + a.patientId);
    }

    public void cancelByPatientId(int patientId) {
        for (Appointment a : emergencyQueue) {
            if (a.patientId == patientId) {
                emergencyQueue.remove(a);
                System.out.println("Cancelled EMERGENCY appointment for Patient ID: " + patientId);
                return;
            }
        }

        int index = -1;
        for (int i = 0; i < size; i++) {
            int current = (front + i) % capacity;
            if (normalQueue[current] != null && normalQueue[current].patientId == patientId) {
                index = current;
                break;
            }
        }

        if (index == -1) {
            System.out.println("Appointment for Patient ID: " + patientId + " not found.");
            return;
        }

        for (int i = index; i != back; i = (i + 1) % capacity) {
            int next = (i + 1) % capacity;
            normalQueue[i] = normalQueue[next];
        }
        normalQueue[back] = null;
        back = (back - 1 + capacity) % capacity;
        size--;

        System.out.println("Cancelled appointment for Patient ID: " + patientId);
    }

    public void showAppointments() {
        if (emergencyQueue.isEmpty() && size == 0) {
            System.out.println("No appointments scheduled.");
            return;
        }

        System.out.println("Current appointments:");
        int count = 1;
        for (Appointment a : emergencyQueue) {
            System.out.println(count++ + ". [EMERGENCY] Patient ID: " + a.patientId +
                " with Doctor ID: " + a.doctorId);
        }

        for (int i = 0; i < size; i++) {
            int index = (front + i) % capacity;
            Appointment a = normalQueue[index];
            System.out.println(count++ + ". Patient ID: " + a.patientId +
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
            System.out.println("4. Cancel appointment by patient ID");
            System.out.println("5. Return to main menu");
            System.out.print("Enter your choice: ");

            int choice = scan.nextInt();
            scan.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter patient ID: ");
                    int pid = scan.nextInt();
                    scan.nextLine();
                    System.out.print("Enter doctor ID: ");
                    int doctorId = scan.nextInt();
                    scan.nextLine();
                    System.out.print("Enter time slot: ");
                    String timeSlot = scan.nextLine();
                    System.out.print("Is this an emergency? (yes/no): ");
                    String emergency = scan.nextLine();

                    if (emergency.equalsIgnoreCase("yes")) {
                        bookEmergency(pid, doctorId);
                    } else {
                        book(pid, doctorId, timeSlot,doctors);
                    }
                    break;
                case 2:
                    showAppointments();
                    break;
                case 3:
                    cancel();
                    break;
                case 4:
                    System.out.print("Enter the patient ID to cancel: ");
                    int cancelId = scan.nextInt();
                    cancelByPatientId(cancelId);
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

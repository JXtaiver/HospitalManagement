import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;


public class ApScheduling {
    private static class Appointment implements Comparable<Appointment> {
        int patientId;
        int doctorId;
        String timeSlot;
        int severityLevel;

        public Appointment(int patientId, int doctorId, String timeSlot) {
            this(patientId, doctorId, timeSlot, 0);
        }

        public Appointment(int patientId, int doctorId, String timeSlot, int severityLevel) {
            this.patientId = patientId;
            this.doctorId = doctorId;
            this.timeSlot = timeSlot;
            this.severityLevel = severityLevel;
        }

        @Override
        public int compareTo(Appointment other) {
            return Integer.compare(other.severityLevel, this.severityLevel); // Higher severity first
        }
    }

    private Appointment[] normalQueue;
    private PriorityQueue<Appointment> emergencyQueue;
    private int front;
    private int back;
    private int size;
    private int capacity;
    private HashMap<Integer, DoctorInfo> doctors;
    private ArrayList<PatientRecords> patients;
    private App app;

    public ApScheduling(int capacity, HashMap<Integer, DoctorInfo> doctors, ArrayList<PatientRecords> patients, App app) {
        this.capacity = capacity;
        this.normalQueue = new Appointment[capacity];
        this.emergencyQueue = new PriorityQueue<>();
        this.front = 0;
        this.back = -1;
        this.size = 0;
        this.doctors = doctors;
        this.patients = patients;
        this.app = app;
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

    private boolean isValidPatientId(int id) {
        for (PatientRecords p : patients) {
            if (p.getID() == id) {
                return true;
            }
        }
        return false;
    }

    public void book(int patientId, int doctorId, String timeSlot) {
        if (!doctors.containsKey(doctorId)) {
            System.out.println("Doctor ID not found.");
            return;
        }
        if (!isValidPatientId(patientId)) {
            System.out.println("Patient ID not found.");
            return;
        }
        if (hasAppointment(patientId)) {
            System.out.println("Patient already has an appointment.");
            return;
        }
        if (isDoctorBooked(doctorId, timeSlot)) {
            System.out.println("Doctor is already booked for this time slot.");
            return;
        }

        DoctorInfo doc = doctors.get(doctorId);
        boolean available = Arrays.asList(doc.getAvailability()).contains(timeSlot);
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

    public void bookEmergency(int patientId, int doctorId, int severityLevel) {
        if (!doctors.containsKey(doctorId)) {
            System.out.println("Doctor ID not found.");
            return;
        }
        if (!isValidPatientId(patientId)) {
            System.out.println("Patient ID not found.");
            return;
        }
        if (hasAppointment(patientId)) {
            System.out.println("Patient already has an appointment.");
            return;
        }

        emergencyQueue.add(new Appointment(patientId, doctorId, "EMERGENCY", severityLevel));
        System.out.println("EMERGENCY appointment booked for Patient ID: " + patientId + " with severity " + severityLevel);
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
        Iterator<Appointment> iterator = emergencyQueue.iterator();
        while (iterator.hasNext()) {
            Appointment a = iterator.next();
            if (a.patientId == patientId) {
                iterator.remove();
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

        PriorityQueue<Appointment> copy = new PriorityQueue<>(emergencyQueue);
        while (!copy.isEmpty()) {
            Appointment a = copy.poll();
            System.out.println(count++ + ". [EMERGENCY] Patient ID: " + a.patientId +
                    " with Doctor ID: " + a.doctorId + " | Severity: " + a.severityLevel);
        }

        for (int i = 0; i < size; i++) {
            int index = (front + i) % capacity;
            Appointment a = normalQueue[index];
            System.out.println(count++ + ". Patient ID: " + a.patientId +
                    " with Doctor ID: " + a.doctorId + " at " + a.timeSlot);
        }
    }

    static Scanner scan = new Scanner(System.in);

    public void ApScheduling_menu(ApScheduling scheduler) {
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
                    System.out.println("Doctor's availability: " + String.join(", ", doctors.get(doctorId).getAvailability()));
                    System.out.print("Enter time slot: ");
                    String timeSlot = scan.nextLine();
                    System.out.print("Is this an emergency? (yes/no): ");
                    String emergency = scan.nextLine();

                    if (emergency.trim().equalsIgnoreCase("yes")) {
                        System.out.print("Enter severity level (1-10): ");
                        int severity = scan.nextInt();
                        scan.nextLine();
                        bookEmergency(pid, doctorId, severity);
                    } else {
                        book(pid, doctorId, timeSlot);
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
                    app.Menu(patients, doctors, scheduler);
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
                    break;
            }
        }
    }
}

import java.util.LinkedList;
import java.util.Queue;

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
}


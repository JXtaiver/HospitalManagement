public class ApScheduling {
    private String[] queue;
    private int front;
    private int back;
    private int size;
    private int capacity;

    public ApScheduling(int capacity) {
        this.capacity = capacity;
        queue = new String[capacity];
        front = 0;
        back = -1;
        size = 0;
    }

    // Book
    public void book(String name) {
        if (size == capacity) {
            System.out.println("No openings. Cannot book more appointments.");
            return;
        }
        back = (back + 1) % capacity;
        queue[back] = name;
        size++;
        System.out.println("Appointment booked for: " + name);
    }

    // Cancel oldest
    public void cancel() {
        if (size == 0) {
            System.out.println("No appointments to cancel.");
            return;
        }
        System.out.println("Cancelled appointment for: " + queue[front]);
        queue[front] = null;
        front = (front + 1) % capacity;
        size--;
    }

    // Cancel person's appointment by name
    public void cancelByName(String name) {
        if (size == 0) {
            System.out.println("No appointments to cancel.");
            return;
        }

        int index = -1;
        for (int i = 0; i < size; i++) {
            int currentIndex = (front + i) % capacity;
            if (queue[currentIndex] != null && queue[currentIndex].equals(name)) {
                index = currentIndex;
                break;
            }
        }

        if (index == -1) {
            System.out.println("Appointment for " + name + " not found.");
            return;
        }

        for (int i = index; i != back; i = (i + 1) % capacity) {
            int nextIndex = (i + 1) % capacity;
            queue[i] = queue[nextIndex];
        }
        queue[back] = null;
        back = (back - 1 + capacity) % capacity;
        size--;

        System.out.println("Cancelled appointment for: " + name);
    }

    //current appointments
    public void ShowAppointments() {
        if (size == 0) {
            System.out.println("No appointments scheduled.");
            return;
        }
        System.out.println("Current appointments:");
        for (int i = 0; i < size; i++) {
            int index = (front + i) % capacity;
            System.out.println((i + 1) + ". " + queue[index]);
        }
    }
}

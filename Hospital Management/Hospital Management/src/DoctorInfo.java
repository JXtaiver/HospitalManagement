import java.util.Random;

public class DoctorInfo {
    private int doctorID;
    private String doctorName;
    private String[] availability;
    private String specialization;

    public DoctorInfo(int doctorID, String doctorName, String[] availability) {
        this.doctorID = doctorID;
        this.doctorName = doctorName;
        this.availability = availability;
        this.specialization = generateRandomSpecialization();
    }

    private String generateRandomSpecialization() {
        String[] specialties = {"Cardiology", "Neurology", "Pediatrics", "Orthopedics", "Dermatology", "Oncology", "Psychiatry"};
        Random rand = new Random();
        return specialties[rand.nextInt(specialties.length)];
    }

    public void updateAvailability(String[] newAvailability) {
        this.availability = newAvailability;
    }

    public void displayInfo() {
        System.out.println("Doctor ID: " + doctorID);
        System.out.println("Name: " + doctorName);
        System.out.println("Specialization: " + specialization);
        System.out.print("Availability: ");
        for (String time : availability) {
            System.out.print(time + " ");
        }
        System.out.println();
    }
}

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
        this.specialization = this.generateRandomSpecialization();
    }

    private String generateRandomSpecialization() {
        String[] specialties = new String[]{"Cardiology", "Neurology", "Pediatrics", "Orthopedics", "Dermatology", "Oncology", "Psychiatry"};
        Random rand = new Random();
        return specialties[rand.nextInt(specialties.length)];
    }

    public void updateAvailability(String[] newAvailability) {
        this.availability = newAvailability;
    }

    public void displayInfo() {
        System.out.println("Doctor ID: " + this.doctorID);
        System.out.println("Name: " + this.doctorName);
        System.out.println("Specialization: " + this.specialization);
        System.out.print("Availability: ");
        String[] var1 = this.availability;
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            String time = var1[var3];
            System.out.print(time + " ");
        }

        System.out.println();
    }
}


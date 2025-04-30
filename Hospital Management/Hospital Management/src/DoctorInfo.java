import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class DoctorInfo {
    private int doctorID;
    private String doctorName;
    private String[] availability;
    private String specialization;
    static Scanner scan = new Scanner(System.in);

    public DoctorInfo(int doctorID, String doctorName, String[] availability, String specialization) {
        this.doctorID = doctorID;
        this.doctorName = doctorName;
        this.availability = availability;
        this.specialization = specialization;
    }

    public String[] getAvailability() {
        return availability;
    }

    public String getSpecialization() {
        return specialization;
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

    App app = new App();
    public void Doctor_Menu(ArrayList<PatientRecords> patients, HashMap<Integer, DoctorInfo> doctors,ApScheduling Scheduler) {
        while (true) {
            System.out.println("\n Select an Option: ");
            System.out.println(" 1: Display Doctor List");
            System.out.println(" 2: Display Doctor Info");
            System.out.println(" 3: Update Availability");
            System.out.println(" 4: Exit to Main Menu");
            int type = scan.nextInt();

            switch (type) {
                case 1:
                    for (Integer id : doctors.keySet()) {
                        System.out.println("Doctor ID: " + id + ", Name: " + doctors.get(id).doctorName);
                    }
                    break;

                case 2:
                    System.out.println("Enter Doctor ID: ");
                    int docId = scan.nextInt();
                    if (doctors.containsKey(docId)) {
                        doctors.get(docId).displayInfo();
                    } else {
                        System.out.println("Doctor not found.");
                    }
                    break;

                case 3:
                    System.out.println("Enter Doctor ID: ");
                    int updateId = scan.nextInt();
                    scan.nextLine();
                    if (doctors.containsKey(updateId)) {
                        System.out.println("Enter new availability (comma-separated): ");
                        String[] newTimes = scan.nextLine().split(",");
                        for (int i = 0; i < newTimes.length; i++) {
                            newTimes[i] = newTimes[i].trim();
                        }
                        doctors.get(updateId).updateAvailability(newTimes);
                        System.out.println("Availability updated.");
                    } else {
                        System.out.println("Doctor not found.");
                    }
                    break;

                case 4:
                    System.out.println("Returning to Main Menu...");
                    app.Menu(patients, doctors,Scheduler);
                    return;

                default:
                    System.out.println("Invalid Option, Try again.");
            }
        }
    }
}

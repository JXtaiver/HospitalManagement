import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;
import java.util.HashMap;
public class App {
    Scanner scan = new Scanner(System.in);

    public static void main(String[] args) throws Exception {

        //Create patient Records Dynamic Array
        ArrayList<PatientRecords> patients = new ArrayList<>();
        Random rand = new Random();
        //Medical history prompts
        String[] H1={"Flu", "Asthma","Diabetes", "Tuberculosis","Headache", "Allergies"};



        for(int i=0; i<50; i++){
            int numDiseases = rand.nextInt(3) + 1;

            // Create a new history array for this patient
            String[] history = new String[numDiseases];

            for (int j = 0; j < numDiseases; j++) {
                // Pick a random disease from H1
                history[j] = H1[rand.nextInt(H1.length)];
            }
            patients.add(new PatientRecords(i, rand.nextInt(70)+1, "Patient"+i, history));

        }
        // Create DoctorInfo HashMap
        HashMap<Integer, DoctorInfo> doctors = new HashMap<>();
        String[] allTimes = {"9AM", "10AM", "11AM", "1PM", "2PM", "3PM"};
        String[] firstNames = {"John", "Emily", "Michael", "Sarah", "David", "Laura", "James", "Olivia"};
        String[] lastNames = {"Smith", "Johnson", "Brown", "Garcia", "Lee", "Patel", "Martinez", "Anderson"};
        String[] specializations = {"Cardiology", "Neurology", "Pediatrics", "Orthopedics", "Dermatology", "Oncology", "Psychiatry"};

        for (int i = 0; i < 50; i++) {
            String fullName = "Dr. " + firstNames[rand.nextInt(firstNames.length)] + " " + lastNames[rand.nextInt(lastNames.length)];

            int numSlots = 2 + rand.nextInt(2); // 2–3 time slots
            String[] times = new String[numSlots];
            for (int j = 0; j < numSlots; j++) {
                times[j] = allTimes[rand.nextInt(allTimes.length)];
            }

            String specialization = specializations[rand.nextInt(specializations.length)];
            doctors.put(i, new DoctorInfo(i, fullName, times, specialization));
        }

        App app = new App();
        app.Menu(patients, doctors);
    }

    public void Menu(ArrayList<PatientRecords> patients, HashMap<Integer, DoctorInfo> doctors) {
        System.out.println("Select an Option: \n 1: Patient Management. \n 2: Appointment Scheduling \n 3: Doctor Management \n 4: Exit the program");
        int val = scan.nextInt();
        if (val > 4 || val < 1) {
            System.out.println("Invalid Input, Try again.");
            Menu(patients, doctors);
        }
        switch (val) {
            case 1:
                PatientRecords patient = new PatientRecords(1029091, 30, "John Doe", new String[]{"easd"});
                patient.Patient_Menu(patients,doctors);
                break;
            case 2:
                ApScheduling scheduler = new ApScheduling(20);
                scheduler.ApScheduling_menu();
                break;
            case 3:
                doctors.get(0).Doctor_Menu(doctors); // open doctor menu using first doctor as reference
                break;
            case 4:
                System.out.println("Exiting the program...");
                System.exit(0);
        }
    }
}

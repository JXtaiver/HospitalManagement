import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;
import java.util.HashMap;

public class App {
    Scanner scan = new Scanner(System.in);
    ApScheduling scheduler;  // Declare ApScheduling object

    public static void main(String[] args) throws Exception {
        // Create patient Records Dynamic Array
        ArrayList<PatientRecords> patients = new ArrayList<>();
        Random rand = new Random();

        // Medical history prompts
        String[] H1 = {"Flu", "Asthma", "Diabetes", "Tuberculosis", "Headache", "Allergies"};

        for (int i = 0; i < 50; i++) {
            int numDiseases = rand.nextInt(3) + 1;
            String[] history = new String[numDiseases];
            for (int j = 0; j < numDiseases; j++) {
                history[j] = H1[rand.nextInt(H1.length)];
            }
            patients.add(new PatientRecords(i, rand.nextInt(70) + 1, "Patient" + i, history));
        }

        // Create DoctorInfo HashMap
        HashMap<Integer, DoctorInfo> doctors = new HashMap<>();
        String[] allTimes = {"12AM", "1AM", "2AM", "3AM", "4AM", "5AM", "6AM", "7AM", "8AM", "9AM", "10AM", "11AM", "12PM",
                             "1PM", "2PM", "3PM", "4PM", "5PM", "6PM", "7PM", "8PM", "9PM", "10PM", "11PM"};
        String[] firstNames = {"John", "Emily", "Michael", "Sarah", "David", "Laura", "James", "Olivia"};
        String[] lastNames = {"Smith", "Johnson", "Brown", "Garcia", "Lee", "Patel", "Martinez", "Anderson"};
        String[] specializations = {"Cardiology", "Neurology", "Pediatrics", "Orthopedics", "Dermatology", "Oncology", "Psychiatry"};

        for (int i = 0; i < 50; i++) {
            String fullName = "Dr. " + firstNames[rand.nextInt(firstNames.length)] + " " + lastNames[rand.nextInt(lastNames.length)];
            int numSlots = 2 + rand.nextInt(8);
            String[] times = new String[numSlots];
            for (int j = 0; j < numSlots; j++) {
                times[j] = allTimes[rand.nextInt(allTimes.length)];
            }
            String specialization = specializations[rand.nextInt(specializations.length)];
            doctors.put(i, new DoctorInfo(i, fullName, times, specialization));
        }

        // Create App instance and initialize the scheduler
        App app = new App();
        app.scheduler = new ApScheduling(20, doctors, patients, app);  

       
      
            app.Menu(patients, doctors);
    }

    public void Menu(ArrayList<PatientRecords> patients, HashMap<Integer, DoctorInfo> doctors,ApScheduling scheduler) {
        System.out.println("Select an Option: \n 1: Patient Management. \n 2: Appointment Scheduling \n 3: Doctor Management \n 4: Exit the program");
        int val = scan.nextInt();
        if (val > 4 || val < 1) {
            System.out.println("Invalid Input, Try again.");
            Menu(patients, doctors,scheduler);
        }
        switch (val) {
            case 1:
                PatientRecords patient = new PatientRecords(1029091, 30, "John Doe", new String[]{"easd"});
                patient.Patient_Menu(patients, doctors,scheduler);
                break;
            case 2:
                if (scheduler != null) {  // Ensure scheduler is not null before calling ApScheduling_menu
                    scheduler.ApScheduling_menu(scheduler);
                } else {
                    System.out.println("Scheduler is not properly initialized.");
                }
                break;
            case 3:
                if (!doctors.isEmpty()) {
                    doctors.get(0).Doctor_Menu(patients, doctors,scheduler);
                } else {
                    System.out.println("No doctors available.");
                }
                break;
            case 4:
                System.out.println("Exiting the program...");
                System.exit(0);
        }
    }
    public void Menu(ArrayList<PatientRecords> patients, HashMap<Integer, DoctorInfo> doctors) {
        System.out.println("Select an Option: \n 1: Patient Management. \n 2: Appointment Scheduling \n 3: Doctor Management \n 4: Exit the program");
        int val = scan.nextInt();
        if (val > 4 || val < 1) {
            System.out.println("Invalid Input, Try again.");
            Menu(patients, doctors,scheduler);
        }
        switch (val) {
            case 1:
                PatientRecords patient = new PatientRecords(1029091, 30, "John Doe", new String[]{"easd"});
                patient.Patient_Menu(patients, doctors,scheduler);
                break;
            case 2:
                if (scheduler != null) {  // Ensure scheduler is not null before calling ApScheduling_menu
                    scheduler.ApScheduling_menu(scheduler);
                } else {
                    System.out.println("Scheduler is not properly initialized.");
                }
                break;
            case 3:
                if (!doctors.isEmpty()) {
                    doctors.get(0).Doctor_Menu(patients, doctors,scheduler);
                } else {
                    System.out.println("No doctors available.");
                }
                break;
            case 4:
                System.out.println("Exiting the program...");
                System.exit(0);
        }
    }
}

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class App {
    Scanner scan = new Scanner(System.in);
    public void Menu(){
        System.out.println("Select an Option: \n 1: Patient Management. \n 2: Appointment Scheduling \n 3: Doctor Management \n 4: Exit the program");
        int val  = scan.nextInt();
        if(val>4 || val<1){
            System.out.println("Invalid Input, Try again.");
            Menu();
        }
        switch(val){
            case 1:
            PatientRecords patient = new PatientRecords(1029091,30,"John Doe",new String[]{"easd"});
            patient.Patient_Menu();

            break;
            case 2:

            break;
            case 3:

            break;
            case 4:
            System.out.println("Exiting the program...");
            return;
        }
    }
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
    PatientRecords Jake = new PatientRecords(51, 45, "Jake", H1);

    for(PatientRecords p : patients){
        //Just Testing to see that everything works
        p.Display_Info(p);
        
    }
    Jake.Update_Info(Jake);
    }
}

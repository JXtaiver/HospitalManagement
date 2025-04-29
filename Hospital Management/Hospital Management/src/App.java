import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner scan = new Scanner(System.in);
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
   Jake.Update_Info(Jake);
    for(PatientRecords p : patients){
        //Just Testing to see that everything works
        p.Display_Info(p);
        
    }
       
    }
}

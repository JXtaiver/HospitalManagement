import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Random;

public class PatientRecords {
    App app = new App();
    static Scanner scan = new Scanner(System.in);
    int Patient_ID;
    int Age;
    String Patient_Name;
    String[] Patient_History;
    Random rand = new Random();
    
    public PatientRecords(int ID,int Age,String Name,String[]History){
        this.Patient_History=History;
        this.Age=Age;
        this.Patient_Name=Name;
        this.Patient_ID=ID;
    }
    public void addp(ArrayList<PatientRecords> patients){
        System.out.println("Enter Name of patient");
        scan.nextLine();
        String n=scan.nextLine();
        

        System.out.println("Enter Patient History(enter 'done' to stop): )");
        ArrayList<String> histo = new ArrayList<>();
        while(true) {
            String Entry = scan.nextLine();
            if(Entry.equalsIgnoreCase("done")){
                break;
            }
            histo.add(Entry);
        }
        
        
        String[]sl = histo.toArray(new String[0]);
        int age = 0;
        System.out.println("Enter Patient Age: ");
        if(scan.hasNextInt()){
            age = scan.nextInt();
            scan.nextLine();
        }else{
            System.out.println("Invalid input, setting age to 0");
            scan.nextLine();
        }

        
       
        patients.add(new PatientRecords(patients.size(), age, n, sl));
    }
    public void setN(String Name){
        this.Patient_Name=Name;
        System.out.println("Patient name updated");
    }
    public void setA(int Age){
        this.Age=Age;
        System.out.println("Patient age updated");
    }
    public int getID(){
        return Patient_ID;
    }
    public String getN(){
        return Patient_Name;
    }
    public String[] getH(){
        return Patient_History;
    }
    public int getA(){
        return Age;
    }
    public void Update_Info(PatientRecords p){
        System.out.println("Select an Option");
        System.out.println(" 1: Set Name");
        System.out.println(" 2: Set Age");
        System.out.println(" 3: Update History");
        String New;
        int Neww;
        int type = scan.nextInt();
        scan.nextLine();
        if (type>3||type<1){
            System.out.println("Invalid number, try again.");
            Update_Info(p);
        }
        switch(type){
            case 1:
            System.out.println("Enter updated name: ");
            New= scan.nextLine();
            p.setN(New);
            break;
            case 2:
            System.out.println("Enter updated age");
            Neww=scan.nextInt();
            p.setA(Neww);
            break;
            case 3:
            Update_History(p);
            break;
        }
    }
    public void Display_Record(PatientRecords p){
        String[] arr = p.Patient_History;
        System.out.println("Patient History: ");
        System.out.println(String.join(", ",arr));
    }
    public void Display_Info(PatientRecords p){
        System.out.println("Patient ID: "+ p.getID());
        System.out.println("Patient Name: "+p.getN());
        System.out.println("Patient Age: "+p.getA());
        Display_Record(p);
    }
    public void Update_History(PatientRecords p){
        String[]hist= new String[p.Patient_History.length+1];
        for(int i = 0; i<p.Patient_History.length;i++ ){
            hist[i]=p.Patient_History[i];
        }
        System.out.println("Enter new History");
        String nh = scan.nextLine();
        hist[hist.length-1]=nh;
        System.out.println("History Updated to: "+String.join(", ",hist));
        p.Patient_History=hist;
    }
    public void Patient_Menu(ArrayList<PatientRecords> patients,HashMap<Integer, DoctorInfo> doctors,ApScheduling scheduler){
       System.out.println("\n Select an Option: \n 1: Display Patient List \n 2: Update Patient Info \n 3: Display Patient Info \n 4: Add Patient\n 5: Exit to main menu");
       int type=scan.nextInt();
       switch(type){
        case 1:
       
        for(PatientRecords p : patients){
            System.out.println("Patient ID: "+ p.getID()+ ", Name: "+p.getN());
        }
        Patient_Menu(patients,doctors,scheduler);
        break;
        case 2:
        System.out.println("Enter Patient ID: ");
        int id = scan.nextInt(); 
        
 
        if (id >= 0 && id < patients.size()) {
        
            patients.get(id).Update_Info(patients.get(id)); 
        } else {
            System.out.println("Patient not found");
        }
        Patient_Menu(patients,doctors,scheduler);
        break;
        case 3:
        System.out.println("Enter Patient ID: ");
        int io = scan.nextInt(); 
        
 
        if (io >= 0 && io < patients.size()) {
        
            patients.get(io).Display_Info(patients.get(io)); 
        } else {
            System.out.println("Patient not found");
        }
        Patient_Menu(patients,doctors,scheduler);
        break;
        case 4:
        addp(patients);
        Patient_Menu(patients,doctors,scheduler);
        break;
        case 5:
        
        System.out.println("Returning to Main Menu....");
        app.Menu(patients, doctors,scheduler);
        return;
        default:
        System.out.println("Invalid Option, Try again.");
        Patient_Menu(patients,doctors,scheduler);
       }
    }
}

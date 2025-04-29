import java.util.Scanner;

public class PatientRecords {
    static Scanner scan = new Scanner(System.in);
    int Patient_ID;
    int Age;
    String Patient_Name;
    String[] Patient_History;

    public PatientRecords(int ID,int Age,String Name,String[]History){
        this.Patient_History=History;
        this.Age=Age;
        this.Patient_Name=Name;
        this.Patient_ID=ID;
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
        System.out.println("Set Name-1");
        System.out.println("Set Age-2");
        System.out.println("Update History-3");
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
    }
}

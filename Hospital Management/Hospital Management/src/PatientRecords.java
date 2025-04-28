public class PatientRecords {
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
}

package deskundig;

public class Runner {

    public static void main(String[] args) {
        String[] sleutels = {"eerstesleutel", "tweedesleutel", "derdesleutel"};
        
        TriDes des = new TriDes(sleutels);
        des.encrypt("C:\\Users\\jeffr_000\\Desktop\\TAAK_1ProcesAnalyse2013_2014_Smets_Jeffrey.xlsx", "C:\\Users\\jeffr_000\\Desktop\\out.bin");
        des.decrypt("C:\\Users\\jeffr_000\\Desktop\\out.bin", "C:\\Users\\jeffr_000\\Desktop\\uit.xlsx");
       
    }
}
package deskundig;

public class Runner {

    public static void main(String[] args) {
        String[] sleutels = {"eerstesleutel", "tweedesleutel", "derdesleutel"};
        
        TriDes des = new TriDes(sleutels);
        des.encrypt("C:\\Users\\jeffr_000\\Desktop\\testnew.txt", "C:\\Users\\jeffr_000\\Desktop\\out.bin");
       
    }
}
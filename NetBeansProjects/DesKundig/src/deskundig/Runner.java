package deskundig;

public class Runner{
    public static void main (String[] args){
        String tekst = "Dit is een andere tekst, hihi";
        String[] sleutels = {"eerstesleutel", "tweedesleutel", "derdesleutel"};
        
        Encryptie en = new Encryptie(sleutels);
        String done = en.Encrypteer(tekst);  
        
        String decDone = en.Decrypteer(done);
        System.out.println(decDone);
        
    }
}
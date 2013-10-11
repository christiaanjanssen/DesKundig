package deskundig;

public class Runner{
    public static void main (String[] args){
        String tekst = "Dit is een andere tekst, hihi";
        String[] sleutels = {"eerstesleutel", "tweedesleutel", "derdesleutel"};
        
        Encryptie en = new Encryptie(sleutels);
        String done = en.Encrypteer(tekst);  
        System.out.println(done);
        
        Encryptie den = new Encryptie(sleutels);
        String decDone = den.Decrypteer(done);
        System.out.println(decDone);
        
    }
}
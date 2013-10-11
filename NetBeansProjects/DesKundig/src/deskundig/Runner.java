package deskundig;

public class Runner{
    public static void main (String[] args){
        String tekst = "Dit is een tekst";
        String sleutel = "eensleutel";
        
        Encryptie en = new Encryptie(tekst, sleutel);
        String done = en.Encrypteer(false);  
        System.out.println(done);
    }
}
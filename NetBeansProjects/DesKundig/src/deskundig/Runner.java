package deskundig;

public class Runner{
    public static void main (String[] args){
        String tekst = "Dit is een tekst";
        String sleutel = "eensleutel";
        
        Encryptie en = new Encryptie(tekst, sleutel);
        int[] uit = en.Encrypteer();
        for (int i = 0; i < uit.length; i++) {
            System.out.print(uit[i]);
        }
    }
}
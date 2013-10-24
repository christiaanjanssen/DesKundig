package deskundig;

import java.io.File;

public class Runner {

    public static void main(String[] args) {
        String[] sleutels = {"eerstesleutel", "tweedesleutel", "derdesleutel"};
        
        /**
         * Voor files
         */
        FileDes des = new FileDes(sleutels);
        des.encrypt(new File("C:\\Users\\jeffr_000\\Desktop\\in.txt"), new File("C:\\Users\\jeffr_000\\Desktop\\out.bin"));
        des.decrypt(new File("C:\\Users\\jeffr_000\\Desktop\\out.bin"), new File("C:\\Users\\jeffr_000\\Desktop\\uit.txt"));
        
        /**
         * Voor tekst
         */
//        EncryptieText e = new EncryptieText(sleutels);
//        String encryptResult = e.Encrypteer("abcdefghi");
//        System.out.println(encryptResult);
//        
//        String decryptResult = e.Decrypteer(encryptResult);
//        System.out.println(decryptResult);
       
    }
}
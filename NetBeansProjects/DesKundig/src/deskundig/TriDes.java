/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package deskundig;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author jeffr_000
 */
public class TriDes {

    Encryptie en;
    BinaryOut bOut;
    String sleutel;

    public TriDes(String[] sleutels) {
        en = new Encryptie(sleutels);
    }

    public void encrypt(String in, String out) {
        bOut = new BinaryOut(out);
        try {
            File file = new File(in);
            long Flength = file.length() * 8;
            FileInputStream is = new FileInputStream(file);
            byte[] chunk = new byte[8];
            String temp;
            int chunkLen = 0;
            while ((chunkLen = is.read(chunk)) != -1) {
                temp = "";
                int[] outArr = new int[64];
                for (int i = 0; i < chunk.length; i++) {
                    String bits = Integer.toBinaryString(chunk[i]);
                    for (int j = 8; j > bits.length(); j--) {
                        temp += "0";
                    }
                    temp += Integer.toBinaryString(chunk[i]);
                }
                for (int i = 0; i < temp.length(); i++) {
                    outArr[i] = Integer.parseInt(temp.substring(i,i+1));
                }
                outArr = en.Encrypteer(outArr);
                for (int i = 0; i < 64; i++) {
                    if (outArr[i] == 1) {
                        bOut.write(true);
                    } else {
                        bOut.write(false);
                    }
                }
            }

            long rest = Flength % 64;
            long addedLong = 64 - rest;
            int added = (int) addedLong;
            String toAdd = Integer.toBinaryString(added);
            temp = "";

            for (int i = 0; i < 64 - toAdd.length(); i++) {
                temp += "0";
            }

            temp += toAdd;
            for (int i = 0; i < temp.length(); i++) {
                if (temp.substring(i, i + 1).equals("1")) {
                    bOut.write(true);
                } else {
                    bOut.write(false);
                }
            }

            bOut.close();

        } catch (FileNotFoundException fnfE) {
            System.err.println("error : file not found");
        } catch (IOException ioE) {
            System.err.println("io error");
        }

    }
}

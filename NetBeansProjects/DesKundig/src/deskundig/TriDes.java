/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package deskundig;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

/**
 *
 * @author jeffr_000
 */
public class TriDes {

    Encryptie en;
    BinaryOut bOut;
    BinaryIn bIn;
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

            long rest = Flength % 64;
            long addedLong = 64 - rest;
            int added = (int) addedLong;
            if(added==64)
                added= 0;
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

            while ((chunkLen = is.read(chunk)) != -1) {
                temp = "";
                int[] outArr = new int[64];
                for (int i = 0; i < chunk.length; i++) {
                    int b = chunk[i] & 0xFF; //unsigned byte !!
                    String bits = Integer.toBinaryString(b);
                    for (int j = 8; j > bits.length(); j--) {
                        temp += "0";
                    }
                    temp += Integer.toBinaryString(b);
                }
                for (int i = 0; i < temp.length(); i++) {
                    outArr[i] = Integer.parseInt(temp.substring(i, i + 1));
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
            

            bOut.flush();
            bOut.close();

        } catch (FileNotFoundException fnfE) {
            System.err.println("error : file not found");
        } catch (IOException ioE) {
            System.err.println("io error");
        }

    }

    public void decrypt(String in, String out) {
        bIn = new BinaryIn(in);
        bOut = new BinaryOut(out);
        File file = new File(in);
        long Flength = file.length() * 8 / 64;
        Boolean reader;
        Boolean first = true;
        int teller = 1;
        int stap = 0;
        String temp = "";
        int added = 0;
        int[] outArr = new int[64];
        while (!bIn.isEmpty()) {
            reader = bIn.readBoolean();
            if (teller != 64) {
                if (first) {
                    if (reader) {
                        temp += "1";
                    } else {
                        temp += 0;
                    }
                } else {
                    if (reader) {
                        outArr[teller-1] = 1;
                    } else {
                        outArr[teller-1] = 0;
                    }
                }
            } else {
                if (first) {
                    if (reader) {
                        temp += "1";
                    } else {
                        temp += 0;
                    }
                    added = Integer.parseInt(temp, 2);
                    System.out.println(temp);
                    System.out.println("added: " + added);
                    first = false;
                    stap ++;
                } else {
                    if (reader) {
                        outArr[teller-1] = 1;
                    } else {
                        outArr[teller-1] = 0;
                    }
                    outArr = en.Decrypteer(outArr);
                    if (stap == Flength) {
                        for (int i = 0; i < 64 - added; i++) {
                            if (outArr[i] == 1) {
                                bOut.write(true);
                            } else {
                                bOut.write(false);
                            }
                        }
                    } else {
                        for (int i = 0; i < 64; i++) {
                            if (outArr[i] == 1) {
                                bOut.write(true);
                            } else {
                                bOut.write(false);
                            }
                        }
                    }
                }
                teller = 0;
                outArr = new int[64];
                stap++;
            }

            teller++;



        }
        bOut.close();
    }
}

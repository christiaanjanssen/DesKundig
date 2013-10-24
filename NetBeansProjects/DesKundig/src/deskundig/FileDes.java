/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package deskundig;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jeffr_000
 */
public class FileDes {

    private Encryptie en;
    private BinaryOut bOut;
    private BinaryIn bIn;
    private String sleutel;
    private ThreadResult enResult;
    private Keys[] Sleutels;

    public FileDes(String[] slke) {
        Sleutels = new Keys[3];
        Sleutels[0] = new Keys(slke[0]);
        Sleutels[1] = new Keys(slke[1]);
        Sleutels[2] = new Keys(slke[2]);
        enResult = new ThreadResult(Sleutels);
    }

    public void encrypt(File file, File out) {
        bOut = new BinaryOut(out.getAbsolutePath());
        try {
            long Flength = file.length() * 8;
            FileInputStream is = new FileInputStream(file);
            byte[] chunk = new byte[8];
            String temp;
            int chunkLen = 0;

            long rest = Flength % 64;
            long addedLong = 64 - rest;
            int added = (int) addedLong;
            if (added == 64) {
                added = 0;
            }
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
                enResult.setResult(outArr);
                enResult.setStap(0);
                Thread newT = new Thread(new Encryptie(enResult, true), "TridesEncryptie");
                newT.start();
                try {
                    newT.join();
                } catch (InterruptedException ex) {
                    Logger.getLogger(FileDes.class.getName()).log(Level.SEVERE, null, ex);
                }
                outArr = enResult.getResult();
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

    public void decrypt(File file, File out) {
        bIn = new BinaryIn(file.getAbsolutePath());
        bOut = new BinaryOut(out.getAbsolutePath());
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
                        outArr[teller - 1] = 1;
                    } else {
                        outArr[teller - 1] = 0;
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
                    System.out.println("added: " + added);
                    first = false;
                    stap++;
                } else {
                    if (reader) {
                        outArr[teller - 1] = 1;
                    } else {
                        outArr[teller - 1] = 0;
                    }
                    enResult.setResult(outArr);
                    enResult.setStap(0);
                    Thread newT = new Thread(new Encryptie(enResult, false), "TridesEncryptie");
                    newT.start();
                    try {
                        newT.join();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(FileDes.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    outArr = enResult.getResult();
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

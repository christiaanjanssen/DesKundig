/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package deskundig;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jeffr_000
 */
public class FileDes implements Runnable {

    private Encryptie en;
    private BinaryOut bOut;
    private BinaryIn bIn;
    private String sleutel;
    private ThreadResult enResult;
    private Keys[] Sleutels;
    private File file, out;
    private boolean bEncrypt;
    private ArrayList<Thread> threadList;
    private ArrayList<ThreadResult> resultList;

    public FileDes(String[] slke, File fin, File fout, boolean ecrypt) {
        Sleutels = new Keys[3];
        Sleutels[0] = new Keys(slke[0]);
        Sleutels[1] = new Keys(slke[1]);
        Sleutels[2] = new Keys(slke[2]);
        enResult = new ThreadResult(Sleutels);
        this.file = fin;
        this.out = fout;
        this.bEncrypt = ecrypt;
        this.threadList = new ArrayList<>();
        resultList = new ArrayList<>();
    }

    public void encrypt() {
        bOut = new BinaryOut(out.getAbsolutePath());
        bIn = new BinaryIn(file.getAbsolutePath());
        long Flength = file.length() * 8;
        long totalSteps = (long) Math.ceil(Flength / 64.0);
        long step = 0;
        String temp;
        int threadTeller = 0;
        int totalThreads = (int) Math.ceil(Flength / 64.0);
        int totalThreadsTeller = 0;

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

        Thread newT;
        int[] outArr = new int[64];
        int teller = 0;
        boolean read;
        
        while (step != totalSteps) {

            if (!bIn.isEmpty()) {
                read = bIn.readBoolean();
                if (read) {
                    outArr[teller] = 1;
                } else {
                    outArr[teller] = 0;
                }
            } else {
                outArr[teller] = 0;
            }
            teller++;

            if (teller == 64) {
                resultList.add(new ThreadResult(Sleutels));
                resultList.get(threadTeller).setResult(outArr);
                newT = new Thread(new Encryptie(resultList.get(threadTeller), true));
                threadList.add(newT);
                newT.start();
//                try {
//                    newT.join();
//                } catch (InterruptedException ex) {
//                    Logger.getLogger(FileDes.class.getName()).log(Level.SEVERE, null, ex);
//                }
                
                totalThreadsTeller++;
                
                if (threadTeller == 9 || totalThreadsTeller == totalThreads) {
                    for (Iterator<Thread> it = threadList.iterator(); it.hasNext();) {
                        try {
                            it.next().join();
                        } catch (InterruptedException ex) {
                            Logger.getLogger(FileDes.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    
                    for (int i = 0; i < resultList.size(); i++) {
                        outArr = resultList.get(i).getResult();
                        for (int j = 0; j < 64; j++) {
                            //System.err.println(j + " step: " + step + "/" + totalSteps + " thread: " + totalThreadsTeller + "/" + totalThreads);
                            if (outArr[j] == 1) {
                                bOut.write(true);
                            } else {
                                bOut.write(false);
                            }
                        }
                    }
                    resultList.clear();
                    threadList.clear();

                    threadTeller = 0;
                } else {
                    threadTeller++;
                }
                teller = 0;
                outArr = new int[64];
                step++;
            }
        }

        bOut.flush();
        bOut.close();

    }

    public void decrypt() {
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

    @Override
    public void run() {
        if (bEncrypt) {
            encrypt();
        } else {
            decrypt();
        }
    }
}

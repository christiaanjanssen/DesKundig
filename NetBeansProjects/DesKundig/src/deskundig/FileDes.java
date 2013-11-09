/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package deskundig;

import Screens.Cryptography;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
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
    private Cryptography superTask;

    public FileDes(String[] slke, File fin, File fout, boolean ecrypt, Cryptography superTask) {
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
        this.superTask = superTask;
    }

    public void encrypt() {
        bOut = new BinaryOut(out.getAbsolutePath());
        bIn = new BinaryIn(file.getAbsolutePath());
        
        long totalProg = file.length();
        double curProg = 0;
        
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

                totalThreadsTeller++;

                if (threadTeller == 50 || totalThreadsTeller == totalThreads) {
                    try {
                        newT.join();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(FileDes.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    for (int j = threadList.size() - 1; j >= 0; j--) {
                        try {
                            threadList.get(j).join();
                        } catch (InterruptedException ex) {
                            Logger.getLogger(FileDes.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                    for (int i = 0; i < resultList.size(); i++) {
                        outArr = resultList.get(i).getResult();
                        for (int j = 0; j < 64; j++) {
                            if (outArr[j] == 1) {
                                bOut.write(true);
                            } else {
                                bOut.write(false);
                            }
                        }
                    }
                    if (out.length() == 0) {
                        superTask.setProgress(0);
                    } else {
                        curProg = (double) out.length() / (double) totalProg * 100.0;
                        superTask.setProgress((int) curProg);
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
        superTask.desDone();

    }

    public void decrypt() {
        bOut = new BinaryOut(out.getAbsolutePath());
        bIn = new BinaryIn(file.getAbsolutePath());
        long Flength = file.length() * 8;
        
        long totalProg = file.length();
        double curProg = 0;
        
        long totalSteps = (long) Math.ceil(Flength / 64.0);
        long step = 0;
        String temp = "";
        int added = 0;
        int threadTeller = 0;
        int totalThreads = (int) Math.ceil(Flength / 64.0) - 1;
        int totalThreadsTeller = 0;

        Thread newT;
        int[] outArr = new int[64];
        int teller = 0;
        boolean read;
        boolean first = true;

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
                if (!first) {
                    resultList.add(new ThreadResult(Sleutels));
                    resultList.get(threadTeller).setResult(outArr);
                    newT = new Thread(new Encryptie(resultList.get(threadTeller), false));
                    threadList.add(newT);
                    newT.start();

                    totalThreadsTeller++;
                    threadTeller++;

                    if (threadTeller == 50 || totalThreadsTeller == totalThreads) {
                        try {
                            newT.join();
                        } catch (InterruptedException ex) {
                            Logger.getLogger(FileDes.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        for (int j = threadList.size() - 1; j >= 0; j--) {
                            try {
                                threadList.get(j).join();
                            } catch (InterruptedException ex) {
                                Logger.getLogger(FileDes.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }

                        for (int i = 0; i < resultList.size(); i++) {
                            outArr = resultList.get(i).getResult();
                            if (totalThreadsTeller != totalThreads || i != (resultList.size() - 1)) {
                                for (int j = 0; j < 64; j++) {
                                    if (outArr[j] == 1) {
                                        bOut.write(true);
                                    } else {
                                        bOut.write(false);
                                    }
                                }
                            } else {
                                for (int j = 0; j < 64 - added; j++) {
                                    if (outArr[j] == 1) {
                                        bOut.write(true);
                                    } else {
                                        bOut.write(false);
                                    }
                                }
                            }
                        }
                        if (out.length() == 0) {
                            superTask.setProgress(0);
                        } else {
                            curProg = (double) out.length() / (double) totalProg * 100.0;
                            superTask.setProgress((int) curProg);
                        }
                        resultList.clear();
                        threadList.clear();

                        threadTeller = 0;
                    }

                } else {
                    for (int i = 0; i < outArr.length; i++) {
                        temp += outArr[i];
                    }
                    added = Integer.parseInt(temp, 2);
                    first = false;
                }
                step++;
                teller = 0;
                outArr = new int[64];

            }
        }

        bOut.flush();
        bOut.close();
        superTask.desDone();

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

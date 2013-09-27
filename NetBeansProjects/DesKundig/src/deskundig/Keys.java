package deskundig;

import java.util.ArrayList;

public class Keys {

    private int[] temp1 = new int[56];
    private int[] temp2 = new int[48];
    private String key;
    private String keys[][] = new String[7][8];
    private String C[][] = new String[4][7];
    private String D[][] = new String[6][8];
    private String uit[][] = new String[6][8];
    private final int[][] PC1 = {
        {57, 49, 41, 33, 25, 17, 9},
        {1, 58, 50, 42, 34, 26, 18},
        {10, 2, 59, 51, 43, 35, 27},
        {19, 11, 3, 60, 52, 44, 36},
        {63, 55, 47, 39, 31, 23, 15},
        {7, 62, 54, 46, 38, 30, 22},
        {14, 6, 61, 53, 45, 37, 29},
        {21, 13, 5, 28, 20, 12, 4}
    };
    private final int[][] PC2 = {
        {14, 17, 11, 24, 1, 5},
        {3, 28, 15, 6, 21, 10},
        {23, 19, 12, 4, 26, 8},
        {16, 7, 27, 20, 13, 2},
        {41, 52, 31, 37, 47, 55},
        {30, 40, 51, 45, 33, 48},
        {44, 49, 39, 56, 34, 53},
        {46, 42, 50, 36, 29, 32}
    };

    public Keys() {
    }

    /**
     * Maakt van de PC1 permutatie matrix een single row matrix (voor gemak)
     */
    public void straightenPC1() {
        int teller = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 7; j++) {
                temp1[teller] = PC1[i][j];
                teller++;
            }
        }
    }

    /**
     * Maakt van de PC2 permutatie matrix een single row matrix (voor gemak)
     */
    public void straightenPC2() {
        int teller = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 7; j++) {
                temp2[teller] = PC2[i][j];
                teller++;
            }
        }
    }

    /**
     * Permuteerd een deel van de key met permutatie matrix C1
     *
     * @param in de in matrix: een deel van het password (64 bits)
     * @param out de gepermuteerde matrix (58 bits)
     */
    public void permWC1(int[] in, int[] out) {
        int temp = 0, i = 0, teller = 0, kijk = 0;
        while (kijk != 56) {
            temp = temp1[i];
            if (temp == teller) {
                out[kijk] = in[teller - 1];
                teller = 0;
                kijk++;
                i++;
            }
            teller++;
        }

        int index = 0;
        for (int j = 0; j < 7; j++) {
            for (int k = 0; k < 8; k++) {
                uit[j][k] = Integer.toString(out[index]);
            }
        }
        index = 0;
    }

    /**
     * Permuteerd een deel van de key met permutatie matrix C2
     *
     * @param in de in matrix: C+D (linker en rechter helft) (58 bits)
     * @param out de gepermuteerde matrix (48 bits)
     */
    public void permWC2(int[] in, int[] out) {
        int temp = 0, i = 0, teller = 0, kijk = 0;
        while (kijk != 48) {
            temp = temp2[i];
            if (temp == teller) {
                out[kijk] = in[teller - 1];
                teller = 0;
                kijk++;
                i++;
            }
            teller++;
        }

        int index = 0;
        for (int j = 0; j < 6; j++) {
            for (int k = 0; k < 8; k++) {
                keys[j][k] = Integer.toString(out[index]);
            }
        }
    }
    
    
    /**
     * splitsen van een deelsleutel naar 2 keys
     * 
     * @param out deelsleuten
     * @param C 1e deel
     * @param D 2de deel
     */
    public void split(int[] out, int[] C, int[] D){
        for (int i = 0; i < 28; i++) {
            C[i]=out[i];
        }
        
        for (int i = 28; i < 56; i++) {
            D[i-28]=out[i];
        }
    }
    
    public void shiftOne(int[] C, int[] D){
        
    }
    
    
}

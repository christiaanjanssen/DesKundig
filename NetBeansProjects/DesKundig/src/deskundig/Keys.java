package deskundig;

import java.util.ArrayList;

public class Keys {

    private String pass;
    private final int[] NSHIFTS = {1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1};
    static final int[][] PC1 = {
        {57, 49, 41, 33, 25, 17, 9},
        {1, 58, 50, 42, 34, 26, 18},
        {10, 2, 59, 51, 43, 35, 27},
        {19, 11, 3, 60, 52, 44, 36},
        {63, 55, 47, 39, 31, 23, 15},
        {7, 62, 54, 46, 38, 30, 22},
        {14, 6, 61, 53, 45, 37, 29},
        {21, 13, 5, 28, 20, 12, 4}
    };
    static final int[][] PC2 = {
        {14, 17, 11, 24, 1, 5},
        {3, 28, 15, 6, 21, 10},
        {23, 19, 12, 4, 26, 8},
        {16, 7, 27, 20, 13, 2},
        {41, 52, 31, 37, 47, 55},
        {30, 40, 51, 45, 33, 48},
        {44, 49, 39, 56, 34, 53},
        {46, 42, 50, 36, 29, 32}
    };

    public int[][] getKeys(String pass) {
        byte[] bytes = pass.getBytes();
        ArrayList<Integer> binners = new ArrayList<>();

        for (byte b : bytes) {
            int val = b;
            for (int i = 0; i < 8; i++) {
                binners.add((val & 128) == 0 ? 0 : 1);
                val <<= 1;
            }
        }

        int[][] toSplit = perm(binners, PC1);

        int[][] C0 = new int[4][7];
        int[][] D0 = new int[4][7];

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 7; j++) {
                C0[i][j] = toSplit[i][j];
            }
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 7; j++) {
                D0[i][j] = toSplit[i + 4][j];
            }
        }
        
        //shiften is volgende

        return null;
    }
    
    private int[][] perm(ArrayList<Integer> in, int[][] PC){
        int PCSize = PC.length + PC[0].length;
        int[][] out = new int[PC.length][PC[0].length];
        
        for (int i = 0; i < PCSize; i++) {
            for (int j = 0; j < PC.length; j++) {
                for (int k = 0; k < PC[0].length; k++) {
                    if (PC[j][k] == i) {
                        out[j][k] = in.get(i);
                    }
                }
            }
        }
        
        return out;
    }

    private ArrayList<Integer> straighten(int[][] in) {
        ArrayList<Integer> temp = new ArrayList<>();

        for (int i = 0; i < in.length; i++) {
            for (int j = 0; j < in[0].length; j++) {
                temp.add(in[i][j]);
            }
        }

        return temp;
    }
    
}

package deskundig;

public class SBox {

    private int[] func_out;
    // private int[] opslag;
    private int[] naSBox;
    private int index;
    private int eersteIndex;
    private int tweedeIndex;
    private int rij;
    private int kolom;
    private int[] resultaatSBox;
    private int[] rijSbox;
    private int[] kolomSBox;
    private int[] eersteDec;
    private int[] tweedeDec;
    private int[] temp;

    public final void reset() {
        this.temp = new int[6];
        this.tweedeDec = new int[4];
        this.eersteDec = new int[2];
        this.kolomSBox = new int[8];
        this.rijSbox = new int[8];
        this.resultaatSBox = new int[8];
        this.kolom = 0;
        this.rij = 0;
        this.tweedeIndex = 0;
        this.eersteIndex = 0;
        this.index = 0;
        this.naSBox = new int[32];
        this.func_out = new int[32];
    }

    /**
     *
     *
     * @param input
     */
    public void DoDecimal(int[] input) {
        eersteDec[0] = input[0];
        eersteDec[1] = input[5];
        rij = 1 * eersteDec[1] + 2 * eersteDec[0];
    }

    public void DoFourDecimal(int[] num) {
        tweedeDec[0] = num[1];
        tweedeDec[1] = num[2];
        tweedeDec[2] = num[3];
        tweedeDec[3] = num[4];
        kolom = 1 * tweedeDec[3] + 2 * tweedeDec[2] + tweedeDec[1] * 4 + tweedeDec[0] * 8;
    }

    public void SelecteerSBox(int keuze, int rij, int kolom) {
        switch (keuze) {
            case 0:
                resultaatSBox[eersteIndex] = sBox1[rij][kolom];
                eersteIndex++;
                break;
            case 1:
                resultaatSBox[eersteIndex] = sBox2[rij][kolom];
                eersteIndex++;
                break;
            case 2:
                resultaatSBox[eersteIndex] = sBox3[rij][kolom];
                eersteIndex++;
                break;
            case 3:
                resultaatSBox[eersteIndex] = sBox4[rij][kolom];
                eersteIndex++;
                break;
            case 4:
                resultaatSBox[eersteIndex] = sBox5[rij][kolom];
                eersteIndex++;
                break;
            case 5:
                resultaatSBox[eersteIndex] = sBox6[rij][kolom];
                eersteIndex++;
                break;
            case 6:
                resultaatSBox[eersteIndex] = sBox7[rij][kolom];
                eersteIndex++;
                break;
            case 7:
                resultaatSBox[eersteIndex] = sBox8[rij][kolom];
                eersteIndex++;
                break;
        }
    }

    /**
     * De methode zorgt voor ene omzetting naar 32 bits
     *
     * @param num
     */
    public void naar32Bits(int getal) {
        int getal2, getal3;

        for (int i = 0; i < 4; i++) {
            getal2 = getal % 2;
            getal3 = getal / 2;
            getal = getal3;
            naSBox[tweedeIndex] = getal2;
            tweedeIndex++;
        }
    }

    public void KeerOm(int[] num) {
        int count = 0;
        int fix = 3;
        int tmp;

        while (count < 32) {
            for (int i = 0; i < 2; i++) {

                tmp = num[count + i];

                num[count + i] = num[fix - (count + i)];
                num[fix - (count + i)] = tmp;
            }

            fix += 8;
            count += 4;
        }
    }

//    public void VulP() {
//        int i = 0;
//        
//        for(int row = 0; row < 8; row++) {
//            for(int col = 0; col < 4; col++){
//                opslag[i] = Matrices.P[row][col];
//                i++;
//            }
//        }
//    }
    public void runP(int[] naSBox, int[] func_out) {
        int tmp = 0;
        int i = 0;
        int loop = 0;
        int check = 0;
        while (check < 32) {
            tmp = RijP[i];
            if (tmp == loop) {
                func_out[check] = naSBox[loop - 1];
                loop = 0;
                check++;
                i++;
            }
            loop++;
        }
    }

    public void runSBox(int[] XOR_Out, int[] S_Out) {
        int count = 0;
        int keuze = 0;
        int i;
        index = 0;

        while (count < 48) {
            for (i = 0; i < 6; i++) {
                temp[i] = XOR_Out[i + count];
            }

            DoDecimal(temp);
            DoFourDecimal(temp);
            rijSbox[index] = rij;
            kolomSBox[index] = kolom;
            SelecteerSBox(keuze, rijSbox[index], kolomSBox[index]);
            naar32Bits(resultaatSBox[index]);

            index++;
            keuze++;
            count += 6;
        }
        KeerOm(naSBox);

        index = 0;

        runP(naSBox, func_out);
        System.arraycopy(func_out, 0, S_Out, 0, 32);
    }
    
    // Initialisatie van de permutatie matrix P in een rij
    private static int[] RijP = {
        16, 7, 20, 21,
        29, 12, 28, 17,
        1, 15, 23, 26,
        5, 18, 31, 10,
        2, 8, 24, 14,
        32, 27, 3, 9,
        19, 13, 30, 6,
        22, 11, 4, 25
    };
    
    // Initialisatie van de 8 S-boxen
    public static int[][] sBox1 = {{14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7},
        {0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8},
        {4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0},
        {15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13}
    };
    public static int[][] sBox2 = {{15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 1},
        {3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5},
        {0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15},
        {13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9}
    };
    public static int[][] sBox3 = {{10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8},
        {13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1},
        {13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7},
        {1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12}
    };
    public static int[][] sBox4 = {{7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15},
        {13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9},
        {10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4},
        {3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14}
    };
    public static int[][] sBox5 = {{2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9},
        {14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6},
        {4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14},
        {11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3}
    };
    public static int[][] sBox6 = {{12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11},
        {10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8},
        {9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6},
        {4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13}
    };
    public static int[][] sBox7 = {{4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1},
        {13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6},
        {1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2},
        {6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12}
    };
    public static int[][] sBox8 = {{13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7},
        {1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2},
        {7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8},
        {2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11}
    };
}

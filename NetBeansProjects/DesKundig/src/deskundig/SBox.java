package deskundig;

public class SBox {

    private int[] func_out;
//    private int[] opslag;
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
                resultaatSBox[eersteIndex] = Matrices.sBox1[rij][kolom];
                eersteIndex++;
                break;
            case 1:
                resultaatSBox[eersteIndex] = Matrices.sBox2[rij][kolom];
                eersteIndex++;
                break;
            case 2:
                resultaatSBox[eersteIndex] = Matrices.sBox3[rij][kolom];
                eersteIndex++;
                break;
            case 3:
                resultaatSBox[eersteIndex] = Matrices.sBox4[rij][kolom];
                eersteIndex++;
                break;
            case 4:
                resultaatSBox[eersteIndex] = Matrices.sBox5[rij][kolom];
                eersteIndex++;
                break;
            case 5:
                resultaatSBox[eersteIndex] = Matrices.sBox6[rij][kolom];
                eersteIndex++;
                break;
            case 6:
                resultaatSBox[eersteIndex] = Matrices.sBox7[rij][kolom];
                eersteIndex++;
                break;
            case 7:
                resultaatSBox[eersteIndex] = Matrices.sBox8[rij][kolom];
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
            tmp = Matrices.RijP[i];
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

        for (int j = 0; j < 32; j++) {
            S_Out[j] = func_out[j];
        }
    }
}

package deskundig;

public class Keys {

    private String pass;
    //private String keys[][] = new String[7][8];
    private byte[][] blok = new byte[8][8];
    private int[] bitKey = new int[64];
    private int[][] keys = new int[16][48];

    public Keys(String p) {
        pass = p;
    }

    public int[][] getKey() {

        VercijferTekst(pass);

        int[] CD1 = new int[56];
        permWC1(bitKey, CD1);

        int[] Cn = new int[28];
        int[] Dn = new int[28];
        split(CD1, Cn, Dn);

        for (int i = 0; i < 16; i++) {
            if (shift[i] == 1) {
                shiftOne(Cn, Dn);
            } else {
                shiftOne(Cn, Dn);
                shiftOne(Cn, Dn);
            }
            int[] cdStraight = new int[56];
            int[] Kn = new int[48];
            combine(Cn, Dn, cdStraight);
            permWC2(cdStraight, Kn);
            keys[i] = Kn;
        }

        return keys;
    }

    /**
     * Permuteerd een deel van de key met permutatie matrix C1
     *
     * @param in de in matrix: een deel van het password (64 bits)
     * @param out de gepermuteerde matrix (58 bits)
     */
    private void permWC1(int[] in, int[] out) {
        int temp = 0, i = 0, teller = 0, kijk = 0;
        while (kijk != 56) {
            temp = RijPC1[i];
            if (temp == teller) {
                out[kijk] = in[teller - 1];
                teller = 0;
                kijk++;
                i++;
            }
            teller++;
        }

    }

    /**
     * Permuteerd een deel van de key met permutatie matrix C2
     *
     * @param in de in matrix: C+D (linker en rechter helft) (58 bits)
     * @param out de gepermuteerde matrix (48 bits)
     */
    private void permWC2(int[] in, int[] out) {
        int temp = 0, i = 0, teller = 0, kijk = 0;
        while (kijk != 48) {
            temp = RijPC2[i];
            if (temp == teller) {
                out[kijk] = in[teller - 1];
                teller = 0;
                kijk++;
                i++;
            }
            teller++;
        }
    }

    /**
     * splitsen van een deelsleutel naar 2 keys
     *
     * @param out deelsleuten
     * @param C 1e deel
     * @param D 2de deel
     */
    private void split(int[] in, int[] C, int[] D) {
        for (int i = 0; i < 28; i++) {
            C[i] = in[i];
        }

        for (int i = 28; i < 56; i++) {
            D[i - 28] = in[i];
        }
    }

    private void combine(int[] C, int[] D, int[] CD) {
        for (int i = 0; i < 28; i++) {
            CD[i] = C[i];
        }

        int index = 28;
        for (int i = 0; i < 28; i++) {
            CD[index] = D[i];
            index++;
        }
    }

    private void shiftOne(int[] C, int[] D) {
        int temp = C[0];
        for (int i = 1; i < C.length; i++) {
            C[i - 1] = C[i];
        }
        C[C.length - 1] = temp;

        temp = D[0];
        for (int i = 1; i < D.length; i++) {
            D[i - 1] = D[i];
        }
        D[D.length - 1] = temp;
    }

    private void VercijferTekst(String tekst) {
        //zorgen dat dit per 8 bits gebeurt, en we niet VERDER gaan dan de tekst zelf
        for (int i = 0; i < 8 && i < tekst.length(); i++) {
            blok[i] = getBinaryBits(tekst.charAt(i));
            //dit geeft een 2D-array terug van 8 op 8 
        }

        int index = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                bitKey[index] = (int) blok[i][j];
                index++;
            }
        }

    }

    private byte[] getBinaryBits(int ch) {
        byte[] bin = new byte[8];
        int tag = 1;
        for (int i = 0; i < 8; i++) {
            bin[7 - i] = (byte) ((ch & ((tag << i))) >> i);
        }
        return bin;
    }
    
    // Permutatie keuze 1 wordt bepaald door volgende matrix PC1 als rij
    public static int[] RijPC1 = {
        57, 49, 41, 33, 25, 17, 9,
        1, 58, 50, 42, 34, 26, 18,
        10, 2, 59, 51, 43, 35, 27,
        19, 11, 3, 60, 52, 44, 36,
        63, 55, 47, 39, 31, 23, 15,
        7, 62, 54, 46, 38, 30, 22,
        14, 6, 61, 53, 45, 37, 29,
        21, 13, 5, 28, 20, 12, 4
    };
    
    // Permutatie keuze 2 wordt bepaald door volgende matrix PC2 als rij
    public static int[] RijPC2 = {
        14, 17, 11, 24, 1, 5,
        3, 28, 15, 6, 21, 10,
        23, 19, 12, 4, 26, 8,
        16, 7, 27, 20, 13, 2,
        41, 52, 31, 37, 47, 55,
        30, 40, 51, 45, 33, 48,
        44, 49, 39, 56, 34, 53,
        46, 42, 50, 36, 29, 32
    };
    
    // Matrix met schiftvolgordes 
    public static int[] shift = {
        1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1
    };
}

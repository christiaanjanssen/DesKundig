package deskundig;

import java.util.Arrays;

public class Encryptie {

    private Keys[] Sleutels;
    private byte[][] blok = new byte[8][8]; // 64-bit array
    private static int[] invoerRij = new int[64];
    private static int[] gepermuteerdeRij = new int[64];
    private static int[] linkerDeel = new int[32]; // Het linker deel van de opgesplitste, gepermuteerde rij
    private static int[] rechterDeel = new int[32]; // Het rechter deel van de opgesplitste, gepermuteerde rij
    private static int[] rechterDeelNaExpansie = new int[48];
    private static int[] blok64Array = new int[64];
    private static int[] nieuwBlok64Array = new int[64];
    private static int[] resultaatXOR = new int[48];
    private static int[] resultaatSBox = new int[32];
    private static int[] naXOR = new int[32];
    private int stap = 0;

    /**
     * Default constructor van de encryptie klasse.
     *
     */
    public Encryptie() {
    }

    public Encryptie(String[] slke) {
        Sleutels = new Keys[3];
        Sleutels[0] = new Keys(slke[0]);
        Sleutels[1] = new Keys(slke[1]);
        Sleutels[2] = new Keys(slke[2]);
    }

    /**
     * Deze functie controleert of de string een 64 bit array past.
     *
     * Indien dit niet het geval is, worden spaties toegevoegd om aan deze
     * voorwaarde te voldoen.
     *
     * @return
     */
    public String ControleerLengteInvoer(String invoer) {
        // Constroleer of de totale lengte deelbaar is door acht
        if (invoer.length() % 8 != 0) {
            int lengte = 8 - invoer.length() % 8;

            // Sterretjes toevoegen
            for (int i = 0; i < lengte; i++) {
                invoer = invoer.concat(" ");
            }
        } else {
            return invoer;
        }

        return invoer;
    }

    /**
     * Deze functie zet een stuck tekst om in een 64 bit arrays.
     *
     * @param invoer
     */
    public void VercijferTekst(String invoer) {
        // Een 64 bit matrix heeft 8 rijen en 8 kolommen
        for (int i = 0; i < 8 && i < invoer.length(); i++) {
            blok[i] = getBinaireBits(invoer.charAt(i));
        }

        int index = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                invoerRij[index] = (int) blok[i][j];
                index++;
            }
        }
    }

    /**
     * Deze functie zet een karakter om naar bits.
     *
     * @param karakter
     * @return
     */
    public byte[] getBinaireBits(int karakter) {
        byte[] bin = new byte[8];
        int tag = 1;
        for (int i = 0; i < 8; i++) {
            // Deze regel controleer of de positie van karakter en de positie van tag gelijk 
            // zijn. Indien dit het geval is wordt deze met i posities naar rechts geschift.
            bin[7 - i] = (byte) ((karakter & ((tag << i))) >> i);
        }
        return bin;
    }

    /**
     * Deze functie splits de gepermuteerde rij op in een linker en een rechter
     * deel.
     *
     * @param perm_uit
     */
    public void DeelOp(int[] volledigeRij) {
        // Linker deel
        for (int i = 0; i < 32; i++) {
            linkerDeel[i] = volledigeRij[i];
        }

        // Rechter deel
        int index = 0;
        for (int i = 32; i < 64; i++) {
            rechterDeel[index] = volledigeRij[i];
            index++;
        }
    }

    /**
     * In deze functie test de XOR-operator (exclusieve OF-operator) op de 2
     * meegegeven rijen.
     *
     * Het resultaat van deze operatie wordt weggeschreven in de resultaatrij.
     *
     * @param ingang1
     * @param ingang2
     * @param resultaat
     */
    public void XOR(int[] ingang1, int[] ingang2, int[] resultaat) {
        int index = 0;
        for (int i = 0; i < ingang1.length; i++) {
            if (ingang1[i] == ingang2[i]) {
                resultaat[index] = 0;
            } else {
                resultaat[index] = 1;
            }
            index++;

        }
    }

    /**
     * Linker- en rechterdeel van kant wisselen.
     */
    public void WisselOm() {
        int temp;
        for (int i = 0; i < 32; i++) {
            temp = linkerDeel[i];
            linkerDeel[i] = rechterDeel[i];
            rechterDeel[i] = temp;
        }
    }

    /**
     * Deze functie voegt het linker- en rechterdeel terug samen.
     */
    public void VoegSamen() {
        for (int i = 0; i < 32; i++) {
            blok64Array[i] = linkerDeel[i];
        }

        int index = 32;
        for (int i = 0; i < 32; i++) {
            blok64Array[index] = rechterDeel[i];
            index++;
        }
    }

    public static int[] BitToByte(int bits64[]) {
        int ch[] = new int[8];
        int index = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 1; j <= 8; j++) {
                ch[i] += (int) Math.pow(2, (8 - j)) * bits64[index];
                index++;
            }
        }

        return ch;
    }

    public String Decrypteer(String invoerString) {
        String done = null;
        int[][] keys = Sleutels[stap].getKey();
        int start = 0;
        int einde = 64;
        int counter = 0;
        int newBlock64_[] = new int[64];

        VercijferTekst(invoerString);

        for (int i = 0; i < invoerString.length() / 64; i++) {
            for (int h = start; h < einde; h++) {
                newBlock64_[counter] = Integer.parseInt(invoerString.substring(h, h + 1));
                counter++;
            }

            Permutatie p = new Permutatie();
            p.VulPermutatie();
            p.Permuteer(newBlock64_, gepermuteerdeRij);

            DeelOp(gepermuteerdeRij);

            for (int j = 15; j >= 0; j--) {
                ExpansieTabel e = new ExpansieTabel();
                e.ZetOmNaarRij();
                e.Exponeren(rechterDeel, rechterDeelNaExpansie);

                XOR(rechterDeelNaExpansie, keys[j], resultaatXOR);

                SBox sBox = new SBox();
                sBox.runSBox(resultaatXOR, resultaatSBox);

                // XOR-operatie uitvoeren op het linkerdeel
                XOR(linkerDeel, resultaatSBox, naXOR);

                // Resultaat van de XOR-bewerking toekennen
                for (int g = 0; g < 32; g++) {
                    linkerDeel[g] = rechterDeel[g];
                    rechterDeel[g] = naXOR[g];
                }

            }

            WisselOm();
            VoegSamen();

            // Inverse permutatiematrix in een rij omzetten
            p.VulInversePermutatie();

            // Inversie permutatie uitvoeren op het samengevoegd resultaat
            p.PermuteerInvers(blok64Array, nieuwBlok64Array);

            int[] ch = BitToByte(nieuwBlok64Array);
            for (int k = 0; k < 8; k++) {
                if (done == null) {
                    done = Character.toString((char) ch[k]);
                } else {
                    done += Character.toString((char) ch[k]);
                }
            }

            start = einde;
            einde += 64;
            counter = 0;

        }

        return done;

    }

    public String Encrypteer(String invoerString) {
        String done = null;
        int[][] keys = Sleutels[stap].getKey();
        int start = 0;
        int einde = 8;

        invoerString = ControleerLengteInvoer(invoerString);

        String tmp;
        for (int i = 0; i < invoerString.length() / 8; i++) {
            tmp = invoerString.substring(start, einde);

            VercijferTekst(tmp);

            Permutatie p = new Permutatie();
            p.VulPermutatie();
            p.Permuteer(invoerRij, gepermuteerdeRij);

            DeelOp(gepermuteerdeRij);

            for (int j = 0; j < 16; j++) {
                ExpansieTabel e = new ExpansieTabel();
                e.ZetOmNaarRij();
                e.Exponeren(rechterDeel, rechterDeelNaExpansie);

                XOR(rechterDeelNaExpansie, keys[j], resultaatXOR);

                SBox sBox = new SBox();
                sBox.runSBox(resultaatXOR, resultaatSBox);

                // XOR-operatie uitvoeren op het linkerdeel
                XOR(linkerDeel, resultaatSBox, naXOR);

                // Resultaat van de XOR-bewerking toekennen
                for (int g = 0; g < 32; g++) {
                    linkerDeel[g] = rechterDeel[g];
                    rechterDeel[g] = naXOR[g];
                }

            }

            WisselOm();
            VoegSamen();

            // Inverse permutatiematrix in een rij omzetten
            p.VulInversePermutatie();

            // Inversie permutatie uitvoeren op het samengevoegd resultaat
            p.PermuteerInvers(blok64Array, nieuwBlok64Array);

            for (int l = 0; l < nieuwBlok64Array.length; l++) {
                if (done == null) {
                    done = Integer.toString(nieuwBlok64Array[l]);
                } else {
                    done += Integer.toString(nieuwBlok64Array[l]);
                }

            }

//            int[] ch = BitToByte(nieuwBlok64Array);
//            for(int k=0; k<8; k++){
//                System.out.print((char)ch[k]);
//                if(done==null)
//                    done=Character.toString((char)ch[k]);
//                else
//                    done+=Character.toString((char)ch[k]);
//            }

            start = einde;
            einde += 8;

        }

        return done;
    }
}

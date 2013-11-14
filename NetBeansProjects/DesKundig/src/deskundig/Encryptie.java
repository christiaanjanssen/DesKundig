package deskundig;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Encryptie implements Runnable {

    private int[] gepermuteerdeRij;
    private int[] linkerDeel; // Het linker deel van de opgesplitste, gepermuteerde rij
    private int[] rechterDeel; // Het rechter deel van de opgesplitste, gepermuteerde rij
    private int[] rechterDeelNaExpansie;
    private int[] blok64Array;
    private int[] nieuwBlok64Array;
    private int[] resultaatXOR;
    private int[] resultaatSBox;
    private int[] naXOR;
    private ThreadResult tResult;
    private ExpansieTabel e;
    private SBox sBox;
    private Permutatie p;
    private boolean enc;
        

    /**
     * Default constructor van de encryptie klasse.
     *
     */
    public Encryptie(ThreadResult intResult, boolean enc) {
        this.naXOR = new int[32];
        this.resultaatSBox = new int[32];
        this.resultaatXOR = new int[48];
        this.nieuwBlok64Array = new int[64];
        this.blok64Array = new int[64];
        this.rechterDeelNaExpansie = new int[48];
        this.rechterDeel = new int[32];
        this.linkerDeel = new int[32];
        this.gepermuteerdeRij = new int[64];
        
        this.tResult = intResult;
        
        this.e = new ExpansieTabel();
        this.p = new Permutatie();
        this.sBox = new SBox();
        
        this.enc = enc;

    }
    
    

    /**
     * Deze functie splits de gepermuteerde rij op in een linker en een rechter
     * deel.
     *
     * @param perm_uit
     */
    public void DeelOp(int[] volledigeRij) {
        System.arraycopy(volledigeRij, 0, linkerDeel, 0, 32);

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
        System.arraycopy(linkerDeel, 0, blok64Array, 0, 32);

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

    public void Decrypteer() {
        int invStap = 2 - tResult.getStap();
        int[][] keys =  tResult.getSleutels(invStap).getKey();

        
        p.Permuteer(tResult.getResult(), gepermuteerdeRij);

        DeelOp(gepermuteerdeRij);

        for (int j = 15; j >= 0; j--) {
            e.Exponeren(rechterDeel, rechterDeelNaExpansie);

            XOR(rechterDeelNaExpansie, keys[j], resultaatXOR);
            
            sBox.reset();
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

        // Inversie permutatie uitvoeren op het samengevoegd resultaat
        p.PermuteerInvers(blok64Array, nieuwBlok64Array);

        int[] ch = BitToByte(nieuwBlok64Array);

        if (tResult.getStap() == 2) {
            tResult.setStap(0);
            tResult.setResult(nieuwBlok64Array);
        } else {
            try {
                tResult.StapUp();
                tResult.setResult(nieuwBlok64Array);
                Thread newThread = new Thread(new Encryptie(tResult, false), "encryptie");
                newThread.start();
                newThread.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(Encryptie.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void encrypteer() {
        int[][] keys = tResult.getSleutels(tResult.getStap()).getKey();

        p.Permuteer(tResult.getResult(), gepermuteerdeRij);

        DeelOp(gepermuteerdeRij);

        for (int j = 0; j < 16; j++) {
            e.Exponeren(rechterDeel, rechterDeelNaExpansie);

            XOR(rechterDeelNaExpansie, keys[j], resultaatXOR);

            sBox.reset();
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

        // Inversie permutatie uitvoeren op het samengevoegd resultaat
        p.PermuteerInvers(blok64Array, nieuwBlok64Array);

        if (tResult.getStap() == 2) {
            tResult.setStap(0);
            tResult.setResult(nieuwBlok64Array);
        } else {
            try {
                tResult.StapUp();
                tResult.setResult(nieuwBlok64Array);
                Thread newThread = new Thread(new Encryptie(tResult, true), "encryptie");
                newThread.start();
                newThread.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(Encryptie.class.getName()).log(Level.SEVERE, null, ex);
            }
        }


    }

    @Override
    public void run() {
        if(enc)
            encrypteer();
        else
            Decrypteer();
    }
}

package deskundig;

public class Encryptie {
    
    private String invoerString;
    private String sleutelString;
    private Keys sleutel;
    
    private byte[][] blok = new byte[8][8]; // 64-bit array
    private String [][] blokString= new String[8][8];
    
    private static int[] invoerRij = new int[64];
    private static int[] gepermuteerdeRij = new int[64]; 
    private static int[] linkerDeel = new int [32]; // Het linker deel van de opgesplitste, gepermuteerde rij
    private static int[] rechterDeel = new int [32]; // Het rechter deel van de opgesplitste, gepermuteerde rij
    private static int[] rechterDeelNaExpansie = new int [48];
    private static int[] blok64Array = new int [64];
    private static int[] nieuwBlok64Array = new int [64];
    
    private static int[] origineleSleutel = new int[64];
    private static int[] aangepasteSleutel = new int[56];
    private static int[] linkerDeelSleutel = new int[28]; // Het linker deel van de opgesplitste, 56-bit sleutel
    private static int[] rechterDeelSleutel = new int[28]; // Het rechter deel van de opgesplitste, 56-bit sleutel
    private static int[] linkerEnRechterDeelSleutel = new int[56];
    private static int[] resultaatSleutel = new int[48];
    private static int[] resultaatXOR = new int[48];
    private static int[] resultaatSBox = new int[32];
    private static int[] naXOR = new int [32];
    
    
    /**
     * Default constructor van de encryptie klasse.
     * 
     */
    public Encryptie() { }
    
    public Encryptie(String invoer, String sleutel) {
        invoerString = invoer;
        this.sleutel = new Keys(sleutel); // Sleutel aanmaken
    }
    
    /**
     * Deze functie controleert of de string een 64 bit array past. 
     * 
     * Indien dit niet het geval is, worden sterretjes (*) toegevoegd om aan 
     * deze voorwaarde te voldoen.
     * 
     * @return
     */
    public String ControleerLengteInvoer(String invoer) {
        // Constroleer of de totale lengte deelbaar is door acht
        if (invoer.length() % 8 != 0) {
            int lengte = 8 - invoer.length() % 8;
            
            // Sterretjes toevoegen
            for (int i = 0; i < lengte; i++) {
                invoer = invoer.concat("*");
            }
        }
        else {
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
                blokString[i][j] = Integer.toString((int) blok[i][j]);
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
            bin[7 - i] = (byte) ((karakter &((tag << i))) >> i);
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
    
    public void Shift(int keuze) {
        if (keuze == 1) {
            // 1 shift
            sleutel.shiftOne(linkerDeel, rechterDeel);
        }
        else {
            // 2 shifts
            sleutel.shiftOne(linkerDeel, rechterDeel);
            sleutel.shiftOne(linkerDeel, rechterDeel);
        }
    }
    
    /**
     * Deze functie voegt het linker- en rechterdeel van de sleutel terug samen.
     */
    public void VulCenD() {
        // Het linkerdeel
        for (int i = 0; i < 28; i++) {
            linkerEnRechterDeelSleutel[i] = linkerDeelSleutel[i];
        }
        
        // Het rechterdeel
        int index = 28;
        for (int i = 0; i < 28; i++) {
            linkerDeelSleutel[index] = rechterDeelSleutel[i];
            index++;
        }
    }
    
    /**
     * In deze functie test de XOR-operator (exclusieve OF-operator) op de 2
     * meegegeven rijen. 
     * 
     * Het resultaat van deze operatie wordt weggeschreven in 
     * de resultaatrij.
     * 
     * @param ingang1
     * @param ingang2
     * @param resultaat 
     */
    public void XOR(int[] ingang1, int[] ingang2, int[] resultaat) {
        for (int i = 0; i < ingang1.length; i++) {
            if (ingang1[i] == ingang2[i]) {
                resultaat[i] = 0;
            }
            else {
                resultaat[i] = 1;
            }
        }
    }
    
    /**
     * Linker- en rechterdeel van kant wisselen.
     */
    public void WisselOm() {
        int tmp;
        for (int i = 0; i < 32; i++) {
            tmp = linkerDeel[i];
            linkerDeel[i] = rechterDeel[i];
            rechterDeel[i] = tmp;
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
    
    /**
     * 
     */
    public int[] Encrypteer() {
        int start = 0;
        int einde = 8;
        
        // Lengte van de ingevoerde tekst controleren
        ControleerLengteInvoer(invoerString);
        
        // Invoer per 8 karakters vercijferen
        String tmp;
        for (int i = 0; i < invoerString.length() / 8; i++) {
            tmp = invoerString.substring(start, einde);
            
            Permutatie p = new Permutatie();
            p.VulPermutatie();  // Zet de permutatiematrix IP om naar een rij
            
            // Permuteer de invoer (die al omgezet is naar een rij) met de permutatiematrix IP
            p.Permuteer(invoerRij, gepermuteerdeRij);
            
            // Deel de gepermuteerde rij op in een linker- en een rechter deel
            DeelOp(gepermuteerdeRij); 
            
            sleutel.straightenPC1(); // Zet de permutatie matrix P1 om naar een rij
            sleutel.permWC1(origineleSleutel, aangepasteSleutel); // Zet 64-bit sleutel om naar 56-bit versie
            sleutel.split(aangepasteSleutel, linkerDeelSleutel, rechterDeelSleutel);
            
            // Beginnen met de 16 vervormingen
            int ronde = 1;
            int index = 0;
            while (ronde <= 16) {
                int keuze = Matrices.shift[index]; // Ophalen welke shift er moet uitgevoerd worden
                ExpansieTabel e = new ExpansieTabel();
                e.ZetOmNaarRij(); // Zet expansietabel om in een rij
                
                // Zet 32-bit om naar 48-bit a.d.h.v. de expansiematrix e
                e.Exponeren(rechterDeel, rechterDeelNaExpansie); 
                
                Shift(keuze); // Voer een shift uit
                
                sleutel.straightenPC2(); // Zet de permutatie matrix P2 om naar een rij
                VulCenD();
                
                // Permuteer de herenigde linker- en rechterdeel van de sleutel met de matrix
                // C2. Het resultaat is een 48-bit rij.
                sleutel.permWC2(linkerEnRechterDeelSleutel, resultaatSleutel); 
                
                // Sleutel in een String plaatsen
                for (int j = 0; j < 48; j++) {
                    if (sleutelString == null) {
                        sleutelString = Integer.toString(resultaatSleutel[i]);
                    }
                    else {
                        sleutelString += Integer.toString(resultaatSleutel[i]);
                    }
                }
                
                // XOR-operatie uitvoeren op het rechterdeel
                XOR(rechterDeelNaExpansie, resultaatSleutel, resultaatXOR);
                
                SBox sBox = new SBox();
                sBox.runSBox(resultaatXOR, resultaatSBox);
                
                // XOR-operatie uitvoeren op het linkerdeel
                XOR(linkerDeel, resultaatSBox, naXOR);
                
                ronde++;
                index++;
                
                // Resultaat van de XOR-bewerking toekennen
                for (int j = 0; j < 32; i++) {
                    linkerDeel[i] = rechterDeel[i];
                    rechterDeel[i] = naXOR[i];
                }
                
                WisselOm();
                VoegSamen();
                
                // Inverse permutatiematrix in een rij omzetten
                p.VulInversePermutatie();
                
                // Inversie permutatie uitvoeren op het samengevoegd resultaat
                p.PermuteerInvers(blok64Array, nieuwBlok64Array);
                
                start = einde;
                einde += 8;
                index = 0;
            }
        }
        
        return nieuwBlok64Array;
    }
}

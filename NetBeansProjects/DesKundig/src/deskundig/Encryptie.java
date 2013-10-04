package deskundig;

public class Encryptie {
    
    private byte[][] blok = new byte[8][8];     // 64-bit array
    private String [][] blokString= new String[8][8];
    private static int[] permutatieMatrix = new int[64];
    
    /**
     * Default constructor van de encryptie klasse.
     */
    public Encryptie() { }
    
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
                permutatieMatrix[index] = (int) blok[i][j];
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
}

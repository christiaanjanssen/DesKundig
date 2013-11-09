package deskundig;

public class ExpansieTabel {
    
    //private int[] expansieRij = new int[48];
    private String shiftR[][] = new String[6][8];
    
    /**
     * De runExpasieTabel methode breidt een 32-bit matrix uit naar een
     * 48-bit matrix. Dit gebeurt a.d.h.v. de expansietabel E (zie Matrices.java)
     * 
     * @param invoerR
     * @param uitvoerR 
     */
    public void Exponeren(int[] invoerR, int[] uitvoerR){
        int tmp;
        int i = 0;
        int loop = 0;
        
        while(i < 48){
            tmp = RijE[i];
            if(tmp == loop){
                uitvoerR[i] = invoerR[loop - 1];
                loop = 0;
                i++;
            }
            loop++;
        } 
    }
    
    // Initialisatie van de expansie matrix E in een rij
    private static int[] RijE = {
        32, 1, 2, 3, 4, 5,
        4, 5, 6, 7, 8, 9,
        8, 9, 10, 11, 12, 13,
        12, 13, 14, 15, 16, 17,
        16, 17, 18, 19, 20, 21,
        20, 21, 22, 23, 24, 25,
        24, 25, 26, 27, 28, 29,
        28, 29, 30, 31, 32, 1
    };
}

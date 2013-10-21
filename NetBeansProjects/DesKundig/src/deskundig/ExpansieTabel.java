package deskundig;

public class ExpansieTabel {
    
    private int[] expansieRij = new int[48];
    private String shiftR[][] = new String[6][8];
    
    /**
     * Default construct van de ExpansieTabel Klasse
     */
    public ExpansieTabel() {
        ZetOmNaarRij();
    }
    
    /**
     * Deze functie zet de expansiematrix om in een rij.
     */
    private void ZetOmNaarRij()   {
        int index = 0;
        
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 6; j++){
                expansieRij[index] = Matrices.E[i][j];
                index++;
            }
        } 
    }
    
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
            tmp = expansieRij[i];
            if(tmp == loop){
                uitvoerR[i] = invoerR[loop - 1];
                loop = 0;
                i++;
            }
            loop++;
        }
        
//        int index = 0;
//        for(int j = 0;j < 48; j++) {
//            for(int k = 0; k < 6; k++){
//                for(int l = 0; l < 8; l++){
//                    shiftR[k][l] = Integer.toString(uitvoerR[index]);
//                    index++;
//                }
//            }
//            index = 0;
//        }
    }
}

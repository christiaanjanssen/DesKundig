package deskundig;

public class ExpansieTabel {
    
    private int[] opslag = new int[48];
    private String shiftR[][] = new String[6][8];
    
    /**
     * Default construct van de ExpansieTabel Klasse
     */
    public ExpansieTabel() {
    }
    
    /**
     * Deze functie vult de expansie tabel
     */
    public void VulExpansieTabel()   {
        int index = 0;
        
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 6; j++){
                opslag[index] = Matrices.E[i][j];
                index++;
            }
        } 
    }
    
    /**
     * De runExpasieTabel methode breidt een 32-bit matrix uit naar een
     * 48-bit matrix a.d.h.v. expansietabel E (zie Matrices.java)
     * 
     * @param invoerR
     * @param uitvoerR 
     */
    public void runExpansieTabel(int [] invoerR, int [] uitvoerR){
        int temp = 0;
        int i = 0;
        int loop = 0;
        int controle = 0;
        
        while(controle < 48){
            temp = opslag[i];
            if(temp == loop){
                uitvoerR[controle] = invoerR[loop - 1];
                loop = 0;
                controle++;
                i++;
            }
            loop++;
        }
        
        for(int j = 0;j < 48; j++) {
            System.out.print(uitvoerR[j]);
            
            int index = 0;
            for(int k = 0; k < 6; k++){
                for(int l = 0; l < 8; l++){
                    shiftR[k][l] = Integer.toString(uitvoerR[index]);
                    index++;
                }
            }
            index = 0;
        }
    }
}

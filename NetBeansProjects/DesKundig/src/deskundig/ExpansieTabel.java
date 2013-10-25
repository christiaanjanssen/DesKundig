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
            tmp = Matrices.RijE[i];
            if(tmp == loop){
                uitvoerR[i] = invoerR[loop - 1];
                loop = 0;
                i++;
            }
            loop++;
        }
        
    }
}

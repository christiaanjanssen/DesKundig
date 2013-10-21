package deskundig;

class Permutatie {
    
    private int[] permutatieRij = new int[64]; 
    private String[][] nieuw64bBlock = new String[4][8];
    
    /**
     * Default constructor van de Permutatie-klasse
     */
    public Permutatie() {
        VulPermutatie();
    }
    
    /**
     * De functie zet de waarden van permutatiematrix IP in een rij. Dit wordt
     * opgeslagen in de permutatieRij-variabele.
     */
    private void VulPermutatie()   {
        int index=0;
        for(int rij = 0; rij < 8; rij++){
            for(int kol = 0; kol < 8; kol++){
              permutatieRij[index] = Matrices.IP[rij][kol];
              index++;
            }
        }
    }
    
    /**
     * De functie zet de waarden van de inverse permutatiematrix IP-1 in een rij.
     * Dit wordt opgeslagen in de permutatieRij-variabele.
     */
    public void VulInversePermutatie()   {
        int index=0;
        for(int rij = 0; rij < 8; rij++){
            for(int kol = 0; kol < 8; kol++){
              permutatieRij[index] = Matrices.invIP[rij][kol];
              index++;
            }
        }       
    }
    
    /**
     * Deze functie permuteerd het 64 but input blok met de permutatiematrix IP.
     * 
     * De IP matrix is vantevoren omgezet naar een rij (permutatieRij).
     * 
     * @param perm_in
     * @param perm_out 
     */
    public void Permuteer(int[]perm_in, int[]perm_uit) {
        int tmp;
        int index = 0;
        int loop = 0;
        
        while(perm_in.length != index) {
            tmp = permutatieRij[index];                          
            if(tmp == loop){                             
                perm_uit[index] = perm_in[loop - 1];        
                loop = 0;
                index++;
            }
            loop++;
        }
    }
    
    /**
     * Deze functie permuteerd het 64 but input blok met de inverse 
     * permutatiematrix IP-1.
     * 
     * De IP-1 matrix is vantevoren omgezet naar een rij (permutatieRij).
     * 
     * @param perm_in
     * @param perm_out 
     */
    public void PermuteerInvers(int [] perm_in, int [] perm_out) {
        int temp;
        int index = 0;
        int loop = 0;
        
        while(perm_in.length != index) {
            temp = permutatieRij[index];
            if(temp == loop){
                perm_out[index] = perm_in[loop - 1];
                loop = 0;
                index++;
            }
            loop++;
        }
        
//        index = 0;
//        for(int i = 0; i < 4; i++){
//            for(int j = 0; j < 8; j++){
//                nieuw64bBlock[i][j]=Integer.toString(perm_out[index]);
//                index++;
//            }
//        }       
    }
}
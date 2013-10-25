package deskundig;

class Permutatie {
    
    private String[][] nieuw64bBlock = new String[4][8];
    
     
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
            tmp = Matrices.RijIP[index];                          
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
            temp = Matrices.RijinvIP[index];
            if(temp == loop){
                perm_out[index] = perm_in[loop - 1];
                loop = 0;
                index++;
            }
            loop++;
        }      
    }
}
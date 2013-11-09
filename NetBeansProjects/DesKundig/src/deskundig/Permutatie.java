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
            tmp = RijIP[index];                          
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
            temp = RijInvIP[index];
            if(temp == loop){
                perm_out[index] = perm_in[loop - 1];
                loop = 0;
                index++;
            }
            loop++;
        }      
    }
    
    // Initialisatie van de permutatie matrix IP in een rij
    private static int[] RijIP = {
        58, 50, 42, 34, 26, 18, 10, 2,
        60, 52, 44, 36, 28, 20, 12, 4,
        62, 54, 46, 38, 30, 22, 14, 6,
        64, 56, 48, 40, 32, 24, 16, 8,
        57, 49, 41, 33, 25, 17, 9, 1,
        59, 51, 43, 35, 27, 19, 11, 3,
        61, 53, 45, 37, 29, 21, 13, 5,
        63, 55, 47, 39, 31, 23, 15, 7
    };
    
    // Initialisatie van de inverse permutatie matrix invIP in een rij
    private static int[] RijInvIP = {
        40, 8, 48, 16, 56, 24, 64, 32,
        39, 7, 47, 15, 55, 23, 63, 31,
        38, 6, 46, 14, 54, 22, 62, 30,
        37, 5, 45, 13, 53, 21, 61, 29,
        36, 4, 44, 12, 52, 20, 60, 28,
        35, 3, 43, 11, 51, 19, 59, 27,
        34, 2, 42, 10, 50, 18, 58, 26,
        33, 1, 41, 9, 49, 17, 57, 25
    };
}
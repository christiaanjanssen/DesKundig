package deskundig;

class Permutatie {
    
    private int[] store_num= new int[64]; 
    private String nieuw64bBlock [][]= new String[4][8];
    
    /**
     * Default constructor van de Permutatie-klasse
     */
    public Permutatie() {}
    
    /**
     * De functie zet de permutatiematrix om in een rij.
     */
    public void FillPermutation()   {
        int index=0;
        for(int rij=0; rij<8; rij++){
            for(int kol=0; kol<8; kol++){
              store_num[index] = Matrices.IP[rij][kol];
              index++;
            }
        }
    }
    
    /**
     * De functie zet de inverse permutatiematrix om in een rij.
     */
    public void FillInversePermutation()   {
        int index=0;
        for(int rij = 0; rij < 8; rij++){
            for(int kol = 0; kol < 8; kol++){
              store_num[index] = Matrices.invIP[rij][kol];
              index++;
            }
        }       
    }
    
    /**
     * Deze functie inverteerd de permutatie-array.
     * 
     * @param perm_in
     * @param perm_out 
     */
    public void DoIP(int[]perm_in, int[]perm_out) {
        int temp = 0;
        int i = 0;
        int loop = 0;
        int check = 0;
        
        while(perm_in.length!=check){
            temp=store_num[i];                          //voorbeeld: temp = 5
            if(temp==loop){                             //5 is niet gelijk aan 0, dus deze if wordt overgeslagen tot loop wel 5 is (zie teller)
                perm_out[check]=perm_in[loop-1];        //perm_out op de 0de plaats wordt dan hetzelfde als dat perm_in op de vijfde plaats is.
                loop=0;                                 //de loop wordt terug op 0 gezet
                check++;                                //de check wordt opgehoogd, samen met de teller
                i++;
            }
            loop++;
        }
    }
    
    public void DoInverseIP(int [] perm_in, int [] perm_out) {
        int temp=0;
        int i=0;
        int loop=0;
        int check=0;
        
        while(perm_in.length!=check){//hier wordt weer precies hetzelfde gedaan als in de vorige while
            temp=store_num[i];
            if(temp==loop){
                perm_out[check]=perm_in[loop-1];
                loop=0;
                check++;
                i++;
            }
            loop++;
        }
       int index=0;
            for(int d=0; d<4;d++){
            for(int j=0; j<8; j++){
                nieuw64bBlock[d][j]=Integer.toString(perm_out[index]);
                index++;
            }
        }       
    }
}
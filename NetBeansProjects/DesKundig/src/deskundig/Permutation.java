package deskundig;
class Permutation {
    public Permutation() {}
    
    public void FillPermutation()   {
        int index=0;
        for(int rij=0; rij<8; rij++){
            for(int kol=0; kol<8; kol++){
              store_num[index]=permutatie[rij][kol];//alle cijfers in de permutatie-array worden hier in 1 rij gezet, dus van 2D naar 1D.
              index++;
            }
        }
       
    }
    public void FillInversePermutation()   {
        int index=0;
        for(int rij=0;rij<8; rij++){
            for(int kol=0; kol<8; kol++){
              store_num[index]=invperm[rij][kol];//zelfde functie als vorige, alleen wordt deze op de inverse permutatie toegepast
              index++;
            }
        }       
    }
    
    public void DoIP(int[]perm_in, int[]perm_out) {
        //deze functie gaat de normale permutatie-array, omgekeerd terug erin zetten (dus inverteren)
        int temp=0;
        int i=0;
        int loop=0;
        int check=0;
        
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
                newBlock64[d][j]=Integer.toString(perm_out[index]);
                index++;
            }
        }       
    }
    private int[] store_num= new int[64];
    
    private int permutatie[][]=
    {{58,50,42,34,26,18,10,2},
    {60, 52, 44 ,36, 28, 20, 12 ,4},
    {62 ,54 ,46 ,38 ,30 ,22 ,14 ,6},
    {64 ,56, 48, 40, 32, 24 ,16, 8},
    {57 ,49 ,41 ,33 ,25 ,17 ,9 ,1},
    {59 ,51, 43 ,35 ,27 ,19 ,11, 3},
    {61 ,53 ,45, 37, 29 ,21 ,13 ,5},
    {63 ,55 ,47 ,39 ,31 ,23, 15, 7}};
    
    private int invperm[][]=
    {{40 , 8 ,48, 16, 56, 24, 64, 32},
     {39, 7, 47, 15, 55 ,23, 63, 31},
     {38 ,6 ,46 ,14, 54, 22 ,62, 30},
     {37 ,5 ,45, 13, 53 ,21, 61, 29},
     {36 ,4 ,44 ,12 ,52 ,20 ,60 ,28},
     {35 ,3 ,43 ,11, 51, 19 ,59 ,27},
     {34 ,2 ,42, 10 ,50, 18 ,58, 26},
     {33, 1 ,41, 9 ,49, 17, 57, 25}};
    
    private String newBlock64 [][]= new String[4][8];
}
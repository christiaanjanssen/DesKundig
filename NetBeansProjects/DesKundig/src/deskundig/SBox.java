package deskundig;

public class SBox {
    private int[] func_out=new int[32];
    private int[] opslag = new int[32];
    private int[] naSBox = new int[32];
    private int index = 0;
    private int eersteIndex = 0;
    private int tweedeIndex = 0;
    private int rij = 0;
    private int kolom = 0;
    private int[] resultaatSBox = new int[8];
    private int[] rijSbox = new int[8];
    private int[] kolomSBox = new int[8];
    private int[] eersteDec = new int[2];
    private int[] tweedeDec = new int[4];
    private int[] temp = new int[6];
    
    /**
     * Default construct van de SBox Klasse
     */
    public SBox() {
    }
    
    /**
     * 
     * 
     * @param input 
     */
    public void DoDecimal(int[] input) {
        eersteDec[0] = input[0];
        eersteDec[1] = input[5];
        rij = 1 * eersteDec[1] + 2 * eersteDec[0];
    }
    
    
    public void DoFourDecimal(int[] num) {
        tweedeDec[0] = num[1];
        tweedeDec[1] = num[2];
        tweedeDec[2] = num[3];
        tweedeDec[3] = num[4];
        kolom = 1 * tweedeDec[3] + 2 * tweedeDec[2] + tweedeDec[1] * 4 + tweedeDec[0] * 8;
    }
    
    public void SelecteerSBox(int keuze, int rij, int kolom) { 
        switch(keuze){
            case 0: 
                resultaatSBox[eersteIndex] = Matrices.sBox1[rij][kolom]; 
                eersteIndex++; 
                break;
            case 1: 
                resultaatSBox[eersteIndex] = Matrices.sBox2[rij][kolom]; 
                eersteIndex++; 
                break;
            case 2: 
                resultaatSBox[eersteIndex] = Matrices.sBox3[rij][kolom]; 
                eersteIndex++; 
                break;
            case 3: 
                resultaatSBox[eersteIndex] = Matrices.sBox4[rij][kolom]; 
                eersteIndex++; 
                break;
            case 4: 
                resultaatSBox[eersteIndex] = Matrices.sBox5[rij][kolom]; 
                eersteIndex++; 
                break;
            case 5: 
                resultaatSBox[eersteIndex] = Matrices.sBox6[rij][kolom]; 
                eersteIndex++; 
                break;
            case 6: 
                resultaatSBox[eersteIndex] = Matrices.sBox7[rij][kolom]; 
                eersteIndex++; 
                break;
            case 7: 
                resultaatSBox[eersteIndex] = Matrices.sBox8[rij][kolom]; 
                eersteIndex++; 
                break;
        }  
    }
    
    /**
     * De methode zorgt voor ene omzetting naar 32 bits
     * 
     * @param num 
     */
    public void naar32Bits(int getal) {
        int getal2, getal3;

        for(int i = 0; i < 4; i++) {
            getal2 = getal % 2;
            getal3 = getal / 2;
            getal = getal3;
            naSBox[tweedeIndex] = getal2;
            tweedeIndex++;  
        }     
    }
    
    public void KeerOm(int[] num){
        int count = 0;
        int fix = 3;
        int tmp;
        
        while(count < 32){
            for(int i = 0; i < 2; i++){
                
                tmp = num[count + i];
                
                num[count + i] = num[fix - (count + i)];
                num[fix - (count + i)] = tmp;
            }
            
            fix += 8;
            count += 4;
        }  
    }
    
    
    public void VulP() {
        int i = 0;
        
        for(int row = 0; row < 8; row++) {
            for(int col = 0; col < 4; col++){
                opslag[i] = Matrices.P[row][col];
                i++;
            }
        }
    }
    
    public void runP(int[] naSBox, int[] func_out) {
        int tmp = 0;
        int i = 0;
        int loop = 0;
        int check = 0;
        while(check < 32){
            tmp = opslag[i];
            if(tmp == loop){
                func_out[check] = naSBox[loop - 1];
                loop = 0;
                check++;
                i++;
            }
            loop++;
        }
    }
    
    public void runSBox(int[] XOR_Out, int[] S_Out){
        int count = 0;
        int keuze = 0;
        int i;
        index = 0;
        
        while(count < 48){
            for(i = 0; i < 6; i++) {
                temp[i] = XOR_Out[i + count]; 
            }
            
            DoDecimal(temp);
            DoFourDecimal(temp);
            rijSbox[index] = rij;
            kolomSBox[index] = kolom;
            SelecteerSBox(keuze, rijSbox[index], kolomSBox[index]);
            naar32Bits(resultaatSBox[index]);
                        
            index++;
            keuze++;
            count += 6;
        }
        KeerOm(naSBox);
        
        index = 0;

        VulP();
        runP(naSBox,func_out);
        
        for(int j = 0; j < 32; j++) {
            S_Out[j] = func_out[j];
        }
    } 
}

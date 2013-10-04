package deskundig;

//Deze code is op dit moment aangepast van lijn 1 tot en met lijn 351 door Michael Eerdekens
//dit is de klasse om de tekst om te zetten in een gecodeerde string, aan de hand van een key.
//als er nog commentaar bij kan (tot nu toe) bij de aangepaste lijnen, voeg maar toe.

public class encrypt {
    
    public encrypt(  String Plaintext, String Key) {
        losseTekst=Plaintext;
        sleutelwoord=Key;  
    }
    
    public String LengteCheck(String achttest){
        //eerst kijken of de tekst deelbaar is door 8 (wat MOET!)
        if(achttest.length()%8!=0){
            int lengte=8-achttest.length()%8;            
            for(int i=0; i<lengte; i++)
                achttest=achttest.concat("*");
                //als het niet zo is, voegen we sterretjes toe tot het wel deelbaar is.
        }
        else{ 
            return achttest;
        }        
       
        return achttest;        
    }   
    
    public void VercijferTekst(String tekst) {
        //zorgen dat dit per 8 bits gebeurt, en we niet VERDER gaan dan de tekst zelf
        for(int i=0; i<8 && i<tekst.length(); i++) {
            blok[i]=getBinaryBits(tekst.charAt(i));
            //dit geeft een 2D-array terug van 8 op 8 
        }
        
        int index=0;
        for(int i=0; i<8; i++){
            for(int j=0; j<8;j++){
                perm[index]=(int)blok[i][j];
                index++;                
            }
        }
    }    
    
    public void VercijferKey(){ 
        //hier gaan we, voor elk karakter, de binaire waarde ervan ophalen.
        for(int i=0; i<8 && i<sleutelwoord.length(); i++){
            blok[i]=getBinaryBits(sleutelwoord.charAt(i));        
        }
        
        int index=0;
        for(int i=0; i<8; i++){
            for(int j=0; j<8;j++){
                //hier worden de bits omgezet naar ints, en achter elkaar in de key_in geplaatst.
                key_in[index]=(int)blok[i][j];
                index++;               
            }
        }
    }
    
    //deze functie dient om de binaire bits op te halen.
    public byte[] getBinaryBits(int ch) {
        byte[] bin=new byte[8];
        int tag=1;
        for(int i=0; i<8; i++) {
            bin[7-i]=(byte)((ch&((tag<<i)))>>i); //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        }
        return bin;
    }
    
    public void DoSegmentation(int[] perm_out){
        //in deze functie wordt de input in twee gesplitst, de helft gaat naar links en de andere helft naar rechts.
        int index=0;
        for(int i=0; i<32; i++)
            links[i]=perm_out[i];
        
        for(int i=32; i<64; i++){
            rechts[index]=perm_out[i];            
            index++;
        }
        index=0;       
    }
    
    public void XOR(int side1[], int [] side2,int [] result){
        //deze functie gaat de beide kanten met elkaar XORren en zo het resultaat aanpassen.
        int index=0;
        for(int i=0; i<side1.length; i++){
            
            if(side1[i]==side2[i])
                result[index]=0;
            else
                result[index]=1;
            index++;            
        }
    }
    
    public void FillC_D(){
        //we werken met 56 bits, de helft daarvan is 28
        int index=28;
        for(int i=0; i<28; i++)
            linksEnRechts[i]=linkseHelft[i];
            //de eerste 28 komen van C
        for(int i=0; i<28; i++){
            linksEnRechts[index]=rechtseHelft[i];
            //de laatste 28 komen van D
            index++;
        }
    }
    
    public void samenvoegen() {
        //ongeveer dezelfde uitleg als voorgaande functie
        int index=32;
        for(int i=0; i<32; i++)
            Blok64Array[i]=links[i];
        
        for(int i=0; i<32; i++){
            Blok64Array[index]=rechts[i];
            index++;
        }
    }
    
    public void omdraaien() {
        int temp;
        for(int i=0;i<32;i++) {
            //links en rechts wisselen m.b.v. een tijdelijke tussenvariabele
            temp=links[i];
            links[i]=rechts[i];
            rechts[i]=temp;
        }
        
        ind=0;
        //naar strings parsen zodat we deze ook kunnen printen
        for(int i=0; i<4;i++){
            for(int j=0; j<8; j++){
                linkseKant[i][j]=Integer.toString(links[ind]);
                rechtseKant[i][j]=Integer.toString(rechts[ind]);
                ind++;
            }
        }
        ArrayPrinter.printArray(linkseKant,"Left Part");
        ArrayPrinter.printArray(rechtseKant,"Right Part");
        DesPanel.StepsText.append("****************************************************************"+'\n');

        ind=0;
    }
    
    public static int[] getByteFromBits(int bits64[]) {
        int index=0;
        System.out.println();//nieuwe lijn
        
        for(int i=0;i<8;i++)
            //we zetten de bits64 array om naar decimale ints en voegen deze toe aan de ch-array achteraan
            for(int j=1; j<=8; j++){
            ch[i]+=(int)Math.pow(2,(8-j))*bits64[index];
            //de bits worden vermenigvuldigd met 2^8- plaats in het array, dus de decimale waarde
            index++;
            }        
        return ch;
    }
    
    public void KiesShift(int choice){
        if(choice==1){
            //resultaten voor en na de left shift laten zien
            
            System.out.println("Voor 1 left-shift: " + index);
            for(int j=0; j<linkseHelft.length; j++)
                System.out.print(linkseHelft[j]);
            
            key.Do_OneLeftShift(linkseHelft,rechtseHelft);
            System.out.println();//lege lijn
            System.out.println("Na 1 left-shift: " + index);
            for(int j=0; j<linkseHelft.length; j++)
                System.out.print(linkseHelft[j]);
        }
        //meer dan 1 left shift per keer
        else{            
            for(int j=0; j<linkseHelft.length; j++){
                System.out.print(linkseHelft[j]);
            }
            key.Do_OneLeftShift(linkseHelft,rechtseHelft);
            key.Do_OneLeftShift(linkseHelft,rechtseHelft);
            System.out.println(" Links= ");
            
            for(int j=0; j<linkseHelft.length; j++)
                System.out.print(linkseHelft[j]);
                //C printen (linkse kant dus)
            System.out.println(" Rechts= ");
            
            for(int j=0; j<rechtseHelft.length; j++)
                System.out.print(rechtseHelft[j]);
                //D printen (rechtse kant dus)
        }        
    }
    
    public void Ontcijfer() {
        DesPanel.StepsText.append("************************Ontcijferen***********************************"+'\n');
        int begin=0;      
        int teller=0;
        int einde=64;
        for(int f=0; f<losseTekst.length()/8; f++){            
            int Round=1;
            for(int h=begin; h<einde; h++ ){
                /*we maken een nieuwe blok aan van integers met daarin de geëncrypteerde boodschap*/
                nieuwBlok64Array_[teller]=Integer.parseInt(eindVercijfering.substring(h,h+1));
                teller++;
            }
            
            DesPanel.StepsText.append("********* Block Number *********"+(f+1)+'\n');
            ind=0;
            DesPanel.StepsText.append("********* Cipher Block In Round ********* "+ Round+'\n');
            for(int d=0 ; d<8; d++){
                //blok van 8x8 aanmaken, waarin de stringversies van de nieuwBlok64Array komen te zitten.
                for(int j=0; j<8; j++){
                    nieuwBlok64String[d][j]=Integer.toString(nieuwBlok64Array[ind]);
                    ind++;
                }
            }
            
            //NOG OPZOEKEN!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            ArrayPrinter.printArray(nieuwBlok64String, "Cipher Block");
            p = new Permutation();
            p.FillPermutation();//van 2D naar 1D gaan
            p.DoIP(nieuwBlok64Array_,perm_out);
            DoSegmentation(perm_out);
            
            int adder=48;
            
            while(Round<=16){ //Deze stap doen we 16 keer
                DesPanel.StepsText.append("*********Nummer********* "+Round+'\n');
                for(int i=0; i<48; i++) {
                    omgekeerdeSleutel[i]=Integer.parseInt(omgekeerdeSleutelString.substring((omgekeerdeSleutelString.length()-adder)+i,(omgekeerdeSleutelString.length()-adder)+i+1));
                    //van string naar gewone integersleutel gaan
                }
                
                System.out.println();//lege lijn
                ExpansieTabel e = new ExpansieTabel();
                e.VulExpansieTabel();
                e.runExpansieTabel(rechts, Right_out);
                DesPanel.StepsText.append("*********Rechtse XOR********* "+Round+'\n');
                XOR(Right_out,omgekeerdeSleutel,XorOutput);
                ind=0;
                for(int g=0; g<6; g++){
                    for(int j=0; j<8; j++){
                        XORS[g][j]=Integer.toString(XorOutput[ind]);
                        ind++;
                    }
                }
                
                ArrayPrinter.printArray(XORS,"XOR Result");
                ind=0;
                SBox sbox= new SBox();                
                sbox.runSBox(XorOutput,naSBOX);//step2 32bits - include permitation
                DesPanel.StepsText.append("*********Linkse XOR********* "+Round+'\n');
                XOR(links,naSBOX,naXor);//XOR
                
                for(int g=0; g<4; g++){
                    for(int j=0; j<8; j++){
                        naXorString[g][j]=Integer.toString(naXor[ind]);
                        ind++;
                    }
                }
                
                ind=0;
                ArrayPrinter.printArray(naXorString,"XOR Resultaat");//na het xorren de array opnieuw printen
                adder=adder+48;
                Round++;
                DesPanel.StepsText.append("*********Links/Rechts wisselen*********"+'\n');
                for(int i=0;i<32;i++) {
                    links[i]=rechts[i];
                    rechts[i]=naXor[i];
                }
            }
            
            ind=0;
            for(int g=0; g<4; g++){
                for(int j=0; j<8; j++){
                    linkseKant[g][j]=Integer.toString(rechts[ind]);
                    rechtseKant[g][j]=Integer.toString(naXor[ind]);
                    ind++;
                }
            }
            ArrayPrinter.printArray(linkseKant,"Linkse helft");
            ArrayPrinter.printArray(rechtseKant,"Rechtse helft");
            //beide helften printen na de wissel
            DesPanel.StepsText.append("********Na wisselen********* " +'\n');
            omdraaien();
            samenvoegen();
            System.out.println("/nBit Swap :");
            
            for(int i=0;i<64;i++) {
                System.out.print(Blok64Array[i]);
            }
            
            p.FillInversePermutation();//van 2D naar 1D
            p.DoInverseIP(Blok64Array,nieuwBlok64Array);            
            System.out.println("Ontcijferingscode: ");
            
            for(int i=0; i<64; i++) {
                if(binDec==null)
                    binDec=Integer.toString(nieuwBlok64Array[i]);
                else
                    binDec+=Integer.toString(nieuwBlok64Array[i]);
                System.out.print(nieuwBlok64Array[i]);
            }
            
            System.out.println(" ");
            ch=getByteFromBits(nieuwBlok64Array);
            
            
            for(int i=0; i<8; i++){
                System.out.print((char)ch[i]);
                if(eindOntcijfering==null)//dus de eerste keer in deze lus
                    eindOntcijfering=Character.toString((char)ch[i]);
                else //bij de volgende 7 keer
                    eindOntcijfering+=Character.toString((char)ch[i]);
            }
            
            for(int i=0; i<8; i++)
            ch[i]=0;
            begin=einde;
            einde=einde+64;
            teller=0;
        }
    }
    
    public String getBinDec(){
        return binDec;
    }
    
    public void DoEncryption(){
        DesPanel.StepsText.append("************************CIJFER***********************************"+'\n');
        VercijferKey();//sleutel vercijferen
        int start=0;
        int end=8;
        losseTekst=LengteCheck(losseTekst);
//        System.out.println("PlainTETX: "+ PlainText.substring(start,start+9));
        
        String temp;
        for(int f=0; f<losseTekst.length()/8; f++){
            temp=losseTekst.substring(start,end);
            VercijferTekst(temp);
            System.out.println("The Orginal Massage code: ");
            for(int i=0;i<64;i++) {
                if(binCi==null)
                    binCi=Integer.toString(perm[i]);
                else
                    binCi+=Integer.toString(perm[i]);
                System.out.print(perm[i]);
            }
            
            int Round=1;
            DesPanel.StepsText.append("*********Block Number *********"+(f+1)+'\n');
            System.out.println(" ");
            Permutation p= new Permutation();
            p.FillPermutation();//step1 from 2D to 1D
            p.DoIP(perm,perm_out);//step1 -array
            DoSegmentation(perm_out);//step2 to Left and Right
            // Here u will work on Right (32 Bits ) & Left 32 Bits
            ////////////////////////////////////////////////////////////////////////
            /////////////////////////////key Generation////////////////////////////
            
            key.FillPC_1();//step1 -array
            System.out.println();
            DesPanel.StepsText.append("*********Key Generation*********" +'\n');
            key.DoPC_1(key_in,key_out);//step1
            
            key.DoSegementation(key_out,linkseHelft,rechtseHelft);//step2
            
            while(Round<=16) {
                DesPanel.StepsText.append("*********Round Number********* "+Round+'\n');
                
                int chooser=num_Left[index_kiezer];
                ExpansieTabel e = new ExpansieTabel();
                e.VulExpansieTabel();
                e.runExpansieTabel(rechts,Right_out);//step3
                
                ////////////////////////////////////////////////////////////////////////
                System.out.println();
                KiesShift(chooser);//step 3 - shift
                System.out.println("Chooser = "+chooser);
                key.FillPC_2();//step4 array
                FillC_D();//step4 contacnate 56bits
                DesPanel.StepsText.append("*********Key For Round Number********* "+Round+'\n');
                key.DoPC_2(linksEnRechts,eindsleutel);//step4 here the key of Round 1
                for(int i=0; i<48;i++) {
                    if(omgekeerdeSleutelString==null)
                        omgekeerdeSleutelString=Integer.toString(eindsleutel[i]);
                    else
                        omgekeerdeSleutelString+=Integer.toString(eindsleutel[i]);
                    
                }
                
                
                ////////////////////////////////////////////////////////////////////////
                ////////////////////////////////////////////////////////////////////////
                DesPanel.StepsText.append("*********Right Part XORED with Round Key********* "+Round+'\n');
                XOR(Right_out,eindsleutel,XorOutput);//step1
                ind=0;
                for(int g=0; g<6; g++){
                    for(int j=0; j<8; j++){
                        XORS[g][j]=Integer.toString(XorOutput[ind]);
                        ind++;
                        
                    }
                }
                ind=0;
                
                ArrayPrinter.printArray(XORS,"XOR Result");
                System.out.print("\nXOR :");
                for(int j=0;j<48;j++) {
                    System.out.print(XorOutput[j]);
                }
                SBox sbox= new SBox();
                
                sbox.runSBox(XorOutput,naSBOX);//step2 32bits - include permitation
                System.out.print("\nS-BOX :");
                for(int j=0;j<32;j++) {
                    System.out.print(naSBOX[j]);
                }
                System.out.print("\n AFter XOR  :");
                DesPanel.StepsText.append("*********Left Part XORED with Output Function in Round********* "+Round+'\n');
                XOR(links,naSBOX,naXor);//XOR
                
                
           
                for(int g=0; g<4; g++){
                    for(int j=0; j<8; j++){
                        naXorString[g][j]=Integer.toString(naXor[ind]);
                        ind++;
                        
                    }
                }
                ind=0;
                ArrayPrinter.printArray(naXorString,"XOR Result");
                
                for(int j=0;j<32;j++) {
                    System.out.print(naXor[j]);
                }
                
                
                Round++;
                index++;
                index_kiezer++;
                
                DesPanel.StepsText.append("*********Left=Right & Right=Left*********"+'\n');
                for(int i=0;i<32;i++) {
                    links[i]=rechts[i];
                    rechts[i]=naXor[i];
                }
                
                ind=0;
                for(int g=0; g<4; g++){
                    for(int j=0; j<8; j++){
                        linkseKant[g][j]=Integer.toString(rechts[ind]);
                        rechtseKant[g][j]=Integer.toString(naXor[ind]);
                        ind++;
                        
                    }
                }
                ArrayPrinter.printArray(linkseKant,"Left Part");
                ArrayPrinter.printArray(rechtseKant,"Right Part");
                
                
            }
            
            DesPanel.StepsText.append("********After Swap Operation*********"+'\n');
            omdraaien();
            
            samenvoegen();
            System.out.println("/nBit Swap :");
            for(int i=0;i<64;i++) {
                System.out.print(Blok64Array[i]);
            }
            
            p.FillInversePermutation();//step1 from 2D to 1D
            DesPanel.StepsText.append("********* Inverse Permutation Operation in Round *********"+(f+1)+'\n');
            p.DoInverseIP(Blok64Array,nieuwBlok64Array);//step1 -array
            System.out.println(" ");
            System.out.println("/nThe Encyption code: ");
            for(int i=0; i<nieuwBlok64Array.length; i++){
                System.out.print(nieuwBlok64Array[i]);
                if(eindVercijfering==null)
                    eindVercijfering=Integer.toString(nieuwBlok64Array[i]);
                else
                    eindVercijfering+=Integer.toString(nieuwBlok64Array[i]);
                
            }
            start=end;
            end=end+8;
            index_kiezer=0;
            
        }
        System.out.println("");
        System.out.println("Final :"+ eindVercijfering);
        
        
        
    }
    public String getEncryption(){
        return eindVercijfering;
    }
    
    public String getDecryption(){
        return eindOntcijfering;
    }
    public String getBinCi(){
        return binCi;
    }
    
    private int ind=0;
    private String binDec;
    private String binCi;
    
    private  static Permutation p;
    private String sleutelwoord;
    private int index_kiezer=0;
    private String losseTekst;
   
    private static int[] rechts=new int[32];
    private static int[] Right_out=new int[48];
    private static int[] perm=new int[64];
    private static int[] perm_out= new int[64];
    private static int[]num_Left= {1,1,2,2,2,2,2,2,1,2,2,2,2,2,2,1};
    private static int[] linksEnRechts=new int[56];
    private static int[] naSBOX=new int[32];
    private static int[] XorOutput=new int[48]; 
    private static int[] links=new int[32];
    private static int[] omgekeerdeSleutel=new int[48];
    private static int[] naXor=new int[32];

    private static int  nieuwBlok64Array_[]=new int[64];
    private static int linkseHelft[]=new int[28];
    private static int rechtseHelft[]=new int[28];
    private static int Blok64Array[]=new int[64];
    private static int ch[]=new int[8];
    private static int nieuwBlok64Array[]=new int[64];
    private static int index=0;
    
    private static String omgekeerdeSleutelString ;
    private static  KeyGen key=new KeyGen();
    
  
    private static int key_in[]=new int[64];
    private static int eindsleutel[]=new int[48];
    private static int key_out[]=new int[56];
   
    private static String [][] naXorString=new String[4][8];
   
    
    private byte[][] blok=new byte[8][8];
    private String [][] blokString= new String[8][8];
    private String eindVercijfering;
    private String eindOntcijfering;
    private String linkseKant[][] = new String[4][8];
    private String rechtseKant[][] = new String[4][8];
    private String XORS[][] = new String[6][8];
    private String [][] nieuwBlok64String = new String[8][8];
}
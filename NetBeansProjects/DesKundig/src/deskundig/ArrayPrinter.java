package deskundig;

public class ArrayPrinter {
    
    /**
     * Default constructor voor de ArrayPrinter-klasse
     */
    public ArrayPrinter() { }
    
    /**
     * Deze functie drukt een matrix af
     * 
     * @param arr
     * @param label 
     */
    public static void printArray(String[][] arr,String label) {
        DesPanel.StepsText.append("-- "+label+" -- "+'\n');
        for(int i=0;i<arr.length ;i++) {         
            DesPanel.StepsText.append("| ");
            
            for(int j=0;j<arr[0].length;j++) {  
                 DesPanel.StepsText.append(arr[i][j]+" ");
            }
            DesPanel.StepsText.append("| "+'\n');
        }
    }
}
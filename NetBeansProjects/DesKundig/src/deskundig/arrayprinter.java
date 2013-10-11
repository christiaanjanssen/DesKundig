package deskundig;

/*
 *   *       Please Visit us at www.codemiles.com     *
 *  This Program was Developed by www.codemiles.com forums Team
 *  *           Please Don't Remove This Comment       *
 */

public class arrayprinter {
    
    /** Creates a new instance of arrayprinter */
    public arrayprinter() {
    }
    public static void printarray(String[][] arr,String label) {
       System.out.println("-- "+label+" -- "+'\n');
        //System.out.println("-- "+label+" -- ");
        for(int i=0;i<arr.length ;i++) {
              System.out.println("| ");
            for(int j=0;j<arr[0].length;j++) {
                
                 System.out.println(arr[i][j]+" ");
            }
              System.out.println("| "+'\n');
        }
    }
}
/*
 *   *       Please Visit us at www.codemiles.com     *
 *  This Program was Developed by www.codemiles.com forums Team
 *  *           Please Don't Remove This Comment       *
 */

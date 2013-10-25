
package deskundig;

public class ThreadResult {
    private int[] result;
    private int stap;
    private Keys[] sleutels;
    
    public ThreadResult(Keys[] inSleutels){
        this.stap = 0;
        this.sleutels = inSleutels;
    }
    
    public int getStap(){
        return stap;
    }
    
    public void StapUp(){
        this.stap++;
    }

    public void setStap(int stap) {
        this.stap = stap;
    }

    public int[] getResult() {
        return result;
    }

    public void setResult(int[] result) {
        this.result = result;
    }

    public Keys getSleutels(int index) {
        return sleutels[index];
    }    
}

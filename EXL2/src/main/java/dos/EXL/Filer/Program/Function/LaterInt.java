package dos.EXL.Filer.Program.Function;

public class LaterInt {

    // A class that is to store an int that will be determined later than when this class is add to Variable


    public LaterInt(int n){
        num = n;
    }

    public LaterInt(){
        num = 0;
    }

    private int num; 

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
    
}

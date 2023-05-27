package dos.EXL.Types;

public class MyError {

    private int line;
    private int Token;
    private int errorNumber; 
    private String message;

    public MyError(String message){
        line = 0;
        Token = 0;
        errorNumber = 0;
        this.message = message;
    }
    
}

package dos.EXL.Types.Errors;

import dos.EXL.Types.MyError;

public class ParserError implements MyError{

    int code; 
    String message;

    public ParserError(String message, int code){
        this.message = message;
        this.code = code;
    }

    @Override
    public String toErrorMessage() {
        return "";
    }

    
}
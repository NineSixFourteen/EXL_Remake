package dos.EXL.Types.Errors;

import dos.EXL.Types.MyError;

public class LogicError implements MyError{

    int code; 
    String message;

    public LogicError(String message, int code){
        this.message = message;
        this.code = code;
    }

    @Override
    public String toErrorMessage() {
        return "";
    }

    @Override
    public String getFullErrorCode() {
        return "L" + code;
    }

    @Override
    public int getErrorCode() {
        return code;
    }

    
}

package dos.EXL.Types.Errors;

import dos.EXL.Types.MyError;

public class ErrorFactory {

    public static MyError makeLogic(String nessage, int code){
        return new LogicError(nessage, code);
    }

    public static MyError makeParser(String nessage, int code){
        return new ParserError(nessage, code);
    }

    
}

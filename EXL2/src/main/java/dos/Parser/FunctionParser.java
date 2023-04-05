package dos.Parser;

import java.util.List;

import dos.Parser.Builders.FunctionBuilder;
import dos.Tokenizer.Types.Token;
import dos.Types.Function;
import dos.Util.Result;

public class FunctionParser {

    public static Result<Function, Error> getFunction(List<Token> tokens){
        FunctionBuilder fb = new FunctionBuilder();
        Result<Function, Error> res = new Result<>();
        
        return null;
    }
    
}

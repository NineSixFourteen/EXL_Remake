package dos.Parser;

import java.util.List;

import dos.Tokenizer.Types.Token;
import dos.Types.Program;
import dos.Types.Tag;
import dos.Util.Result;

public class ProgramParser {
    
    public static Result<Program,Error> toClass(List<Token> tokens){
        Tag t;
        switch(tokens.get(0).getType()){
            case Public:
                t = Tag.Public;
                break;
            case Private:
                t = Tag.Private;
                break;
            case Class:
                break;
            default:
                Result<Program,Error> res = new Result<>();
                res.setError(new Error("Expected differn't token at start"));
                return res;
        }
        return null;
    }

}

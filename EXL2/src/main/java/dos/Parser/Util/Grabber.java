package dos.Parser.Util;

import java.util.List;

import org.javatuples.Pair;

import dos.Tokenizer.Types.Token;
import dos.Tokenizer.Types.TokenType;
import dos.Util.Result;

public class Grabber {

    public static Result<Pair<List<Token>, Integer>,Error> grabBracket(List<Token> tokens, int point){
        int start = point;
        Token t = tokens.get(point);
        Result<Pair<List<Token>, Integer>,Error> res = new Result<>();
        TokenType ty;
        switch(t.getType()){
            case LBrace:
                ty = TokenType.RBrace;
                break;
            case LSquare:
                ty = TokenType.RSquare;
                break;
            case LThan:
                ty = TokenType.GThan;
                break;
            case LBracket:
                ty = TokenType.RBracket;
                break;
            default:
                res.setError(new Error(t + " is not a valid bracket"));
                return res;
        } 
        point++;
        int open = 0;
        while(point < tokens.size()){
            if(tokens.get(point).getType() == ty){
                if(open == 0){
                    res.setValue(new Pair<List<Token>,Integer>(tokens.subList(start, point), point));
                } else {
                    open--;
                }
            } else if(tokens.get(point).getType() == t.getType()){
                open++;
            }
            point++;
        }
        res.setError(new Error("Could not find matching closing bracket for " + t));
        return res;
    }
    
}

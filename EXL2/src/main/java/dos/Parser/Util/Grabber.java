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

    public static Result<Pair<List<Token>, Integer>, Error> grabFunction(List<Token> tokens, int point){
        int start = point;
        while(tokens.get(point).getType() != TokenType.LBrace){
            point++;
        }
        Result<Pair<List<Token>, Integer>, Error> res = new Result<>();
        Result<Pair<List<Token>, Integer>,Error> body = grabBracket(tokens, point);
        if(body.hasValue()){
            int end = body.getValue().getValue1();
            res.setValue(new Pair<List<Token>,Integer>(tokens.subList(start, end), end));
        } else {
            res.setError(body.getError());
        }
        return res;
    }

    public static Result<Pair<List<Token>, Integer>, Error> grabLine(List<Token> tokens, int point){
        Result<Pair<List<Token>, Integer>, Error> res = new Result<>();
        int start = point;
        while(point < tokens.size()){
            if(tokens.get(point).getType() == TokenType.SemiColan){
                res.setValue(new Pair<List<Token>,Integer>(tokens.subList(start, point), point));
                return res;
            } else{
                point++;
            }
        }
        res.setError(new Error("Cant find ending of line"));
        return res;
    }

    public static Result<Pair<List<Token>, Integer>,Error> grabNextLine(List<Token> tokens, int point){
        int start = point;
        Result<Pair<List<Token>, Integer>,Error> res = new Result<>();
        while((tokens.get(point).getType() != TokenType.SemiColan || tokens.get(point).getType() != TokenType.LBrace) && point < tokens.size()){
            if(tokens.get(point).getType() == TokenType.LBracket){
                while(tokens.get(point).getType() != TokenType.RBracket && point < tokens.size()){
                    point++;
                }
            }
            point++;
        }
        if(point >= tokens.size()){
            res.setError(new Error("Could not find the end of line this line for " + tokens));
            return res;
        }
        switch(tokens.get(point).getType()){
            case SemiColan:
                res.setValue(new Pair<List<Token>,Integer>(tokens.subList(start, point), point));
                break;
            case LBrace:
                Result<Pair<List<Token>, Integer>,Error> brace = grabBracket(tokens, point);
                if(brace.hasError()){res.setError(brace.getError());return res;}
                point = brace.getValue().getValue1();
                res.setValue(new Pair<List<Token>,Integer>(tokens.subList(start, point),point ));
                break;
            default:
                res.setError(new Error("HOW THE FUDGE DID THIS HAPPEN " + tokens));
        }
        return res;
    }
    
}

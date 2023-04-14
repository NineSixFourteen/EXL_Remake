package dos.Parser.Expressions;

import java.util.List;

import org.javatuples.Pair;

import dos.Tokenizer.Types.Token;
import dos.Types.Expression;
import dos.Util.Result;

public class ValueParser {

    public static Result<Pair<Expression, Integer>, Error> parseValue(List<Token> tokens, int point){
        switch(tokens.get(point).getType()){
            case ValueInt:
                return parseInt(tokens, point);
            case ValueChar:
                return parseChar(tokens, point);
            case ValueFloat:
                return parseFloat(tokens, point);
            case Value:
                if(point + 1 < tokens.size()){
                    switch(tokens.get(point + 1).getType()){
                        case LBrace:
                            parseFuncCall(tokens, point);
                        case Dot:
                            parseObject(tokens, point);
                        default: 
                            
                    }
                } else {
                    return parseVar(tokens, point);
                }
                return parseInt(tokens, point);
            case ValueString:
                return parseString(tokens, point);
        }
    } 

    private static Result<Pair<Expression, Integer>, Error> throwError(String error){
        Result<Pair<Expression, Integer>, Error> res = new Result<>();
        res.setError(new Error(error));
        return res;
    }

    private static Result<Pair<Expression, Integer>, Error> parseInt(List<Token> tokens, int point){
        return null;
    } 

    private static Result<Pair<Expression, Integer>, Error> parseChar(List<Token> tokens, int point){
        return null;
    } 

    private static Result<Pair<Expression, Integer>, Error> parseFloat(List<Token> tokens, int point){
        return null;
    } 

    private static Result<Pair<Expression, Integer>, Error> parseString(List<Token> tokens, int point){
        return null;
    } 

    private static Result<Pair<Expression, Integer>, Error> parseVar(List<Token> tokens, int point){
        return null;
    } 

    private static Result<Pair<Expression, Integer>, Error> parseFuncCall(List<Token> tokens, int point){
        return null;
    } 

    private static Result<Pair<Expression, Integer>, Error> parseObject(List<Token> tokens, int point){
        return null;
    } 


}
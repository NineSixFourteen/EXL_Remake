package dos.Parser.Expressions;

import java.util.List;

import org.javatuples.Pair;

import dos.Parser.ExpressionParser;
import dos.Tokenizer.Types.Token;
import dos.Types.Expression;
import dos.Util.Result;

public class LogicParser {

    // Prec 1 = LThan, LThanEQ, GThan, GThanEq, EqualTo, NotEqualTo,
    // Prec 2 = And, Or
    // other = Not
    public static Result<Pair<Expression, Integer>, Error> parseLogic(List<Token> tokens, int point, Expression prev){
        switch(tokens.get(point).getType()){
            case LThan:
            case LThanEq:
            case GThanEq:
            case GThan:
            case EqualTo:
            case NotEqualTo:
                return parsePrec1(tokens, point, prev);
            case And:
            case Or:
                return parsePrec2(tokens, point, prev);
            case Not:
                return parseNot(tokens, point, prev);
            default: 
                return ExpressionParser.throwError("Unexpected Token in logic"  + tokens.get(point));
        }
    } 

    private static Result<Pair<Expression, Integer>, Error> parsePrec1(List<Token> tokens, int point, Expression prev){
        return null;
    } 

    private static Result<Pair<Expression, Integer>, Error> parsePrec2(List<Token> tokens, int point, Expression prev){
        return null;
    } 

    private static Result<Pair<Expression, Integer>, Error> parseNot(List<Token> tokens, int point, Expression prev){
        return null;
    } 


}

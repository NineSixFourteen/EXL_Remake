package dos.Parser.Expressions;

import java.util.List;

import org.javatuples.Pair;

import dos.Parser.ExpressionParser;
import dos.Parser.Builders.ExpressionFactorys.ExpressionFactory;
import dos.Parser.Util.Grabber;
import dos.Tokenizer.Types.Token;
import dos.Types.Expression;
import dos.Util.Result;

public class SymbolParser {

    public static Result<Pair<Expression, Integer>, Error> parseSymbol(List<Token> tokens, int point){
        Result<Pair<Expression, Integer>, Error> res = new Result<>();
        switch(tokens.get(point).getType()){
            case LBrace:
                return parseLBrac(tokens, point);
            case LSquare:
                return parseLBrac(tokens, point);
            case New:
                return parseLBrac(tokens, point);
            default:
                break;
        }
        res.setError(new Error("Unknown Symbol in Symbol Parser"  + tokens.get(point)));
        return res;
    } 

    private static Result<Pair<Expression, Integer>, Error> parseLBrac(List<Token> tokens, int point){
        Result<Pair<Expression, Integer>, Error> res = new Result<>();
        var body = Grabber.grabBracket(tokens, point);
        if(body.hasError()){res.setError(body.getError());return res;}
        var ExpressionMaybe = ExpressionParser.parse(body.getValue().getValue0());
        Expression expression = ExpressionFactory.symbols.BracExpr(ExpressionMaybe.getValue());
        res.setValue(new Pair<Expression,Integer>(expression, body.getValue().getValue1()));
        return res;
    } 

    private static Result<Pair<Expression, Integer>, Error> parseArray(List<Token> tokens, int point){
        return null;
    } 

    private static Result<Pair<Expression, Integer>, Error> parseObject(List<Token> tokens, int point){
        return null;
    } 

}

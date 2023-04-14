package dos.Parser.Expressions;

import java.util.List;

import org.javatuples.Pair;

import dos.Parser.ExpressionParser;
import dos.Parser.Builders.ExpressionFactorys.ExpressionFactory;
import dos.Parser.Util.Grabber;
import dos.Parser.Util.Seperator;
import dos.Tokenizer.Types.Token;
import dos.Tokenizer.Types.TokenType;
import dos.Types.Expression;
import dos.Util.Result;

public class SymbolParser {

    public static Result<Pair<Expression, Integer>, Error> parseSymbol(List<Token> tokens, int point){
        Result<Pair<Expression, Integer>, Error> res = new Result<>();
        switch(tokens.get(point).getType()){
            case LBrace:
                return parseLBrac(tokens, point);
            case LSquare:
                return parseArray(tokens, point);
            case New:
                return parseObject(tokens, point);
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
        Result<Pair<Expression, Integer>, Error> res = new Result<>();
        var body = Grabber.grabBracket(tokens, point);
        if(body.hasError()){res.setError(body.getError());return res;}
        List<List<Token>> splitTokens = Seperator.splitOnCommas(body.getValue().getValue0());
        var exprs = ExpressionParser.parseMany(splitTokens);
        if(exprs.hasError()){res.setError(exprs.getError());return res;}
        Expression e = ExpressionFactory.symbols.ArrayExpr(exprs.getValue());
        res.setValue(new Pair<Expression,Integer>(e, body.getValue().getValue1()));
        return res;
    } 

    private static Result<Pair<Expression, Integer>, Error> parseObject(List<Token> tokens, int point){
        Result<Pair<Expression, Integer>, Error> res = new Result<>();
        if(tokens.get(point + 1).getType() != TokenType.Value){res.setError(new Error(""));return res;}
        String name = tokens.get(point + 1).getValue();
        var paramsMaybe = Grabber.grabBracket(tokens, point + 2);
        if(paramsMaybe.hasError()){res.setError(paramsMaybe.getError());return res;}
        List<List<Token>> splitTokens = Seperator.splitOnCommas(paramsMaybe.getValue().getValue0());
        var exprs = ExpressionParser.parseMany(splitTokens);
        if(exprs.hasError()){res.setError(exprs.getError());return res;}
        Expression e = ExpressionFactory.symbols.objectExpr(name,exprs.getValue());
        res.setValue(new Pair<Expression,Integer>(e, paramsMaybe.getValue().getValue1()));
        return res;
    } 

}

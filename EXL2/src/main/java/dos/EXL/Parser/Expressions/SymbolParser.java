package dos.EXL.Parser.Expressions;

import java.util.List;

import org.javatuples.Pair;

import dos.EXL.Parser.ExpressionParser;
import dos.EXL.Parser.Factorys.ExpressionFactorys.ExpressionFactory;
import dos.EXL.Parser.Util.Grabber;
import dos.EXL.Parser.Util.Seperator;
import dos.EXL.Tokenizer.Types.Token;
import dos.EXL.Tokenizer.Types.TokenType;
import dos.EXL.Types.Expression;
import dos.Util.Result;
import dos.Util.Results;

public class SymbolParser {

    public static Result<Pair<Expression, Integer>> parseSymbol(List<Token> tokens, int point){
        switch(tokens.get(point).getType()){
            case LBracket:
                return parseLBrac(tokens, point);
            case LSquare:
                return parseArray(tokens, point);
            case New:
                return parseObject(tokens, point);
            default:
                break;
        }
        return Results.makeError("Unknown Symbol in Symbol Parser"  + tokens.get(point));
    } 

    private static Result<Pair<Expression, Integer>> parseLBrac(List<Token> tokens, int point){
        var body = Grabber.grabBracket(tokens, point);
        if(body.hasError()) return Results.makeError(body.getError());
        var ExpressionMaybe = ExpressionParser.parse(body.getValue().getValue0());
        Expression expression = ExpressionFactory.symbols.BracExpr(ExpressionMaybe.getValue());
        return Results.makeResult(new Pair<Expression,Integer>(expression, body.getValue().getValue1()));
    } 

    private static Result<Pair<Expression, Integer>> parseArray(List<Token> tokens, int point){
        var body = Grabber.grabBracket(tokens, point);
        if(body.hasError()) return Results.makeError(body.getError());
        List<List<Token>> splitTokens = Seperator.splitOnCommas(body.getValue().getValue0());
        var exprs = ExpressionParser.parseMany(splitTokens);
        if(exprs.hasError()) return Results.makeError(exprs.getError());
        Expression e = ExpressionFactory.symbols.ArrayExpr(exprs.getValue());
        return Results.makeResult(new Pair<Expression,Integer>(e, body.getValue().getValue1()));
    } 

    private static Result<Pair<Expression, Integer>> parseObject(List<Token> tokens, int point){
        if(tokens.get(point + 1).getType() != TokenType.Value) return Results.makeError("TODO 92932");
        String name = tokens.get(point + 1).getValue();
        var paramsMaybe = Grabber.grabBracket(tokens, point + 2);
        if(paramsMaybe.hasError()) return Results.makeError(paramsMaybe.getError());
        List<List<Token>> splitTokens = Seperator.splitOnCommas(paramsMaybe.getValue().getValue0());
        var exprs = ExpressionParser.parseMany(splitTokens);
        if(exprs.hasError()) return Results.makeError(exprs.getError());
        Expression e = ExpressionFactory.symbols.objectExpr(name,exprs.getValue());
        return Results.makeResult(new Pair<Expression,Integer>(e, paramsMaybe.getValue().getValue1()));
    } 

}

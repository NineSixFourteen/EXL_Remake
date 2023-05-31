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
import dos.EXL.Types.Errors.ErrorFactory;
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
        return Results.makeError(ErrorFactory.makeParser("Unknown Symbol in Symbol Parser ..Symbol Parser"  + tokens.get(point),0));
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
        if(point + 1 >= tokens.size())
            return Results.makeError(ErrorFactory.makeParser("Object declaration missing part after new ",12));
        if(tokens.get(point + 1).getType() != TokenType.Value) 
            return Results.makeError(ErrorFactory.makeParser("Expected name of Object Declaration instead got " + tokens.get(point +1), 2));
        String name = tokens.get(point + 1).getValue();
        if(point + 2 >= tokens.size())
            return Results.makeError(ErrorFactory.makeParser("Object declaration missing part after name ",12));
        if(tokens.get(point + 2).getType() != TokenType.LBracket) 
            return Results.makeError(ErrorFactory.makeParser("Expected ( got  " + tokens.get(point + 2), 2));
        var paramsMaybe = Grabber.grabBracket(tokens, point + 2);
        if(paramsMaybe.hasError()) 
            return Results.makeError(paramsMaybe.getError());
        List<List<Token>> splitTokens = Seperator.splitOnCommas(paramsMaybe.getValue().getValue0());
        var exprs = ExpressionParser.parseMany(splitTokens);
        if(exprs.hasError()) 
            return Results.makeError(exprs.getError());
        Expression e = ExpressionFactory.symbols.objectExpr(name,exprs.getValue());
        return Results.makeResult(new Pair<Expression,Integer>(e, paramsMaybe.getValue().getValue1()));
    } 

}

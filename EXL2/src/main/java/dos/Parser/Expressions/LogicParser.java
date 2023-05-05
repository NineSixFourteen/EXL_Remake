package dos.Parser.Expressions;

import java.util.List;

import org.javatuples.Pair;

import dos.Parser.ExpressionParser;
import dos.Parser.Factorys.ExpressionFactorys.ExpressionFactory;
import dos.Parser.Util.Grabber;
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
                return parseBasic(tokens, point, prev);
            case Or:
                return parseOr(tokens, point, prev);
            case And:
                return parseAnd(tokens, point, prev);
            case Not:
                return parseNot(tokens, point, prev);
            default: 
                return ExpressionParser.throwError("Unexpected Token in logic "  + tokens.get(point));
        }
    } 

    private static Result<Expression, Error> makeLogic(Expression lhs, Expression rhs, Token ty){
        Result<Expression, Error> res = new Result<>();
        switch(ty.getType()){
            case LThan:
                res.setValue(ExpressionFactory.logic.LTExpr(lhs, rhs));
                break;
            case LThanEq:
                res.setValue(ExpressionFactory.logic.LTEQExpr(lhs, rhs));
                break;
            case GThan:
                res.setValue(ExpressionFactory.logic.GTExpr(lhs, rhs));
                break;
            case GThanEq:
                res.setValue(ExpressionFactory.logic.GTEQExpr(lhs, rhs));
                break;
            case NotEqualTo:
                res.setValue(ExpressionFactory.logic.NEQExpr(lhs, rhs));
                break;
            case EqualTo:
                res.setValue(ExpressionFactory.logic.EQExpr(lhs, rhs));
                break;
            case And:
                res.setValue(ExpressionFactory.logic.ANDExpr(lhs, rhs));
                break;
            case Or:
                res.setValue(ExpressionFactory.logic.ORExpr(lhs, rhs));
                break;
            default:
                res.setError(new Error("Unknown logic token type" + ty));
        }
        return res;
    }

    private static Result<Pair<Expression, Integer>, Error> parseBasic(List<Token> tokens, int point, Expression prev){
        var rhsMaybe = Grabber.grabBoolean(tokens, point + 1);
        Result<Pair<Expression, Integer>, Error> res = new Result<>();
        if(rhsMaybe.hasError()){res.setError(rhsMaybe.getError());return res;}
        var exprMaybe = ExpressionParser.parse(rhsMaybe.getValue().getValue0());
        if(exprMaybe.hasError()){res.setError(exprMaybe.getError());return res;}
        var fullExprMaybe = makeLogic(prev, exprMaybe.getValue(), tokens.get(point));
        if(fullExprMaybe.hasError()){res.setError(fullExprMaybe.getError());return res;}
        point = rhsMaybe.getValue().getValue1();
        res.setValue(new Pair<Expression,Integer>(fullExprMaybe.getValue(), point));
        return res;
    } 

    private static Result<Pair<Expression, Integer>, Error> parseOr(List<Token> tokens, int point, Expression prev){
        var rhs = tokens.subList(point + 1, tokens.size());
        Result<Pair<Expression, Integer>, Error> res = new Result<>();
        var exprMaybe = ExpressionParser.parse(rhs);
        if(exprMaybe.hasError()){res.setError(exprMaybe.getError());return res;}
        res.setValue(new Pair<Expression,Integer>(ExpressionFactory.logic.ORExpr(prev, exprMaybe.getValue()), tokens.size() +1));
        return res;
    } 

    private static Result<Pair<Expression, Integer>, Error> parseAnd(List<Token> tokens, int point, Expression prev){
        var rhsMaybe = Grabber.grabBoolean(tokens, point + 1);
        Result<Pair<Expression, Integer>, Error> res = new Result<>();
        if(rhsMaybe.hasError()){res.setError(rhsMaybe.getError());return res;}
        point = rhsMaybe.getValue().getValue1();
        var exprMaybe = ExpressionParser.parse(rhsMaybe.getValue().getValue0());
        if(exprMaybe.hasError()){res.setError(exprMaybe.getError());return res;}
        res.setValue(new Pair<Expression,Integer>(ExpressionFactory.logic.ANDExpr(prev, exprMaybe.getValue()), point));
        return res;
    } 

    private static Result<Pair<Expression, Integer>, Error> parseNot(List<Token> tokens, int point, Expression prev){
        Result<Pair<Expression,Integer>, Error> res = new Result<>();
        var exprMaybe = ExpressionParser.parseExpression(tokens, point, prev);
        if(exprMaybe.hasError()){res.setError(exprMaybe.getError());return res;}
        var expr = ExpressionFactory.logic.NotExpr(exprMaybe.getValue().getValue0());
        res.setValue(new Pair<Expression,Integer>(expr, exprMaybe.getValue().getValue1()));
        return res;
    } 


}

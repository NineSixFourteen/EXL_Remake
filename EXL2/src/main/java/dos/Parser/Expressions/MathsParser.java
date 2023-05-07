package dos.Parser.Expressions;

import java.util.List;

import org.javatuples.Pair;

import dos.Parser.ExpressionParser;
import dos.Parser.Factorys.ExpressionFactorys.ExpressionFactory;
import dos.Tokenizer.Types.Token;
import dos.Types.Expression;
import dos.Util.Result;

public class MathsParser {

    //Prec 1 = Plus, Minus 
    //Prec 2 = Mul, Div, Mod
    public static Result<Pair<Expression, Integer>, Error> parseMaths(List<Token> tokens, int point, Expression prev){
        switch(tokens.get(point).getType()){
            case Plus:
            case Minus:
            case Div:
            case Mul:
            case Mod:
                return parsePrec1(tokens, point, prev); //prec 1
            default: 
                return ExpressionParser.throwError("Unknown maths operator"  + tokens.get(point));
        }
    } 

    //RHS = Right hand Side
    private static Result<Pair<Expression, Integer>, Error> parsePrec1(List<Token> tokens, int point, Expression prev){
        Token token = tokens.get(point);
        Result<Pair<Expression,Integer>, Error> res = new Result<>();
        var RHSMaybe = ExpressionParser.parseExpression(tokens, point + 1, prev);
        if(RHSMaybe.hasError()){res.setError(RHSMaybe.getError());return res;}
        point = RHSMaybe.getValue().getValue1();
        if(point >= tokens.size()){
            var expr = makeExpression(prev, RHSMaybe.getValue().getValue0(), token);
            if(expr.hasError()){res.setError(expr.getError());return res;}
            res.setValue(new Pair<Expression,Integer>(expr.getValue(), point));
        } else {
            switch(tokens.get(point).getType()){
                case Mul:
                case Div:
                case Mod:
                    var fullExprMaybe = parseMaths(tokens, point, RHSMaybe.getValue().getValue0());
                    if(fullExprMaybe.hasError()){res.setError(fullExprMaybe.getError());return res;}
                    var expr = makeExpression(prev, fullExprMaybe.getValue().getValue0(), token);
                    if(expr.hasError()){res.setError(expr.getError());return res;}
                    res.setValue(new Pair<Expression,Integer>(expr.getValue(), fullExprMaybe.getValue().getValue1()));
                    break;
                case Plus: 
                case Minus:
                case LThanEq:
                case LThan:
                case GThan:
                case GThanEq:
                case And:
                case Or:
                case NotEqualTo:
                case EqualTo:
                    var expr2 = makeExpression(prev, RHSMaybe.getValue().getValue0(), token);
                    if(expr2.hasError()){res.setError(expr2.getError());return res;}
                    res.setValue(new Pair<Expression,Integer>(expr2.getValue(), point));
                    break;
                default:
                    res.setError(new Error("Unexpected synbol " + tokens.get(point)));
            }
        }
        return res;
    } 

    private static Result<Expression, Error> makeExpression(Expression lhs, Expression rhs, Token ty){
        Result<Expression, Error> res = new Result<>();
        switch(ty.getType()){
            case Plus:
                res.setValue(ExpressionFactory.maths.addExpr(lhs, rhs));
                break;
            case Minus:
                res.setValue(ExpressionFactory.maths.subExpr(lhs, rhs));
                break;
            case Div:
                res.setValue(ExpressionFactory.maths.divExpr(lhs, rhs));
                break;
            case Mul:
                res.setValue(ExpressionFactory.maths.mulExpr(lhs, rhs));
                break;
            case Mod:
                res.setValue(ExpressionFactory.maths.modExpr(lhs, rhs));
                break;
            default: 
                res.setError(new Error("Unknown Token type for maths expressions" + ty));
        }
        return res;
    }

}
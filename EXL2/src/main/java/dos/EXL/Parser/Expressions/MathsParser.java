package dos.EXL.Parser.Expressions;

import java.util.List;

import org.javatuples.Pair;

import dos.EXL.Parser.ExpressionParser;
import dos.EXL.Parser.Factorys.ExpressionFactorys.ExpressionFactory;
import dos.EXL.Tokenizer.Types.Token;
import dos.EXL.Types.Expression;
import dos.EXL.Types.Errors.ErrorFactory;
import dos.Util.Result;
import dos.Util.Results;

public class MathsParser {

    //Prec 1 = Plus, Minus 
    //Prec 2 = Mul, Div, Mod
    public static Result<Pair<Expression, Integer>> parseMaths(List<Token> tokens, int point, Expression prev){
        switch(tokens.get(point).getType()){
            case Plus:
            case Minus:
            case Div:
            case Mul:
            case Mod:
                return parsePrec1(tokens, point, prev); //prec 1
            default: 
                return Results.makeError(ErrorFactory.makeParser("Unknown maths operator  ..MathsParser"  + tokens.get(point),0));
        }
    } 

    //RHS = Right hand Side
    private static Result<Pair<Expression, Integer>> parsePrec1(List<Token> tokens, int point, Expression prev){
        Token token = tokens.get(point);
        if(tokens.size() <= point + 1)
            return Results.makeError(ErrorFactory.makeParser("Missing expresssion after symbol " + tokens.get(point),4));
        var RHSMaybe = ExpressionParser.parseExpression(tokens, point + 1, prev);
        if(RHSMaybe.hasError()) 
            return Results.makeError(RHSMaybe.getError());
        point = RHSMaybe.getValue().getValue1();
        if(point >= tokens.size()){
            var expr = makeExpression(prev, RHSMaybe.getValue().getValue0(), token);
            if(expr.hasError()) 
                return Results.makeError(expr.getError());
            return Results.makeResult(new Pair<Expression,Integer>(expr.getValue(), point));
        } else {
            switch(tokens.get(point).getType()){
                case Mul:
                case Div:
                case Mod:
                    var fullExprMaybe = parseMaths(tokens, point, RHSMaybe.getValue().getValue0());
                    if(fullExprMaybe.hasError()) 
                        return Results.makeError(fullExprMaybe.getError());
                    var expr = makeExpression(prev, fullExprMaybe.getValue().getValue0(), token);
                    if(expr.hasError()) 
                        return Results.makeError(expr.getError());
                    return Results.makeResult(new Pair<Expression,Integer>(expr.getValue(), fullExprMaybe.getValue().getValue1()));
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
                    if(expr2.hasError()) 
                        return Results.makeError(expr2.getError());
                    return Results.makeResult(new Pair<Expression,Integer>(expr2.getValue(), point));
                default:
                return Results.makeError(ErrorFactory.makeParser("Expected synbol instead got " + tokens.get(point),0));
            }
        }
    } 

    private static Result<Expression> makeExpression(Expression lhs, Expression rhs, Token ty){
        switch(ty.getType()){
            case Plus:
                return Results.makeResult(ExpressionFactory.maths.addExpr(lhs, rhs));
            case Minus:
                return Results.makeResult(ExpressionFactory.maths.subExpr(lhs, rhs));
            case Div:
                return Results.makeResult(ExpressionFactory.maths.divExpr(lhs, rhs));
            case Mul:
                return Results.makeResult(ExpressionFactory.maths.mulExpr(lhs, rhs));
            case Mod:
                return Results.makeResult(ExpressionFactory.maths.modExpr(lhs, rhs));
            default: 
            return Results.makeError(ErrorFactory.makeParser("Unknown Token type for maths expressions ..MathsParser" + ty,0));
        }
    }

}
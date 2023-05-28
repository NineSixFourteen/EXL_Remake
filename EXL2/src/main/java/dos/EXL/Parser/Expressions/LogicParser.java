package dos.EXL.Parser.Expressions;

import java.util.List;

import org.javatuples.Pair;

import dos.EXL.Parser.ExpressionParser;
import dos.EXL.Parser.Factorys.ExpressionFactorys.ExpressionFactory;
import dos.EXL.Parser.Util.Grabber;
import dos.EXL.Tokenizer.Types.Token;
import dos.EXL.Tokenizer.Types.TokenType;
import dos.EXL.Types.Expression;
import dos.EXL.Types.Errors.ErrorFactory;
import dos.EXL.Types.Unary.BracketExpr;
import dos.Util.Result;
import dos.Util.Results;

public class LogicParser {

    // Prec 1 = LThan, LThanEQ, GThan, GThanEq, EqualTo, NotEqualTo,
    // Prec 2 = And, Or
    // other = Not
    public static Result<Pair<Expression, Integer>> parseLogic(List<Token> tokens, int point, Expression prev){
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
                return Results.makeError(ErrorFactory.makeParser("Unexpected Token in logic "  + tokens.get(point),1));
        }
    } 

    private static Result<Expression> makeLogic(Expression lhs, Expression rhs, Token ty){
        switch(ty.getType()){
            case LThan:
                return Results.makeResult(ExpressionFactory.logic.LTExpr(lhs, rhs));
            case LThanEq:
                return Results.makeResult(ExpressionFactory.logic.LTEQExpr(lhs, rhs));
            case GThan:
                return Results.makeResult(ExpressionFactory.logic.GTExpr(lhs, rhs));
            case GThanEq:
                return Results.makeResult(ExpressionFactory.logic.GTEQExpr(lhs, rhs));
            case NotEqualTo:
                return Results.makeResult(ExpressionFactory.logic.NEQExpr(lhs, rhs));
            case EqualTo:
                return Results.makeResult(ExpressionFactory.logic.EQExpr(lhs, rhs));
            case And:
                return Results.makeResult(ExpressionFactory.logic.ANDExpr(lhs, rhs));
            case Or:
                return Results.makeResult(ExpressionFactory.logic.ORExpr(lhs, rhs));   
            default:
            return Results.makeError(ErrorFactory.makeParser("Unknown logic token type ..LogicParser " + ty,0));
        }

    }

    private static Result<Pair<Expression, Integer>> parseBasic(List<Token> tokens, int point, Expression prev){
        var rhsMaybe = Grabber.grabBoolean(tokens, point + 1);
        if(rhsMaybe.hasError())return Results.makeError(rhsMaybe.getError());
        var exprMaybe = ExpressionParser.parse(rhsMaybe.getValue().getValue0());
        if(exprMaybe.hasError())return Results.makeError(exprMaybe.getError());
        var fullExprMaybe = makeLogic(prev, exprMaybe.getValue(), tokens.get(point));
        if(fullExprMaybe.hasError())return Results.makeError(fullExprMaybe.getError());
        point = rhsMaybe.getValue().getValue1();
        return Results.makeResult(new Pair<Expression,Integer>(fullExprMaybe.getValue(), point));
    } 

    private static Result<Pair<Expression, Integer>> parseOr(List<Token> tokens, int point, Expression prev){
        var rhs = tokens.subList(point + 1, tokens.size());
        var exprMaybe = ExpressionParser.parse(rhs);
        if(exprMaybe.hasError()) return Results.makeError(exprMaybe.getError());
        return Results.makeResult(new Pair<Expression,Integer>(ExpressionFactory.logic.ORExpr(prev, exprMaybe.getValue()), tokens.size() +1));
    } 

    private static Result<Pair<Expression, Integer>> parseAnd(List<Token> tokens, int point, Expression prev){
        var rhsMaybe = Grabber.grabBoolean(tokens, point + 1);
        if(rhsMaybe.hasError()) return Results.makeError(rhsMaybe.getError());
        point = rhsMaybe.getValue().getValue1();
        var exprMaybe = ExpressionParser.parse(rhsMaybe.getValue().getValue0());
        if(exprMaybe.hasError()) return Results.makeError(exprMaybe.getError());
        return Results.makeResult(new Pair<Expression,Integer>(ExpressionFactory.logic.ANDExpr(prev, exprMaybe.getValue()), point));
    } 

    private static Result<Pair<Expression, Integer>> parseNot(List<Token> tokens, int point, Expression prev){
        if(point + 1 > tokens.size()){
            return Results.makeError(ErrorFactory.makeParser("Not requires an expression after",4));
        }
        if(tokens.get(point + 1).getType() == TokenType.LBracket){
            var contents = Grabber.grabBracket(tokens, point + 1);
            if(contents.hasError()) return Results.makeError(contents.getError());
            var body = ExpressionParser.parse(contents.getValue().getValue0());
            if(body.hasError()) return Results.makeError(body.getError());
            var expr = ExpressionFactory.logic.NotExpr(new BracketExpr(body.getValue()));
            return Results.makeResult(new Pair<Expression,Integer>(expr, contents.getValue().getValue1()));
        } else {
            var contents = Grabber.grabExpression(tokens, point + 1);
            if(contents.hasError()) return Results.makeError(contents.getError());
            var body = ExpressionParser.parse(contents.getValue().getValue0());
            if(body.hasError()) return Results.makeError(body.getError());
            var expr = ExpressionFactory.logic.NotExpr(body.getValue());
            return Results.makeResult(new Pair<Expression,Integer>(expr, contents.getValue().getValue1()));
        }
    } 


}

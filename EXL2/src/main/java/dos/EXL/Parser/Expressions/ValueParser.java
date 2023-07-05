package dos.EXL.Parser.Expressions;

import java.util.List;

import org.javatuples.Pair;

import dos.EXL.Parser.ExpressionParser;
import dos.EXL.Parser.Util.Grabber;
import dos.EXL.Parser.Util.Seperator;
import dos.EXL.Tokenizer.Types.Token;
import dos.EXL.Types.Expression;
import dos.EXL.Types.Errors.ErrorFactory;
import dos.EXL.Types.Unary.FunctionExpr;
import dos.EXL.Types.Unary.Types.BooleanExpr;
import dos.EXL.Types.Unary.Types.CharExpr;
import dos.EXL.Types.Unary.Types.FloatExpr;
import dos.EXL.Types.Unary.Types.IntExpr;
import dos.EXL.Types.Unary.Types.StringExpr;
import dos.EXL.Types.Unary.Types.VarExpr;
import dos.Util.Result;
import dos.Util.Results;

public class ValueParser {

    public static Result<Pair<Expression, Integer>> parseValue(List<Token> tokens, int point, Expression prev){
        if(!prev.makeString().equals(""))
            return Results.makeError(ErrorFactory.makeParser("The token " + tokens.get(point) + " cannot come after " + prev.makeString() ,11));
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
                        case LBracket:
                            return parseFuncCall(tokens, point);
                        default: 
                            return parseVar(tokens, point);
                    }
                } else {
                    return parseVar(tokens, point);
                }
            case ValueString:
                return parseString(tokens, point);
            case ValueBoolean: 
                return parseBoolean(tokens, point);
            default:
                return Results.makeError(ErrorFactory.makeParser("Token is not a value ..ValueParser " + tokens.get(point),0));

        }
    } 

    private static Result<Pair<Expression, Integer>> parseBoolean(List<Token> tokens, int point) {
        switch(tokens.get(point).getValue()){
            case "true":
                return Results.makeResult(new Pair<Expression,Integer>(new BooleanExpr(true), ++point));
            case "false":
                return Results.makeResult(new Pair<Expression,Integer>(new BooleanExpr(false), ++point));
            default:
                return Results.makeError(ErrorFactory.makeParser("Value of this token could not be parsed as a boolean somehow ???..ValueParser " + tokens.get(point),0));
        }
    }

    private static Result<Pair<Expression, Integer>> parseInt(List<Token> tokens, int point){
        try{
            int i = Integer.parseInt(tokens.get(point).getValue());
            Expression e = new IntExpr(i);
            return Results.makeResult(new Pair<Expression,Integer>(e, ++point));
        } catch(Exception e){
            return Results.makeError(ErrorFactory.makeParser("Value of this token could not be parsed as a integer somehow ???..ValueParser " + tokens.get(point),0));
        }
    } 

    private static Result<Pair<Expression, Integer>> parseChar(List<Token> tokens, int point){
        try{
            Expression e = new CharExpr(tokens.get(point).getValue().charAt(0));
            return Results.makeResult(new Pair<Expression,Integer>(e, ++point));
        } catch(Exception e){
            return Results.makeError(ErrorFactory.makeParser("Value of this token could not be parsed as a char somehow ???..ValueParser " + tokens.get(point),0));
        }
    } 

    private static Result<Pair<Expression, Integer>> parseFloat(List<Token> tokens, int point){
        try{
            float f = Float.parseFloat(tokens.get(point).getValue());
            Expression e = new FloatExpr(f);
            return Results.makeResult(new Pair<Expression,Integer>(e, ++point));
        } catch(Exception e){
            return Results.makeError(ErrorFactory.makeParser("Value of this token could not be parsed as a float somehow ???..ValueParser " + tokens.get(point),0));
        }
    } 

    private static Result<Pair<Expression, Integer>> parseString(List<Token> tokens, int point){
        try{
            Expression e = new StringExpr(tokens.get(point).getValue());
            return Results.makeResult(new Pair<Expression,Integer>(e, ++point));
        } catch(Exception e){
            return Results.makeError(ErrorFactory.makeParser("Value of this token could not be parsed as a string somehow ???..ValueParser " + tokens.get(point),0));
        }
    } 

    private static Result<Pair<Expression, Integer>> parseVar(List<Token> tokens, int point){
        try{
            Expression e = new VarExpr(tokens.get(point).getValue());
            return Results.makeResult(new Pair<Expression,Integer>(e, ++point));
        } catch(Exception e){
            return Results.makeError(ErrorFactory.makeParser("Value of this token could not be parsed as a string somehow ???..ValueParser " + tokens.get(point),0));
        }
    } 

    private static Result<Pair<Expression, Integer>> parseFuncCall(List<Token> tokens, int point){
        String name = tokens.get(point++).getValue();
        var x = Grabber.grabBracket(tokens, point);
        if(x.hasError()) 
            return Results.makeError(x.getError());
        List<List<Token>> tokenList = Seperator.splitOnCommas(x.getValue().getValue0());
        var z = ExpressionParser.parseMany(tokenList);
        if(z.hasError()) 
            return Results.makeError(z.getError());
        Expression ret = new FunctionExpr(name, z.getValue());
        return Results.makeResult(new Pair<Expression,Integer>(ret, x.getValue().getValue1()));
    } 

}
package dos.Parser.Expressions;

import java.util.List;

import org.javatuples.Pair;

import dos.Parser.ExpressionParser;
import dos.Tokenizer.Types.Token;
import dos.Types.Expression;
import dos.Types.Unary.Types.FloatExpr;
import dos.Types.Unary.Types.IntExpr;
import dos.Types.Unary.Types.StringExpr;
import dos.Types.Unary.Types.VarExpr;
import dos.Util.Result;

public class ValueParser {

    public static Result<Pair<Expression, Integer>, Error> parseValue(List<Token> tokens, int point){
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
                        case LBrace:
                            return parseFuncCall(tokens, point);
                        case Dot:
                            return parseObject(tokens, point);
                        default: 
                            return ExpressionParser.throwError("Unkown next character after Value" + tokens.get(point + 1));
                    }
                } else {
                    return parseVar(tokens, point);
                }
            case ValueString:
                return parseString(tokens, point);
            default:
                return ExpressionParser.throwError("Unkown next character after Value" + tokens.get(point + 1));
        }
    } 



    private static Result<Pair<Expression, Integer>, Error> parseInt(List<Token> tokens, int point){
        Result<Pair<Expression, Integer>, Error> res = new Result<>();
        try{
            int i = Integer.parseInt(tokens.get(point).getValue());
            Expression e = new IntExpr(i);
            res.setValue(new Pair<Expression,Integer>(e, ++point));
        } catch(Exception e){
            res.setError(new Error("Value of this token could not be parsed as an integer " + tokens.get(point)));
        }
        return res;
    } 

    private static Result<Pair<Expression, Integer>, Error> parseChar(List<Token> tokens, int point){
        Result<Pair<Expression, Integer>, Error> res = new Result<>();
        try{
            int i = Integer.parseInt(tokens.get(point).getValue());
            Expression e = new IntExpr(i);
            res.setValue(new Pair<Expression,Integer>(e, ++point));
        } catch(Exception e){
            res.setError(new Error("Value of this token could not be parsed as an integer " + tokens.get(point)));
        }
        return res;
    } 

    private static Result<Pair<Expression, Integer>, Error> parseFloat(List<Token> tokens, int point){
        Result<Pair<Expression, Integer>, Error> res = new Result<>();
        try{
            float f = Float.parseFloat(tokens.get(point).getValue());
            Expression e = new FloatExpr(f);
            res.setValue(new Pair<Expression,Integer>(e, ++point));
        } catch(Exception e){
            res.setError(new Error("Value of this token could not be parsed as an float " + tokens.get(point)));
        }
        return res;
    } 

    private static Result<Pair<Expression, Integer>, Error> parseString(List<Token> tokens, int point){
        Result<Pair<Expression, Integer>, Error> res = new Result<>();
        try{
            Expression e = new StringExpr(tokens.get(point).getValue());
            res.setValue(new Pair<Expression,Integer>(e, ++point));
        } catch(Exception e){
            res.setError(new Error("Value of this token could not be parsed as a string somehow ??? " + tokens.get(point)));
        }
        return res;
    } 

    private static Result<Pair<Expression, Integer>, Error> parseVar(List<Token> tokens, int point){
        Result<Pair<Expression, Integer>, Error> res = new Result<>();
        try{
            Expression e = new VarExpr(tokens.get(point).getValue());
            res.setValue(new Pair<Expression,Integer>(e, ++point));
        } catch(Exception e){
            res.setError(new Error("Value of this token could not be parsed as a string somehow ??? " + tokens.get(point)));
        }
        return res;
    } 

    private static Result<Pair<Expression, Integer>, Error> parseFuncCall(List<Token> tokens, int point){
        return null;
    } 

    private static Result<Pair<Expression, Integer>, Error> parseObject(List<Token> tokens, int point){
        return null;
    } 


}
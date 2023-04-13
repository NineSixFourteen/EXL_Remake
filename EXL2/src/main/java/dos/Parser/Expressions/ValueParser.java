package dos.Parser.Expressions;

import java.util.List;

import org.javatuples.Pair;

import dos.Tokenizer.Types.Token;
import dos.Types.Expression;
import dos.Util.Result;

public class ValueParser {

    public static Result<Pair<Expression, Integer>, Error> parseValue(List<Token> tokens, int point, Expression prev){
        return null;
    } 

    private static Result<Pair<Expression, Integer>, Error> parseInt(List<Token> tokens, int point, Expression prev){
        return null;
    } 

    private static Result<Pair<Expression, Integer>, Error> parseChar(List<Token> tokens, int point, Expression prev){
        return null;
    } 

    private static Result<Pair<Expression, Integer>, Error> parseFloat(List<Token> tokens, int point, Expression prev){
        return null;
    } 

    private static Result<Pair<Expression, Integer>, Error> parseString(List<Token> tokens, int point, Expression prev){
        return null;
    } 

    private static Result<Pair<Expression, Integer>, Error> parseVal(List<Token> tokens, int point, Expression prev){
        return null;
    } 

    private static Result<Pair<Expression, Integer>, Error> parseFuncCall(List<Token> tokens, int point, Expression prev){
        return null;
    } 

    private static Result<Pair<Expression, Integer>, Error> parseObject(List<Token> tokens, int point, Expression prev){
        return null;
    } 


}
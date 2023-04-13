package dos.Parser.Expressions;

import java.util.List;

import org.javatuples.Pair;

import dos.Tokenizer.Types.Token;
import dos.Types.Expression;
import dos.Util.Result;

public class MathsParser {

    public static Result<Pair<Expression, Integer>, Error> parseMaths(List<Token> tokens, int point, Expression prev){
        return null;
    } 

    private static Result<Pair<Expression, Integer>, Error> parsePlus(List<Token> tokens, int point, Expression prev){
        return null;
    } 

    private static Result<Pair<Expression, Integer>, Error> parseMinus(List<Token> tokens, int point, Expression prev){
        return null;
    } 

    private static Result<Pair<Expression, Integer>, Error> parseDiv(List<Token> tokens, int point, Expression prev){
        return null;
    } 

    private static Result<Pair<Expression, Integer>, Error> parseMul(List<Token> tokens, int point, Expression prev){
        return null;
    } 

    private static Result<Pair<Expression, Integer>, Error> parseMod(List<Token> tokens, int point, Expression prev){
        return null;
    } 

}
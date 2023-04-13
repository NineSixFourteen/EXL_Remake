package dos.Parser.Expressions;

import java.util.List;

import org.javatuples.Pair;

import dos.Tokenizer.Types.Token;
import dos.Types.Expression;
import dos.Util.Result;

public class LogicParser {

    public static Result<Pair<Expression, Integer>, Error> parseLogic(List<Token> tokens, int point, Expression prev){
        return null;
    } 

    private static Result<Pair<Expression, Integer>, Error> parseLT(List<Token> tokens, int point, Expression prev){
        return null;
    } 

    private static Result<Pair<Expression, Integer>, Error> parseLTEQ(List<Token> tokens, int point, Expression prev){
        return null;
    } 

    private static Result<Pair<Expression, Integer>, Error> parseGT(List<Token> tokens, int point, Expression prev){
        return null;
    } 

    private static Result<Pair<Expression, Integer>, Error> parseGTEQ(List<Token> tokens, int point, Expression prev){
        return null;
    } 

    private static Result<Pair<Expression, Integer>, Error> parseAND(List<Token> tokens, int point, Expression prev){
        return null;
    } 

    private static Result<Pair<Expression, Integer>, Error> parseOR(List<Token> tokens, int point, Expression prev){
        return null;
    } 

    private static Result<Pair<Expression, Integer>, Error> parseNot(List<Token> tokens, int point, Expression prev){
        return null;
    } 

    private static Result<Pair<Expression, Integer>, Error> parseEQ(List<Token> tokens, int point, Expression prev){
        return null;
    } 

    private static Result<Pair<Expression, Integer>, Error> parseNEQ(List<Token> tokens, int point, Expression prev){
        return null;
    } 
}

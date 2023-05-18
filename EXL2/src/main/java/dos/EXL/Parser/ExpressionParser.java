package dos.EXL.Parser;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;

import dos.EXL.Parser.Expressions.LogicParser;
import dos.EXL.Parser.Expressions.MathsParser;
import dos.EXL.Parser.Expressions.ObjectParser;
import dos.EXL.Parser.Expressions.SymbolParser;
import dos.EXL.Parser.Expressions.ValueParser;
import dos.EXL.Parser.Util.ExprCategories;
import dos.EXL.Tokenizer.Types.Token;
import dos.EXL.Tokenizer.Types.TokenType;
import dos.EXL.Types.Expression;
import dos.EXL.Types.Unary.Types.VarExpr;
import dos.Util.Result;

public class ExpressionParser {

    public static Result<List<Expression>, Error> parseMany(List<List<Token>> splitTokens){
        Result<List<Expression>, Error> res = new Result<>();
        List<Expression> exprs = new ArrayList<>();
        for(List<Token> toks : splitTokens){
            var exprMaybe = ExpressionParser.parse(toks);
            if(exprMaybe.hasError()){res.setError(exprMaybe.getError());return res;}
            exprs.add(exprMaybe.getValue());
        }
        res.setValue(exprs);
        return res;
    } 

    public static Result<Expression, Error> parse(List<Token> tokens){
        Expression prev = new VarExpr("");
        Result<Expression, Error> res = new Result<>();
        int point = 0; 
        while(point < tokens.size()){
            var exprMaybe = parseExpression(tokens, point, prev);
            if(exprMaybe.hasError()){res.setError(exprMaybe.getError());return res;}
            prev = exprMaybe.getValue().getValue0();
            point = exprMaybe.getValue().getValue1();
        }
        res.setValue(prev);
        return res;
    }

    private static ExprCategories findTokenType(TokenType type) {
        switch(type){
            case Plus:case Minus:case Div:case Mul:case Mod:
                return ExprCategories.Maths;
            case LThan:case LThanEq:case GThan:case GThanEq: case And:case Or:case Not:case EqualTo:case NotEqualTo:
                return ExprCategories.Logic;
            case Value:case ValueChar:case ValueFloat:case ValueInt:case ValueString:
                return ExprCategories.Value;
            case LBrace:case LBracket:case New:case LSquare:
                return ExprCategories.Symbol;
            case Dot:
                return ExprCategories.Object;
            default: 
                return ExprCategories.unknown;
        }
    }

    public static Result<Pair<Expression, Integer>, Error> parseExpression(List<Token> tokens, int point, Expression prev){
        Result<Pair<Expression, Integer>, Error>res = new Result<>();
        Result<Pair<Expression, Integer>, Error> exprMaybe = new Result<>();
        switch(findTokenType(tokens.get(point).getType())){
            case Logic:
                exprMaybe = LogicParser.parseLogic(tokens, point, prev);
                break;
            case Maths:
                exprMaybe = MathsParser.parseMaths(tokens, point, prev);
                break;
            case Symbol:
                exprMaybe = SymbolParser.parseSymbol(tokens, point);
                break;
            case Value:
                exprMaybe = ValueParser.parseValue(tokens, point);
                break;
            case Object:
                exprMaybe = ObjectParser.parseObj(tokens, point, prev);
                break;
            case unknown:
                res.setError(new Error("Don't know how to parse " + tokens.get(point)));
                return res;        
        }
        if(exprMaybe.hasError()){res.setError(exprMaybe.getError());return res;}
        res.setValue(exprMaybe.getValue());
        return res;
    }

    public static Result<Pair<Expression, Integer>, Error> throwError(String error){
        Result<Pair<Expression, Integer>, Error> res = new Result<>();
        res.setError(new Error(error));
        return res;
    }


    
}
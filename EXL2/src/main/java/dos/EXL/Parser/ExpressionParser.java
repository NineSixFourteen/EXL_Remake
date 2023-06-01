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
import dos.EXL.Types.Errors.ErrorFactory;
import dos.EXL.Types.Unary.Types.VarExpr;
import dos.Util.Result;
import dos.Util.Results;

public class ExpressionParser {

    public static Result<List<Expression>> parseMany(List<List<Token>> splitTokens){
        List<Expression> exprs = new ArrayList<>();
        for(List<Token> toks : splitTokens){
            var exprMaybe = ExpressionParser.parse(toks);
            if(exprMaybe.hasError()) return Results.makeError(exprMaybe.getError());
            exprs.add(exprMaybe.getValue());
        }
        return Results.makeResult(exprs);
    } 

    public static Result<Expression> parse(List<Token> tokens){
        Expression prev = new VarExpr("");
        int point = 0; 
        while(point < tokens.size()){
            var exprMaybe = parseExpression(tokens, point, prev);
            if(exprMaybe.hasError()) return Results.makeError(exprMaybe.getError());
            prev = exprMaybe.getValue().getValue0();
            point = exprMaybe.getValue().getValue1();
        }
        return Results.makeResult(prev);
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

    public static Result<Pair<Expression, Integer>> parseExpression(List<Token> tokens, int point, Expression prev){
        switch(findTokenType(tokens.get(point).getType())){
            case Logic:
                return LogicParser.parseLogic(tokens, point, prev);
            case Maths:
                return MathsParser.parseMaths(tokens, point, prev);
            case Symbol:
                return SymbolParser.parseSymbol(tokens, point);
            case Value:
                return ValueParser.parseValue(tokens, point,prev);
            case Object:
                return ObjectParser.parseObj(tokens, point, prev);
            default:
            case unknown:
            return Results.makeError(ErrorFactory.makeParser("Invalid token found in expression " + tokens.get(point) + " you may of missed a semicolan",15));        
        }
    }
    
}

package dos.EXL.Parser.Expressions;

import java.util.List;


import org.javatuples.Pair;

import dos.EXL.Parser.ExpressionParser;
import dos.EXL.Parser.Util.Grabber;
import dos.EXL.Parser.Util.Seperator;
import dos.EXL.Tokenizer.Types.Token;
import dos.EXL.Tokenizer.Types.TokenType;
import dos.EXL.Types.Expression;
import dos.EXL.Types.Binary.ObjectFieldExpr;
import dos.EXL.Types.Binary.ObjectFuncExpr;
import dos.EXL.Types.Errors.ErrorFactory;
import dos.EXL.Types.Unary.FunctionExpr;
import dos.Util.Result;
import dos.Util.Results;

public class ObjectParser {

    public static Result<Pair<Expression, Integer>> parseObj(List<Token> tokens, int point, Expression prev) {
        Expression e;
        if(point + 1 >= tokens.size()){
            return Results.makeError(ErrorFactory.makeParser("Missing expression after Dot(.) token",4));
        }
        String name = tokens.get(++point).getValue();
        if(point + 1 < tokens.size() && tokens.get(point + 1).getType() == TokenType.LBracket){
            var x = Grabber.grabBracket(tokens, ++point);
            if(x.hasError())
                return Results.makeError(x.getError());
            List<List<Token>> tokenList = Seperator.splitOnCommas(x.getValue().getValue0());
            var z = ExpressionParser.parseMany(tokenList);
            if(z.hasError())
                return Results.makeError(z.getError());
            e = new ObjectFuncExpr(prev, new FunctionExpr(name, z.getValue()));
            point = x.getValue().getValue1();
        } else {
            e = new ObjectFieldExpr(prev, name);
            point++;
        }
        if(point + 1 < tokens.size() && tokens.get(point + 1).getType() == TokenType.Dot){
            return parseObj(tokens, point + 1, e);
        }else {
            return Results.makeResult(new Pair<Expression,Integer>(e, point ));
        }
        
    }
    
}

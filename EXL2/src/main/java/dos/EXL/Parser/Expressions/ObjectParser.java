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
import dos.EXL.Types.Unary.FunctionExpr;
import dos.Util.Result;

public class ObjectParser {

    public static Result<Pair<Expression, Integer>, Error> parseObj(List<Token> tokens, int point, Expression prev) {
        Expression e;
        Result<Pair<Expression, Integer>, Error> res = new Result<>();
        String name = tokens.get(++point).getValue();
        if(point + 1 < tokens.size() && tokens.get(point + 1).getType() == TokenType.LBracket){
            var x = Grabber.grabBracket(tokens, ++point);
            List<List<Token>> tokenList = Seperator.splitOnCommas(x.getValue().getValue0());
            var z = ExpressionParser.parseMany(tokenList);
            e = new ObjectFuncExpr(prev, new FunctionExpr(name, z.getValue()));
            point = x.getValue().getValue1();
        } else {
            e = new ObjectFieldExpr(prev, name);
            point++;
        }
        if(point + 1 < tokens.size() && tokens.get(point + 1).getType() == TokenType.Dot){
            return parseObj(tokens, point + 1, e);
        }else {
            res.setValue(new Pair<Expression,Integer>(e, point ));
            return res;
        }
        
    }
    
}
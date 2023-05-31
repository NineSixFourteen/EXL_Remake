package dos.EXL.Parser.Lines;

import java.util.List;

import org.javatuples.Triplet;

import dos.EXL.Parser.ExpressionParser;
import dos.EXL.Parser.Factorys.LineFactory;
import dos.EXL.Tokenizer.Types.Token;
import dos.EXL.Tokenizer.Types.TokenType;
import dos.EXL.Types.Expression;
import dos.EXL.Types.Line;
import dos.EXL.Types.Errors.ErrorFactory;
import dos.Util.Result;
import dos.Util.Results;

public class DeclareLineParser {
    

    public static Result<Triplet<String, String, Expression>> parse(List<Token> tokens){
        String type = tokens.get(0).getType().name();
        type = type.equals("String") ? type : type.toLowerCase();
        if(tokens.get(1).getType() != TokenType.Value)
            return Results.makeError(ErrorFactory.makeParser("Expected name of new variable instead found token " + tokens.get(1),2));
        String name = tokens.get(1).getValue();
        var expression = ExpressionParser.parse(tokens.subList(3, tokens.size() - 1));
        if(expression.hasError())   
            return Results.makeError(expression.getError());
        return Results.makeResult(new Triplet<String,String,Expression>(type, name, expression.getValue()));
    }

    public static Result<Line> getLine(List<Token> tokens){
        var declareMaybe = parse(tokens);
        if(declareMaybe.hasError()) 
            return Results.makeError(declareMaybe.getError());
        Triplet<String, String, Expression> declareParts = declareMaybe.getValue();
        return Results.makeResult(LineFactory.IninitVariable(declareParts.getValue1(), declareParts.getValue0(),declareParts.getValue2()));
    }


}

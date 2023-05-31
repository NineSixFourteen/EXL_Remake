package dos.EXL.Parser.Lines;

import java.util.List;

import dos.EXL.Parser.ExpressionParser;
import dos.EXL.Parser.Factorys.LineFactory;
import dos.EXL.Tokenizer.Types.Token;
import dos.EXL.Types.Line;
import dos.Util.Result;
import dos.Util.Results;

// Single Expression Line Parser i.e. Print, Return and Expression Lines i.e. func(9);
public class SELParser {

    public static Result<Line> getPrint(List<Token> tokens){
        var printMaybe = ExpressionParser.parse(tokens.subList(1, tokens.size() - 1));
        if(printMaybe.hasError()) 
            return Results.makeError(printMaybe.getError());
        return Results.makeResult(LineFactory.Print(printMaybe.getValue()));
    }

    public static Result<Line> getReturn(List<Token> tokens){
        var returnMaybe = ExpressionParser.parse(tokens.subList(1, tokens.size() - 1));
        if(returnMaybe.hasError())
            return Results.makeError(returnMaybe.getError());
        return Results.makeResult(LineFactory.returnL(returnMaybe.getValue()));
    }

    public static Result<Line> getExprLine(List<Token> tokens){
        var exprMaybe = ExpressionParser.parse(tokens.subList(0, tokens.size() - 1));
        if(exprMaybe.hasError())
            return Results.makeError(exprMaybe.getError());
        return Results.makeResult(LineFactory.exprL(exprMaybe.getValue()));
    }


    
}

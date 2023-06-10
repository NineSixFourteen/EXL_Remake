package dos.EXL.Parser.Lines;

import java.util.List;

import org.javatuples.Pair;

import dos.EXL.Parser.ExpressionParser;
import dos.EXL.Parser.Factorys.LineFactory;
import dos.EXL.Tokenizer.Types.Token;
import dos.EXL.Tokenizer.Types.TokenType;
import dos.EXL.Compiler.ASM.Util.Primitives;
import dos.EXL.Types.Expression;
import dos.EXL.Types.Line;
import dos.EXL.Types.Errors.ErrorFactory;
import dos.Util.Result;
import dos.Util.Results;

public class VarOverParser {

    public static Result<Pair<String,Expression>> parse(List<Token> tokens) {
        if(tokens.get(0).getType() != TokenType.Value)
            return Results.makeError(ErrorFactory.makeParser("Weird behaviour ..LineParser",0));
        String name = tokens.get(0).getValue();
        if(tokens.size() <= 3)
            return Results.makeError(ErrorFactory.makeParser("Expected Expression after = in Variable overwrite", 4));
        var exprMaybe = ExpressionParser.parse(tokens.subList(2, tokens.size() - 1));
        if(exprMaybe.hasError())
            return Results.makeError(exprMaybe.getError());
        return Results.makeResult(new Pair<String,Expression>(name, exprMaybe.getValue()));
    }

    public static Result<Line> getLine(List<Token> tokens){
        var varOMaybe = parse(tokens); 
        if(varOMaybe.hasError())
            return Results.makeError(varOMaybe.getError());
        var varOParts = varOMaybe.getValue();
        return Results.makeResult(LineFactory.varO(varOParts.getValue0(),varOParts.getValue1()));
    }
    
}

package dos.EXL.Parser.Lines;

import java.util.List;
import java.util.OptionalInt;
import java.util.stream.IntStream;

import org.javatuples.Pair;

import dos.EXL.Parser.CodeBlockParser;
import dos.EXL.Parser.ExpressionParser;
import dos.EXL.Parser.Factorys.LineFactory;
import dos.EXL.Parser.Util.Grabber;
import dos.EXL.Tokenizer.Types.Token;
import dos.EXL.Tokenizer.Types.TokenType;
import dos.EXL.Types.Expression;
import dos.EXL.Types.Line;
import dos.EXL.Types.Binary.Boolean.BoolExpr;
import dos.EXL.Types.Errors.ErrorFactory;
import dos.EXL.Types.Lines.CodeBlock;
import dos.Util.Result;
import dos.Util.Results;

public class ElseParser {
    

    public static Result<CodeBlock> parse(List<Token> tokens){
        var bodyMaybe = Grabber.grabBracket(tokens,1);
        if(bodyMaybe.hasError())
            return Results.makeError(bodyMaybe.getError());
        var body = CodeBlockParser.getCodeBlock(bodyMaybe.getValue().getValue0());
        if(body.hasError())
            return Results.makeError(body.getError());
        return Results.makeResult( body.getValue());
    }

    public static Result<Line> getLine(List<Token> tokens){
        var elseMaybe = parse(tokens);
        if(elseMaybe.hasError()) 
            return Results.makeError(elseMaybe.getError());
        return Results.makeResult(LineFactory.elseL(elseMaybe.getValue()));
    }
    
}

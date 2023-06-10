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
import dos.EXL.Compiler.ASM.Util.Primitives;
import dos.EXL.Types.Expression;
import dos.EXL.Types.Line;
import dos.EXL.Types.Errors.ErrorFactory;
import dos.EXL.Types.Lines.CodeBlock;
import dos.Util.Result;
import dos.Util.Results;

public class WhileParser {

    public static Result<Pair<Expression,CodeBlock>> parse(List<Token> tokens){
        OptionalInt index = IntStream.range(0, tokens.size())
                     .filter(i -> TokenType.LBrace.equals(tokens.get(i).getType()))
                     .findFirst();
        if(index.isEmpty())           
            return Results.makeError(ErrorFactory.makeParser("Expected to find { symbol in while line", 2));
        if(index.getAsInt() == 1)
            return Results.makeError(ErrorFactory.makeParser("Expected an expression after while", 4));
        var booleanMaybe = ExpressionParser.parse(tokens.subList(1, index.getAsInt()));
        if(booleanMaybe.hasError())
            return Results.makeError(booleanMaybe.getError());
        var bodyMaybe = Grabber.grabBracket(tokens, index.getAsInt());
        if(bodyMaybe.hasError())
            return Results.makeError(bodyMaybe.getError());
        var body = CodeBlockParser.getCodeBlock(bodyMaybe.getValue().getValue0());
        if(body.hasError())
            return Results.makeError(body.getError());
        return Results.makeResult(new Pair<Expression,CodeBlock>(booleanMaybe.getValue(), body.getValue()));
    }

    public static Result<Line> getLine(List<Token> tokens){
        var whileMaybe = parse(tokens);
        if(whileMaybe.hasError()) 
            return Results.makeError(whileMaybe.getError());
        Pair<Expression,CodeBlock> whileParts = whileMaybe.getValue();
        return Results.makeResult(LineFactory.whileL(whileParts.getValue0(),whileParts.getValue1()));
    }
    
}

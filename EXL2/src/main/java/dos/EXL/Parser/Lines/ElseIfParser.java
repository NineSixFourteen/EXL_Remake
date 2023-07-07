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

public class ElseIfParser {

     public static Result<Pair<BoolExpr,CodeBlock>> parse(List<Token> tokens){
        OptionalInt index = IntStream.range(0, tokens.size())
                     .filter(i -> TokenType.LBrace.equals(tokens.get(i).getType()))
                     .findFirst();
        if(index.isEmpty())           
            return Results.makeError(ErrorFactory.makeParser("Expected to find { symbol in else if line", 2));
        if(index.getAsInt() == 1)
            return Results.makeError(ErrorFactory.makeParser("Expected an expression after else if", 4));
        var exprMaybe = ExpressionParser.parse(tokens.subList(2, index.getAsInt()));
        if(exprMaybe.hasError())
            return Results.makeError(exprMaybe.getError());
        var booleanMaybe = toBool(exprMaybe.getValue());
        if(booleanMaybe.hasError())
            return Results.makeError(booleanMaybe.getError());
        var bodyMaybe = Grabber.grabBracket(tokens, index.getAsInt());
        if(bodyMaybe.hasError())
            return Results.makeError(bodyMaybe.getError());
        var body = CodeBlockParser.getCodeBlock(bodyMaybe.getValue().getValue0());
        if(body.hasError())
            return Results.makeError(body.getError());
        return Results.makeResult(new Pair<>(booleanMaybe.getValue(), body.getValue()));
    }

    public static Result<Line> getLine(List<Token> tokens){
        var elseIfMaybe = parse(tokens);
        if(elseIfMaybe.hasError()) 
            return Results.makeError(elseIfMaybe.getError());
        Pair<BoolExpr,CodeBlock> elseIfParts = elseIfMaybe.getValue();
        return Results.makeResult(LineFactory.elseIfLine(elseIfParts.getValue0(),elseIfParts.getValue1()));
    }

        private static Result<BoolExpr> toBool(Expression bool){
        try{
            BoolExpr bol = (BoolExpr) bool;
            return Results.makeResult(bol);
        } catch(Exception e){
            return Results.makeError(ErrorFactory.makeLogic("The expression " + bool + " is not boolean yet you put it next to a AND why would u do that u knew or something xD cringe", 2));
        }
    }
    
}

package dos.EXL.Parser;

import java.util.List;
import java.util.OptionalInt;
import java.util.stream.IntStream;

import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.javatuples.Triplet;

import dos.EXL.Parser.Util.Grabber;
import dos.EXL.Parser.Util.TagGrabber;
import dos.EXL.Tokenizer.Types.Token;
import dos.EXL.Tokenizer.Types.TokenType;
import dos.EXL.Types.Expression;
import dos.EXL.Types.Line;
import dos.EXL.Types.Lines.CodeBlock;
import dos.EXL.Types.Lines.DeclarLine;
import dos.EXL.Types.Lines.Field;
import dos.Util.Result;
import dos.Util.Results;

public class LineParser {
    
    public static Result<Field> getField(List<Token> tokens){
        var TagsMaybe = TagGrabber.getClassTags(tokens, 0);
        int point = TagsMaybe.getValue().getValue1();
        String type = tokens.get(point).getType().name().toLowerCase();
        if(tokens.get(point + 1).getType() != TokenType.Value) 
            return Results.makeError("Expected Field Name got " + tokens.get(point + 1));
        String name = tokens.get(point + 1).getValue();
        var expression = ExpressionParser.parse(tokens.subList(point + 3, tokens.size()));
        if(expression.hasError()) 
            return Results.makeError(expression.getError());
        return Results.makeResult(new Field(TagsMaybe.getValue().getValue0(), name,  expression.getValue(), type));
    }

    public static Result<Triplet<String, String, Expression>> getDeclare(List<Token> tokens){
        String type = tokens.get(0).getType().name().toLowerCase();
        if(tokens.get(1).getType() != TokenType.Value)
            return Results.makeError("Expected Function Name got " + tokens.get(1));
        String name = tokens.get(1).getValue();
        var expression = ExpressionParser.parse(tokens.subList(3, tokens.size() - 1));
        if(expression.hasError())   
            return Results.makeError(expression.getError());
        return Results.makeResult(new Triplet<String,String,Expression>(type, name, expression.getValue()));
    }

    public static Result<Quartet<DeclarLine,Expression,Line,CodeBlock>> getFor(List<Token> tokens){//TODOOOO
        return null;
    }

    public static Result<Pair<Expression,CodeBlock>> getIf(List<Token> tokens){
        OptionalInt index = IntStream.range(0, tokens.size())
                     .filter(i -> TokenType.LBrace.equals(tokens.get(i).getType()))
                     .findFirst();
        if(index.isEmpty())
            return Results.makeError("No Lbrace in If line" + tokens);
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

    public static Result<Pair<String,Expression>> getVarOver(List<Token> tokens) {
        if(tokens.get(0).getType() != TokenType.Value)
            return Results.makeError("How tf did this happen ");
        String name = tokens.get(0).getValue();
        var exprMaybe = ExpressionParser.parse(tokens.subList(2, tokens.size() - 1));
        if(exprMaybe.hasError())
            return Results.makeError(exprMaybe.getError());
        return Results.makeResult(new Pair<String,Expression>(name, exprMaybe.getValue()));

    }
    


}

package dos.EXL.Parser.Lines;

import java.util.List;

import dos.EXL.Parser.ExpressionParser;
import dos.EXL.Parser.Util.TagGrabber;
import dos.EXL.Tokenizer.Types.Token;
import dos.EXL.Tokenizer.Types.TokenType;
import dos.EXL.Types.Errors.ErrorFactory;
import dos.EXL.Types.Lines.Field;
import dos.Util.Result;
import dos.Util.Results;

public class FeildParser {

    public static Result<Field> parse(List<Token> tokens){
        var TagsMaybe = TagGrabber.getClassTags(tokens, 0);
        int point = TagsMaybe.getValue().getValue1();
        String type = tokens.get(point).getType().name().toLowerCase();
        if(tokens.get(point + 1).getType() != TokenType.Value) 
            return Results.makeError(ErrorFactory.makeParser("Expected name of field instead found token " + tokens.get(point  +1),2));
        String name = tokens.get(point + 1).getValue();
        var expression = ExpressionParser.parse(tokens.subList(point + 3, tokens.size()));
        if(expression.hasError()) 
            return Results.makeError(expression.getError());
        return Results.makeResult(new Field(TagsMaybe.getValue().getValue0(), name,  expression.getValue(), type));
    }
    
}

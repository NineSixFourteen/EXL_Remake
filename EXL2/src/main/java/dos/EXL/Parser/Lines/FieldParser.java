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

public class FieldParser {

    public static Result<Field> parse(List<Token> tokens){
        var TagsMaybe = TagGrabber.getClassTags(tokens, 0);
        int point = TagsMaybe.getValue().getValue1();
        String type;
        switch(tokens.get(point).getType()){
            case Value:
                type = tokens.get(point).getValue(); 
                break;
            case Int:
            case Float:
            case Long:
            case Short:
            case Double:
            case Boolean:
            case Char:
                type = tokens.get(point).getType().name().toLowerCase();
                break;
            case String:
                type = tokens.get(point).getType().name();
                break;
            default:
                return Results.makeError(ErrorFactory.makeParser("Expected type of field, insted found " + tokens.get(point),2));
        }
        if(tokens.get(point + 1).getType() != TokenType.Value) 
            return Results.makeError(ErrorFactory.makeParser("Expected name of field instead found token " + tokens.get(point  +1),2));
        String name = tokens.get(point + 1).getValue();
        if(tokens.size() <= point + 3)
        return Results.makeError(ErrorFactory.makeParser("Missing expression to be assigned to new field",4));
        var expression = ExpressionParser.parse(tokens.subList(point + 3, tokens.size() - 1));
        if(expression.hasError()) 
            return Results.makeError(expression.getError());
        return Results.makeResult(new Field(TagsMaybe.getValue().getValue0(), name,  expression.getValue(), type));
    }
    
}

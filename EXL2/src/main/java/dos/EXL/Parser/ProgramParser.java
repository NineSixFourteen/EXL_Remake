package dos.EXL.Parser;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;

import dos.EXL.Parser.Builders.ProgramBuilder;
import dos.EXL.Parser.Lines.FieldParser;
import dos.EXL.Parser.Util.Grabber;
import dos.EXL.Parser.Util.TagGrabber;
import dos.EXL.Tokenizer.Types.Token;
import dos.EXL.Tokenizer.Types.TokenType;
import dos.EXL.Types.Function;
import dos.EXL.Types.Program;
import dos.EXL.Types.Errors.ErrorFactory;
import dos.EXL.Types.Lines.Field;
import dos.Util.Result;
import dos.Util.Results;

public class ProgramParser {
    
    public static Result<Program> toClass(List<Token> tokens){
        ProgramBuilder pb = new ProgramBuilder();
        int point = 0;
        // Get Class Tags
        var tagsMaybe = TagGrabber.getClassTags(tokens, point);
        if(tagsMaybe.hasError())
            return Results.makeError(tagsMaybe.getError());
        pb.addTags(tagsMaybe.getValue().getValue0());
        // Move point to after tags
        point = tagsMaybe.getValue().getValue1();
        if(tokens.get(point++).getType() != TokenType.Class)
            return Results.makeError(ErrorFactory.makeParser("Expected class token instead found token " + tokens.get(point  +1),2));
        var nameMaybe = getName(tokens, point);
        if(nameMaybe.hasError())
            return Results.makeError(nameMaybe.getError());
        pb.setName(nameMaybe.getValue().getValue0());
        // Move point to after Name
        point = nameMaybe.getValue().getValue1();
        var classBodyMaybe = Grabber.grabBracket(tokens, point);
        if(classBodyMaybe.hasError())
            return Results.makeError(tagsMaybe.getError());
        var classBody = classBodyMaybe.getValue();
        point = classBody.getValue1();
        var z = getFieldsAndFunctions(classBody.getValue0());
        if(z.hasError())
            return Results.makeError(z.getError());
        List<Function> funcs = z.getValue().getValue0();
        List<Field> fields = z.getValue().getValue1();
        funcs.forEach(x -> pb.addFunction(x));
        fields.forEach(x -> pb.addField(x));
        return Results.makeResult(pb.build());
    }

    private static Result<Pair<String,Integer>> getName(List<Token> tokens, int point) {
        if(tokens.get(point).getType() != TokenType.Value){
            return Results.makeError(ErrorFactory.makeParser("Expected name of class instead found token " + tokens.get(point),2));
        } else {
            return Results.makeResult(new Pair<>(tokens.get(point).getValue(), point + 1));
        }
    }

    private static Result<Boolean> isFunc(List<Token> tokens, int point) {
        while(point < tokens.size()){
            switch(tokens.get(point).getType()){
                case Equal:
                    return Results.makeResult(false);
                case LBracket:
                    return Results.makeResult(true);
                case Private:
                case Static:
                case Public:
                case Value:
                case Int:
                case Float:
                case Long:
                case Short:
                case String:
                case Double:
                case Boolean:
                case Char:
                    point++;
                    break;
                default:
                    return Results.makeError(ErrorFactory.makeParser("Unknown line" + tokens, 10));
            }
        }
        return Results.makeError(ErrorFactory.makeParser("Unknown line" + tokens, 10));
    }

    private static Result<Pair<List<Function>,List<Field>>> getFieldsAndFunctions(List<Token> tokens) {
        int point = 0; 
        List<Function> functions = new ArrayList<>();
        List<Field> fields = new ArrayList<>();
        while(point < tokens.size()){
            var x = isFunc(tokens, point);
            if(x.hasValue()){
                if(x.getValue()){
                    var funcMaybe = getFunction(tokens,point);
                    if(funcMaybe.hasError())
                        return Results.makeError(funcMaybe.getError());
                    var func = funcMaybe.getValue();
                    point = func.getValue1();
                    functions.add(func.getValue0());
                } else {
                    var fieldMaybe = getField(tokens, point);
                    if(fieldMaybe.hasError())
                        return Results.makeError(fieldMaybe.getError());
                    var field = fieldMaybe.getValue();
                    point = field.getValue1();
                    fields.add(field.getValue0());
                }
            } else {
                return Results.makeError(x.getError());
            }
        }
        return Results.makeResult(new Pair<List<Function>,List<Field>>(functions, fields));
    }

    private static Result<Pair<Function,Integer>> getFunction(List<Token> tokens, int point){
        var funcBody = Grabber.grabFunction(tokens, point); 
        if(funcBody.hasValue()){
            point = funcBody.getValue().getValue1();
            var func = FunctionParser.getFunction(funcBody.getValue().getValue0());
            if(func.hasValue()){
                return Results.makeResult(new Pair<>(func.getValue(),point));
            } else {
                return Results.makeError(func.getError());
            }
        }
        return Results.makeError(funcBody.getError());
    }

    private static Result<Pair<Field,Integer>> getField(List<Token> tokens, int point){
        var fieldBody = Grabber.grabNextLine(tokens, point); 
        if(fieldBody.hasValue()){
            point = fieldBody.getValue().getValue1();
            var field = FieldParser.parse(fieldBody.getValue().getValue0());
            if(field.hasValue()){
                return Results.makeResult(new Pair<>(field.getValue(), point));
            } else {
                return Results.makeError(field.getError());
            }
        } else {
            return Results.makeError(fieldBody.getError());
        }
    }

}

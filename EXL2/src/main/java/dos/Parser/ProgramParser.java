package dos.Parser;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;

import dos.Parser.Builders.ProgramBuilder;
import dos.Parser.Util.Grabber;
import dos.Tokenizer.Types.Token;
import dos.Tokenizer.Types.TokenType;
import dos.Types.Function;
import dos.Types.Program;
import dos.Types.Tag;
import dos.Types.Lines.Field;
import dos.Util.Result;

public class ProgramParser {
    
    public static Result<Program,Error> toClass(List<Token> tokens){
        Result<Program,Error> res = new Result<>();
        ProgramBuilder pb = new ProgramBuilder();
        int point = 0;
        var tagsMaybe = getClassTags(tokens, point);
        if(tagsMaybe.hasError()){res.setError(tagsMaybe.getError()); return res;} 
        for(Tag t : tagsMaybe.getValue().getValue0()){
            pb.addTag(t);
        }
        point = tagsMaybe.getValue().getValue1();
        var nameMaybe = getName(tokens, point);
        if(nameMaybe.hasError()){res.setError(nameMaybe.getError()); return res;} 
        pb.setName(nameMaybe.getValue().getValue0());
        point = nameMaybe.getValue().getValue1();
        var classBodyMaybe = Grabber.grabBracket(tokens, point);
        if(classBodyMaybe.hasError()){res.setError(classBodyMaybe.getError());return res;}
        var classBody = classBodyMaybe.getValue();
        point = classBody.getValue1();
        var z = getFieldsAndFunctions(classBody.getValue0());
        return res;
    }

    private static Result<Pair<List<Tag>,Integer>, Error>  getClassTags(List<Token> tokens, int point){
        Result<Pair<List<Tag>,Integer>, Error> res = new Result<>();
        List<Tag> tags = new ArrayList<>();
        switch(tokens.get(point++).getType()){
            case Public:
                tags.add(Tag.Public);
                if(tokens.get(point).getType() != TokenType.Class){
                    res.setError(new Error("Expected Class token found " + tokens.get(point)));
                } else point++;
                break;
            case Private:
                tags.add(Tag.Private);
                if(tokens.get(point).getType() != TokenType.Class){
                    res.setError(new Error("Expected Class token found " + tokens.get(point)));
                } else point++;
                break;
            case Class:
                res.setValue(new Pair<List<Tag>,Integer>(tags, point));
            default: 
                res.setError(new Error("Expected differn't token at start"));
        }
        return res;
    }

    private static Result<Pair<String,Integer>, Error> getName(List<Token> tokens, int point) {
        Result<Pair<String,Integer>, Error> res = new Result<>();
        if(tokens.get(point).getType() != TokenType.Value){
            res.setError(new Error("Name of class cannot be " + tokens.get(point)));
        } else {
            res.setValue(new Pair<>(tokens.get(point).getValue(), point + 1));
        }
        return res;
    }

    private static Result<Boolean,Error> isFunc(List<Token> tokens, int point) {
        Result<Boolean,Error> res = new Result<>();
        while(point < tokens.size()){
            switch(tokens.get(point).getType()){
                case Private: case Public:case Static:case Int:case Double:case Float:case Boolean:case Long:case Value: 
                    point++;
                    break;
                case Equal:
                    res.setValue(false);
                    return res;
                case LBrace:
                    res.setValue(true);
                    return res;
                default:
                    res.setError(new Error("Unexpected character"));
                    return res;
            }
        }
        res.setError(new Error("Confusing line" + tokens));
        return res;
    }

    private static Result<Pair<List<Function>,List<Field>>,Error> getFieldsAndFunctions(List<Token> tokens) {
        int point = 0; 
        Result<Pair<List<Function>,List<Field>>,Error> res = new Result<>();
        List<Function> functions = new ArrayList<>();
        List<Field> fields = new ArrayList<>();
        while(point < tokens.size()){
            var x = isFunc(tokens, point);
            if(x.hasValue()){
                if(x.getValue()){
                    var funcBody = Grabber.grabFunction(tokens, point); 
                    if(funcBody.hasValue()){
                        point = funcBody.getValue().getValue1();
                        var func = FunctionParser.getFunction(funcBody.getValue().getValue0());
                        if(func.hasValue()){
                            functions.add(func.getValue());
                        } else {
                            res.setError(func.getError());
                            return res;
                        }
                    } else {
                        res.setError(funcBody.getError());
                        return res;
                    }
                } else {
                    var fieldBody = Grabber.grabLine(tokens, point); 
                    if(fieldBody.hasValue()){
                        point = fieldBody.getValue().getValue1();
                        var field = LineParser.getField(fieldBody.getValue().getValue0());
                        if(field.hasValue()){
                            fields.add(field.getValue());
                        } else {
                            res.setError(field.getError());
                            return res;
                        }
                    } else {
                        res.setError(fieldBody.getError());
                        return res;
                    }
                }
            } else {
                res.setError(x.getError());
                return res;
            }
        }
        return null;
    }

}

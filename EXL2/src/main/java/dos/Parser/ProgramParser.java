package dos.Parser;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;

import dos.Parser.Builders.ProgramBuilder;
import dos.Parser.Util.Grabber;
import dos.Parser.Util.TagGrabber;
import dos.Tokenizer.Types.Token;
import dos.Tokenizer.Types.TokenType;
import dos.Types.Function;
import dos.Types.Program;
import dos.Types.Lines.Field;
import dos.Util.Result;

public class ProgramParser {
    
    public static Result<Program,Error> toClass(List<Token> tokens){
        Result<Program,Error> res = new Result<>();
        ProgramBuilder pb = new ProgramBuilder();
        int point = 0;
        // Get Class Tags
        var tagsMaybe = TagGrabber.getClassTags(tokens, point);
        if(tagsMaybe.hasError()){res.setError(tagsMaybe.getError()); return res;} 
        pb.addTags(tagsMaybe.getValue().getValue0());
        // Move point to after tags
        point = tagsMaybe.getValue().getValue1();
        if(tokens.get(point++).getType() != TokenType.Class){res.setError(new Error("Expected Class Token got" + tokens.get(point - 1)));return res;}
        // Get Name
        var nameMaybe = getName(tokens, point);
        if(nameMaybe.hasError()){res.setError(nameMaybe.getError()); return res;} 
        pb.setName(nameMaybe.getValue().getValue0());
        // Move point to after Name
        point = nameMaybe.getValue().getValue1();
        var classBodyMaybe = Grabber.grabBracket(tokens, point);
        if(classBodyMaybe.hasError()){res.setError(classBodyMaybe.getError());return res;}
        var classBody = classBodyMaybe.getValue();
        point = classBody.getValue1();
        var z = getFieldsAndFunctions(classBody.getValue0());
        if(z.hasError()){res.setError(z.getError());return res;}
        List<Function> funcs = z.getValue().getValue0();
        List<Field> fields = z.getValue().getValue1();
        funcs.forEach(x -> pb.addFunction(x));
        fields.forEach(x -> pb.addField(x));
        res.setValue(pb.build());
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
                case Equal:
                    res.setValue(false);
                    return res;
                case LBrace:
                    res.setValue(true);
                    return res;
                default:
                    point++;
            }
        }
        res.setError(new Error("Unknown line " + tokens));
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
                    var funcBody = Grabber.grabFunction(tokens, point + 1); 
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
        res.setValue(new Pair<List<Function>,List<Field>>(functions, fields));
        return res;
    }

}

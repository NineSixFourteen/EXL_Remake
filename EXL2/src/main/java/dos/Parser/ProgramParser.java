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
        point = getClassTags(pb, res, tokens, point);
        if(!res.isOk()){return res;}
        point = getName(pb, res, tokens, point);
        if(!res.isOk()){return res;}
        var x = Grabber.grabBracket(tokens, point);
        if(!x.isOk()){res.setError(x.getError());return res;}
        var y = x.getValue();
        point = y.getValue1();
        var z = getFieldsAndFunctions(y.getValue0());
        return res;
    }

    private static Result<Pair<List<Function>,List<Field>>,Error> getFieldsAndFunctions(List<Token> tokens) {
        int point = 0; 
        Result<Pair<List<Function>,List<Field>>,Error> res = new Result<>();
        List<Function> functions = new ArrayList<>();
        List<Field> fields = new ArrayList<>();
        while(point < tokens.size()){
            var x = isFunc(tokens, point);
            if(x.isOk()){
                if(x.getValue()){
                    var funcBody = Grabber.grabFunction(tokens, point); 
                    if(funcBody.isOk()){
                        point = funcBody.getValue().getValue1();
                        var func = FunctionParser.getFunction(funcBody.getValue().getValue0());
                        if(func.isOk()){
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
                    if(fieldBody.isOk()){
                        point = fieldBody.getValue().getValue1();
                        var field = LineParser.getField(fieldBody.getValue().getValue0());
                        if(field.isOk()){
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

    private static int getClassTags(ProgramBuilder pb, Result<Program,Error> res, List<Token> tokens, int point){
        switch(tokens.get(point++).getType()){
            case Public:
                pb.addTag(Tag.Public);
                if(tokens.get(point).getType() != TokenType.Class){
                    res.setError(new Error("Expected Class token found " + tokens.get(point)));
                } else point++;
                break;
            case Private:
                pb.addTag(Tag.Private);
                if(tokens.get(point).getType() != TokenType.Class){
                    res.setError(new Error("Expected Class token found " + tokens.get(point)));
                } else point++;
                break;
            case Class:
                break;
            default: 
                res.setError(new Error("Expected differn't token at start"));
        }
        return point;
    }

    private static int getName(ProgramBuilder pb, Result<Program, Error> res, List<Token> tokens, int point) {
        if(tokens.get(point).getType() != TokenType.Value){
            res.setError(new Error("Name of class cannot be " + tokens.get(point)));
        } else {
            pb.setName(tokens.get(point).getValue());
            point++;
        }
        return point;
    }

}

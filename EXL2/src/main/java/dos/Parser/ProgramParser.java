package dos.Parser;

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
        return null;
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

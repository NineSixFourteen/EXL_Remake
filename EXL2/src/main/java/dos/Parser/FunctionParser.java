package dos.Parser;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;

import dos.Parser.Builders.FunctionBuilder;
import dos.Parser.Util.Grabber;
import dos.Tokenizer.Types.Token;
import dos.Tokenizer.Types.TokenType;
import dos.Types.Function;
import dos.Types.Tag;
import dos.Util.Result;

public class FunctionParser {

    public static Result<Function, Error> getFunction(List<Token> tokens){
        FunctionBuilder fb = new FunctionBuilder();
        Result<Function, Error> res = new Result<>();
        var tagsMaybe = getClassTags(tokens);
        if(tagsMaybe.hasError()){res.setError(tagsMaybe.getError());return res;}
        for(Tag t : tagsMaybe.getValue().getValue0()){
            fb.addTag(t);
        }
        int point = tagsMaybe.getValue().getValue1();
        var typeMaybe = getType(tokens, point++);
        if(typeMaybe.hasError()){res.setError(typeMaybe.getError());return res;}
        fb.setType(typeMaybe.getValue());
        var nameMaybe = getName(tokens, point++);
        if(nameMaybe.hasError()){res.setError(nameMaybe.getError());return res;}
        fb.setName(nameMaybe.getValue());
        var codeBlockMaybe = Grabber.grabBracket(tokens, point);
        if(codeBlockMaybe.hasError()){res.setError(codeBlockMaybe.getError());return res;}
        var bodyMaybe = CodeBlockParser.getCodeBlock(codeBlockMaybe.getValue().getValue0());
        if(bodyMaybe.hasError()){res.setError(bodyMaybe.getError());return res;}
        fb.setBody(bodyMaybe.getValue());
        res.setValue(fb.build());
        return res;
    }

    private static Result<Pair<List<Tag>,Integer>, Error> getClassTags(List<Token> tokens){
        Result<Pair<List<Tag>,Integer>, Error> res = new Result<>();
        boolean tag = false;
        List<Tag> tags = new ArrayList<>();
        int point = 0;
        int privateT = 0;
        int publicT = 0;
        int staticT = 0;
        while(tag){
            switch(tokens.get(point++).getType()){
                case Public:
                    publicT++;
                    tags.add(Tag.Public);
                    break;
                case Private:
                    privateT++;
                    tags.add(Tag.Private);
                    break;
                case Static:
                    staticT++;
                    tags.add(Tag.Private);
                    break;
                default:
                    tag = false;
            }
        }
        if(privateT > 0){
            if(privateT > 1){
                res.setError(new Error("You only need one private tag for a function. " + privateT + " is to many it doesn't get more private trust me." ));
            }
            if(publicT > 0){
                res.setError(new Error("Function can not be private and public at the same time pick one"));
            }
        }
        if(publicT > 0){
            if(publicT > 1){
                res.setError(new Error("You only need one public tag for a function. " + publicT + " is to many it doesn't get more public trust me." ));
            }
        }
        if(staticT > 1){
            res.setError(new Error("You only need one static tag for a function. " + staticT + " is to many it doesn't get more static watever that would mean." ));
        }
        if(!res.hasError()){
            res.setValue(new Pair<List<Tag>,Integer>(tags, point));
        }
        return res;
    }

    private static Result<String,Error> getType(List<Token> tokens, int point) {
        Result<String,Error> res = new Result<>();
        String type;
        switch(tokens.get(point).getType()){
            case Int:
                res.setValue("Int");
                break;
            case Float:
                res.setValue("Int");
                break;
            case String:
                res.setValue("Int");
                break;
            case Boolean:
                res.setValue("Int");
                break;
            case Long:
                res.setValue("Int");
                break;
            case Short:
                res.setValue("Int");
                break;
            case Value:
                res.setValue("Int");
                break;
            default:
                res.setError(new Error("Unknown Type for function"));
        }
        return res;
    }

    private static Result<String, Error> getName(List<Token> tokens, int point) {
        Result<String, Error> res = new Result<>();
        if(tokens.get(point).getType() != TokenType.Value){
            res.setError(new Error("Expected name of function instead found token " + tokens.get(point)));
        } else {
            res.setValue(tokens.get(point).getValue());
        }
        return res;
    }


    
}

package dos.Parser;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;

import dos.Parser.Builders.FunctionBuilder;
import dos.Parser.Util.Grabber;
import dos.Parser.Util.Seperator;
import dos.Tokenizer.Types.Token;
import dos.Tokenizer.Types.TokenType;
import dos.Types.Function;
import dos.Types.Tag;
import dos.Util.Result;

public class FunctionParser {

    public static Result<Function, Error> getFunction(List<Token> tokens){
        FunctionBuilder fb = new FunctionBuilder();
        Result<Function, Error> res = new Result<>();
        //Get Tags and add them to function builder
        var tagsMaybe = getClassTags(tokens);
        if(tagsMaybe.hasError()){res.setError(tagsMaybe.getError());return res;}
        for(Tag t : tagsMaybe.getValue().getValue0()){
            fb.addTag(t);
        }
        int point = tagsMaybe.getValue().getValue1();
        // Grab Type of function and add to FB 
        var typeMaybe = getType(tokens, point++);
        if(typeMaybe.hasError()){res.setError(typeMaybe.getError());return res;}
        fb.setType(typeMaybe.getValue());
        // Grab Name of function and add to FB 
        var nameMaybe = getName(tokens, point++);
        if(nameMaybe.hasError()){res.setError(nameMaybe.getError());return res;}
        fb.setName(nameMaybe.getValue());
        // Grab the parameters/ arguments for the function and add them to Function Builder
        var argsMaybe = Grabber.grabBracket(tokens, point);
        if(argsMaybe.hasError()){res.setError(argsMaybe.getError());return res;}
        var argsSep = Seperator.splitOnCommas(argsMaybe.getValue().getValue0());
        for(int i = 0; i < argsSep.size();i++){
            var paramMaybe = parseParam(argsSep.get(i)); 
            if(paramMaybe.hasError()){res.setError(paramMaybe.getError());return res;}
            fb.addParameter(paramMaybe.getValue().getValue0(), paramMaybe.getValue().getValue1());
        }
        var codeBlockMaybe = Grabber.grabBracket(tokens, argsMaybe.getValue().getValue1());
        var bodyMaybe = CodeBlockParser.getCodeBlock(codeBlockMaybe.getValue().getValue0());
        if(bodyMaybe.hasError()){res.setError(bodyMaybe.getError());return res;}
        fb.setBody(bodyMaybe.getValue());
        res.setValue(fb.build());
        return res;
    }

    private static Result<Pair<String,String>, Error> parseParam(List<Token> list) {
        Result<Pair<String,String>, Error> res = new Result<>();
        if(list.size() != 2){
            res.setError(new Error("To many tokens in a parameter only need type and name of parameter"));
            return res;
        }
        var type = getType(list, 0);
        if(type.hasError()){res.setError(type.getError());return res;}
        if(list.get(1).getType() != TokenType.Value){
            res.setError(new Error("name of parameter has to be unique cannot be " + list.get(1).getType() ));
            return res;
        }
        res.setValue(new Pair<String,String>(type.getValue(), list.get(1).getValue()));
        return res;
    }

    private static Result<Pair<List<Tag>,Integer>, Error> getClassTags(List<Token> tokens){
        Result<Pair<List<Tag>,Integer>, Error> res = new Result<>();
        boolean tag = true;
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
                    tags.add(Tag.Static);
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
            res.setValue(new Pair<List<Tag>,Integer>(tags, point - 1));
        }
        return res;
    }

    private static Result<String,Error> getType(List<Token> tokens, int point) {
        Result<String,Error> res = new Result<>();
        switch(tokens.get(point).getType()){
            case Int:
                res.setValue("int");
                break;
            case Float:
                res.setValue("float");
                break;
            case String:
                res.setValue("String");
                break;
            case Boolean:
                res.setValue("boolean");
                break;
            case Long:
                res.setValue("long");
                break;
            case Short:
                res.setValue("shory");
                break;
            case Void:
                res.setValue("void");
                break;
            case Value:
                res.setValue("todo- FuncParser");
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

package dos.EXL.Parser;

import java.util.List;

import org.javatuples.Pair;

import dos.EXL.Parser.Builders.FunctionBuilder;
import dos.EXL.Parser.Util.Grabber;
import dos.EXL.Parser.Util.Seperator;
import dos.EXL.Parser.Util.TagGrabber;
import dos.EXL.Tokenizer.Types.Token;
import dos.EXL.Tokenizer.Types.TokenType;
import dos.EXL.Types.Function;
import dos.EXL.Types.Tag;
import dos.Util.Result;
import dos.Util.Results;

public class FunctionParser {

    public static Result<Function> getFunction(List<Token> tokens){
        FunctionBuilder fb = new FunctionBuilder();
        //Get Tags and add them to function builder
        var tagsMaybe = TagGrabber.getClassTags(tokens, 0);
        if(tagsMaybe.hasError()) return Results.makeError(tagsMaybe.getError());
        for(Tag t : tagsMaybe.getValue().getValue0()){
            fb.addTag(t);
        }
        int point = tagsMaybe.getValue().getValue1();
        // Grab Type of function and add to FB 
        var typeMaybe = getType(tokens, point++);
        if(typeMaybe.hasError()) return Results.makeError(typeMaybe.getError());
        fb.setType(typeMaybe.getValue());
        // Grab Name of function and add to FB 
        var nameMaybe = getName(tokens, point++);
        if(nameMaybe.hasError()) return Results.makeError(typeMaybe.getError());
        fb.setName(nameMaybe.getValue());
        // Grab the parameters/ arguments for the function and add them to Function Builder
        var argsMaybe = Grabber.grabBracket(tokens, point);
        if(argsMaybe.hasError()) return Results.makeError(typeMaybe.getError());
        var argsSep = Seperator.splitOnCommas(argsMaybe.getValue().getValue0());
        for(int i = 0; i < argsSep.size();i++){
            var paramMaybe = parseParam(argsSep.get(i)); 
            if(paramMaybe.hasError()) return Results.makeError(typeMaybe.getError());
            fb.addParameter(paramMaybe.getValue().getValue0(), paramMaybe.getValue().getValue1());
        }
        var codeBlockMaybe = Grabber.grabBracket(tokens, argsMaybe.getValue().getValue1());
        var bodyMaybe = CodeBlockParser.getCodeBlock(codeBlockMaybe.getValue().getValue0());
        if(bodyMaybe.hasError()) return Results.makeError(typeMaybe.getError());
        return Results.makeResult(fb.setBody(bodyMaybe.getValue()).build());
    }

    private static Result<Pair<String,String>> parseParam(List<Token> list) {
        if(list.size() != 2){
            return Results.makeError("To many tokens in a parameter only need type and name of parameter");
        }
        var type = getType(list, 0);
        if(type.hasError()) return Results.makeError(type.getError());
        if(list.get(1).getType() != TokenType.Value){
            return Results.makeError("name of parameter has to be unique cannot be " + list.get(1).getType());
        }
        return Results.makeResult(new Pair<String,String>(type.getValue(), list.get(1).getValue()));
    }

    private static Result<String> getType(List<Token> tokens, int point) {
        switch(tokens.get(point).getType()){
            case Int:
                return Results.makeResult("int");
            case Float:
                return Results.makeResult("float");
            case String:
                return Results.makeResult("String");
            case Boolean:
                return Results.makeResult("boolean");
            case Long:
                return Results.makeResult("long");
            case Short:
                return Results.makeResult("shory");
            case Void:
                return Results.makeResult("void");
            case Value:
                return Results.makeResult("todo- FuncParser");
            case Char:
                return Results.makeResult("char");
            default:
                return Results.makeError("Unknown Type for Function Parser"  + tokens.get(point));
        }
    }

    private static Result<String> getName(List<Token> tokens, int point) {
        if(tokens.get(point).getType() != TokenType.Value){
            return Results.makeError("Expected name of function instead found token " + tokens.get(point));
        } else {
            return Results.makeResult(tokens.get(point).getValue());
        }
    }


    
}

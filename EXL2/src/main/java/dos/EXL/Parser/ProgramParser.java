package dos.EXL.Parser;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;
import org.javatuples.Quartet;

import dos.EXL.Parser.Builders.ProgramBuilder;
import dos.EXL.Parser.Lines.FieldParser;
import dos.EXL.Parser.Util.FunctionType;
import dos.EXL.Parser.Util.Grabber;
import dos.EXL.Parser.Util.TagGrabber;
import dos.EXL.Tokenizer.Types.Token;
import dos.EXL.Tokenizer.Types.TokenType;
import dos.EXL.Types.Function;
import dos.EXL.Types.Program;
import dos.EXL.Types.Errors.ErrorFactory;
import dos.EXL.Types.Lines.Field;
import dos.Util.Maybe;
import dos.Util.Result;
import dos.Util.Results;

public class ProgramParser {
    
    public static Result<Program> toClass(List<Token> tokens){
        ProgramBuilder pb = new ProgramBuilder();
        var importsMaybe = ImportParser.parse(tokens);
        if(importsMaybe.hasError())
            return Results.makeError(importsMaybe.getError());
        pb.addImports(importsMaybe.getValue());
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
        var z = getFieldsAndFunctionsAndCons(classBody.getValue0(),nameMaybe.getValue().getValue0());
        if(z.hasError())
            return Results.makeError(z.getError());
        var data = z.getValue();
        List<Function> constructs = data.getValue1();
        List<Function> funcs = data.getValue2();
        List<Field> fields = data.getValue3();
        pb.setMain(data.getValue0());
        funcs.forEach(x -> pb.addFunction(x));
        fields.forEach(x -> pb.addField(x));
        constructs.forEach(x -> pb.addConstructor(x));
        return Results.makeResult(pb.build());
    }

    private static Result<Pair<String,Integer>> getName(List<Token> tokens, int point) {
        if(tokens.get(point).getType() != TokenType.Value){
            return Results.makeError(ErrorFactory.makeParser("Expected name of class instead found token " + tokens.get(point),2));
        } else {
            return Results.makeResult(new Pair<>(tokens.get(point).getValue(), point + 1));
        }
    }

    private static Result<Quartet<Maybe<Function>,List<Function>, List<Function>,List<Field>>> getFieldsAndFunctionsAndCons(List<Token> tokens, String string) {
        int point = 0; 
        List<Function> functions = new ArrayList<>();
        List<Field> fields = new ArrayList<>();
        List<Function> constructors = new ArrayList<>();
        Maybe<Function> Main = new Maybe<>();
        while(point < tokens.size()){
            var x = getFunctionType(tokens,point, string);
            if(x.hasError())
                return Results.makeError(x.getError());
            switch(x.getValue()){
                case Constructor:
                    var consMaybe = getCons(tokens, point, string);
                    if(consMaybe.hasError())
                        return Results.makeError(consMaybe.getError());
                    var cons = consMaybe.getValue();
                    point = cons.getValue1();
                    constructors.add(cons.getValue0());
                    break;
                case Field:
                    var fieldMaybe = getField(tokens, point);
                    if(fieldMaybe.hasError())
                        return Results.makeError(fieldMaybe.getError());
                    var field = fieldMaybe.getValue();
                    point = field.getValue1();
                    fields.add(field.getValue0());
                    break;
                case Function:
                    var funcMaybe = getFunction(tokens,point);
                    if(funcMaybe.hasError())
                        return Results.makeError(funcMaybe.getError());
                    var func = funcMaybe.getValue();
                    point = func.getValue1();
                    functions.add(func.getValue0());
                    break;
                case Main:
                    if(Main.hasValue()){
                        return Results.makeError(ErrorFactory.makeParser("Multiple functions declared as main",17));
                    }
                    var mainMaybe = getFunction(tokens,point);
                    if(mainMaybe.hasError())
                        return Results.makeError(mainMaybe.getError());
                    var main = mainMaybe.getValue();
                    point = main.getValue1();
                    Main = new Maybe<>(main.getValue0());
                    break;
                default:
                    break; 
            }
        }
        return Results.makeResult(new Quartet<>(Main,constructors,functions, fields));
    }

    private static Result<FunctionType> getFunctionType(List<Token> tokens, int point, String className){
        //Skip Tags
        boolean b = true;
        while(point < tokens.size() && b){
            switch(tokens.get(point).getType()){
                case Static:
                case Private:
                case Public:
                    point++;
                    break;
                default:
                    b = false;
            }
        }
        if(point >= tokens.size())
            return  Results.makeError(ErrorFactory.makeParser("Expected to find type but found nothing",2));
        switch(tokens.get(point).getType()){
            case Int:
            case Long:
            case Double:
            case Float:
            case Char:
            case String:
            case Short:
            case Boolean:
            case Void:
                point++;
                break;
            case Value:
                if(tokens.get(point).getValue().equals(className))
                    return Results.makeResult(FunctionType.Constructor);
                point++;
            default:
                return Results.makeError(ErrorFactory.makeParser("Expected to find type but found " + tokens.get(point) + " instead",1));
        }
        if(point >= tokens.size() || tokens.get(point).getType() != TokenType.Value)
            return Results.makeError(ErrorFactory.makeParser("Expected to name but found " + (point >= tokens.size() ? " nothhing" : tokens.get(point)) ,1));
        if(tokens.get(point).getValue().equals("main")){
            return  Results.makeResult(FunctionType.Main);
        }
        point++;
        if(point >= tokens.size())
            return Results.makeError(ErrorFactory.makeParser("Expected to find either = or ( but found nothing",2));
        switch(tokens.get(point).getType()){
            case LBracket:
                return  Results.makeResult(FunctionType.Function);
            case Equal:
                return  Results.makeResult(FunctionType.Field);
            default:
                return Results.makeError(ErrorFactory.makeParser("Expected to find either = or ( but found " + tokens.get(point) + " instead",2));
        }
    }

    private static Result<Pair<Function,Integer>> getCons(List<Token> tokens, int point, String name) {
        var funcBody = Grabber.grabFunction(tokens, point); 
        if(funcBody.hasValue()){
            point = funcBody.getValue().getValue1();
            var func = FunctionParser.getConstruct(funcBody.getValue().getValue0(), name);
            if(func.hasValue()){
                return Results.makeResult(new Pair<>(func.getValue(),point));
            } else {
                return Results.makeError(func.getError());
            }
        }
        return Results.makeError(funcBody.getError());
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

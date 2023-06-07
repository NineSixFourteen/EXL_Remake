package dos.EXL.Parser;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;

import dos.EXL.Parser.Util.Grabber;
import dos.EXL.Tokenizer.Types.Token;
import dos.EXL.Tokenizer.Types.TokenType;
import dos.EXL.Types.Errors.ErrorFactory;
import dos.Util.Result;
import dos.Util.Results;

public class ImportParser {

    public static Result<List<Pair<String,String>>> parse(List<Token> tokens){
        int point = 0;
        List<Pair<String,String>> importPaths = new ArrayList<>();
        while(point < tokens.size() && tokens.get(point).getType() == TokenType.Import){
            point++;
            Result<Pair<List<Token>,Integer>> pathTokens = Grabber.grabPath(tokens, point);
            if(pathTokens.hasError())
                return Results.makeError(pathTokens.getError());
            point = pathTokens.getValue().getValue1();
            if(point < tokens.size()){
                switch(tokens.get(point).getType()){
                    case SemiColan:
                        importPaths.add(getPaths(pathTokens.getValue().getValue0()));
                        break;
                    case as:
                        point++;
                        if(point + 1 < tokens.size()){
                            if(tokens.get(point).getType() == TokenType.Value){
                                String name = tokens.get(point).getValue();
                                importPaths.add(makePaths(name, pathTokens.getValue().getValue0()));
                                if(tokens.get(point + 1).getType() != TokenType.SemiColan){
                                    return Results.makeError(ErrorFactory.makeParser("Expected a semicolan(;), found " + tokens.get(point) ,2));
                                }
                                point += 2;
                                break;
                            } else {
                                Results.makeError(ErrorFactory.makeParser("Name of import has to be a value, it cannot be " + tokens.get(point), point));
                            }
                        } else{
                            return Results.makeError(ErrorFactory.makeParser("Expected name of import and a semicolan(;) after as" ,2));
                        }
                    default:
                        return Results.makeError(ErrorFactory.makeParser("Expected either as or semicolan(;) instead found " + tokens.get(point) ,2));
                }
            }
            else 
                return Results.makeError(ErrorFactory.makeParser("Expected either as or semicolan(;) after import path " ,2));
        }
        return Results.makeResult(importPaths);
    }

    private static Pair<String, String> makePaths(String name, List<Token> path) {
        return new Pair<>(name, makeShortPath(path));
    }

    private static Pair<String, String> getPaths(List<Token> path) {
        return new Pair<>(makePath(path), makeShortPath(path));
    }

    private static String makeShortPath(List<Token> path) {
        StringBuilder sb = new StringBuilder();
        for(Token t: path){
            switch(t.getType()){
                case Dot:
                    sb.append(".");
                    break;
                case Value:
                    sb.append(t.getValue());
                    break;
                default:
                    break;
            }
        }
        return sb.toString();
    }

    private static String makePath(List<Token> path) {
        return path.get(path.size() - 1).getValue();
    }
    
}

package dos.EXL.Parser.Util;

import java.util.List;

import org.javatuples.Pair;

import dos.EXL.Tokenizer.Types.Token;
import dos.EXL.Tokenizer.Types.TokenType;
import dos.EXL.Types.Errors.ErrorFactory;
import dos.Util.Result;
import dos.Util.Results;

public class Grabber {

    public static Result<Pair<List<Token>,Integer>> grabBracket(List<Token> tokens, int point){
        int start = point;
        Token t = tokens.get(point);
        TokenType ty;
        switch(t.getType()){
            case LBrace:
                ty = TokenType.RBrace;
                break;
            case LSquare:
                ty = TokenType.RSquare;
                break;
            case LThan:
                ty = TokenType.GThan;
                break;
            case LBracket:
                ty = TokenType.RBracket;
                break;
            default:
                return Results.makeError(ErrorFactory.makeParser("Expected a Bracket got " + tokens.get(point),0));

        } 
        point++;
        int open = 0;
        while(point < tokens.size()){
            if(tokens.get(point).getType() == ty){
                if(open == 0){
                    return Results.makeResult(new Pair<List<Token>,Integer>(tokens.subList(start + 1, point), point + 1));
                } else {
                    open--;
                }
            } else if(tokens.get(point).getType() == t.getType()){
                open++;
            }
            point++;
        }
        return Results.makeError(ErrorFactory.makeParser("Could not find matching closing bracket for " + t,3));
    }

    public static Result<Pair<List<Token>, Integer>> grabFunction(List<Token> tokens, int point){
        int start = point;
        while(tokens.get(point).getType() != TokenType.LBrace){
            point++;
        }
        Result<Pair<List<Token>, Integer>> body = grabBracket(tokens, point);
        if(body.hasValue()){
            int end = body.getValue().getValue1();
            return Results.makeResult(new Pair<List<Token>,Integer>(tokens.subList(start, end), end));
        } else {
            return Results.makeError(body.getError());
        }
    }

    public static Result<Pair<List<Token>, Integer>> grabLine(List<Token> tokens, int point){
        int start = point;
        while(point < tokens.size()){
            if(tokens.get(point).getType() == TokenType.SemiColan){
                return Results.makeResult(new Pair<List<Token>,Integer>(tokens.subList(start, point), point));
            } else{
                point++;
            }
        }
        return Results.makeError(ErrorFactory.makeParser("Cant find ending of line",5));
    }

    public static Result<Pair<List<Token>, Integer>> grabNextLine(List<Token> tokens, int point){
        int start = point;
        while(point < tokens.size() && (tokens.get(point).getType() != TokenType.SemiColan && tokens.get(point).getType() != TokenType.LBrace)){
            if(tokens.get(point).getType() == TokenType.LBracket){
                while(tokens.get(point).getType() != TokenType.RBracket && point < tokens.size()){
                    point++;
                }
            }
            point++;
        }
        if(point >= tokens.size()){
            return Results.makeError(ErrorFactory.makeParser("Cant find ending of line",5));
        }
        switch(tokens.get(point).getType()){
            case SemiColan:
                return Results.makeResult(new Pair<List<Token>,Integer>(tokens.subList(start, point + 1), point + 1));
            case LBrace:
                Result<Pair<List<Token>, Integer>> brace = grabBracket(tokens, point);
                if(brace.hasError())return Results.makeError(brace.getError());
                point = brace.getValue().getValue1();
                return Results.makeResult(new Pair<List<Token>,Integer>(tokens.subList(start, point),point ));
            default:
                return Results.makeError(ErrorFactory.makeParser("HOW THE FUDGE DID THIS HAPPEN ..Grabber" + tokens,0));
        }
    }

    public static Result<Pair<List<Token>, Integer>> grabBoolean(List<Token> tokens, int point){
        int start = point;
        while(point < tokens.size() && tokens.get(point).getType() != TokenType.And && tokens.get(point).getType() != TokenType.Or){
            if(tokens.get(point).getType() == TokenType.LBracket){
                var bracMaybe = grabBracket(tokens, point);
                if(bracMaybe.hasError())return Results.makeError(bracMaybe.getError());
                point = bracMaybe.getValue().getValue1() - 1;
            }
            point++;
        }
        return Results.makeResult(new Pair<List<Token>,Integer>(tokens.subList(start, point), point));
    }

    public static Result<Pair<List<Token>, Integer>> grabExpression(List<Token> tokens, int i) {
        int start = i++;
        boolean b = true;
        while(i < tokens.size() && b){
            switch(tokens.get(i).getType()){
                case LBracket:
                    var place = Grabber.grabBracket(tokens, i);
                    if(place.hasError())return place;
                    i = place.getValue().getValue1() + 1;
                    break;
                case Dot:
                    i +=2 ; 
                    break;
                default: 
                    b = false;
            }
        }
        return Results.makeResult(new Pair<List<Token>,Integer>(tokens.subList(start, i - 1), i - 1));
    }
    
}

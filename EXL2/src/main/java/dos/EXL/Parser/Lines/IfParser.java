package dos.EXL.Parser.Lines;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.IntStream;

import org.javatuples.Pair;
import org.javatuples.Triplet;

import dos.EXL.Parser.CodeBlockParser;
import dos.EXL.Parser.ExpressionParser;
import dos.EXL.Parser.Factorys.LineFactory;
import dos.EXL.Parser.Util.Grabber;
import dos.EXL.Tokenizer.Types.Token;
import dos.EXL.Tokenizer.Types.TokenType;
import dos.EXL.Types.Expression;
import dos.EXL.Types.Line;
import dos.EXL.Types.Binary.Boolean.BoolExpr;
import dos.EXL.Types.Errors.ErrorFactory;
import dos.EXL.Types.Lines.CodeBlock;
import dos.Util.Result;
import dos.Util.Results;

public class IfParser {

    public static Result<Triplet<BoolExpr,CodeBlock, List<Line>>> parse(List<Token> tokens){
        OptionalInt index = IntStream.range(0, tokens.size())
                     .filter(i -> TokenType.LBrace.equals(tokens.get(i).getType()))
                     .findFirst();
        if(index.isEmpty())           
            return Results.makeError(ErrorFactory.makeParser("Expected to find { symbol in if line", 2));
        if(index.getAsInt() == 1)
            return Results.makeError(ErrorFactory.makeParser("Expected an expression after if", 4));
        var exprMaybe = ExpressionParser.parse(tokens.subList(1, index.getAsInt()));
        if(exprMaybe.hasError())
            return Results.makeError(exprMaybe.getError());
        var booleanMaybe = toBool(exprMaybe.getValue());
        if(booleanMaybe.hasError())
            return Results.makeError(booleanMaybe.getError());
        var bodyMaybe = Grabber.grabBracket(tokens, index.getAsInt());
        if(bodyMaybe.hasError())
            return Results.makeError(bodyMaybe.getError());
        var body = CodeBlockParser.getCodeBlock(bodyMaybe.getValue().getValue0());
        if(body.hasError())
            return Results.makeError(body.getError());
        int point = bodyMaybe.getValue().getValue1();
        List<Line> elses = new ArrayList<>();
        while(tokens.size() > point + 1 && tokens.get(point).getType() == TokenType.Else){
            var x = getElse(tokens, point);
            if(x.hasError())
                return Results.makeError(x.getError());
            elses.add(x.getValue().getValue0());
            point = x.getValue().getValue1();
        }
        return Results.makeResult(new Triplet<>(booleanMaybe.getValue(), body.getValue(), elses));
    }

    private static Result<Pair<Line, Integer>> getElse(List<Token> tokens, int point) {
        if(tokens.size() > point + 2){
            switch(tokens.get(point + 1).getType()){
                case If:
                    return ElseIfParser.getElse(tokens, point);
                default:
                    return ElseParser.getElse(tokens, point);
            }
        }
        return Results.makeError(ErrorFactory.makeParser("Expected tokens after Else", 2));
    }

    public static Result<Line> getLine(List<Token> tokens){
        var ifMaybe = parse(tokens);
        if(ifMaybe.hasError()) 
            return Results.makeError(ifMaybe.getError());
        var ifParts = ifMaybe.getValue();
        return Results.makeResult(LineFactory.ifL(ifParts.getValue0(),ifParts.getValue1(), ifParts.getValue2()));
    }

        private static Result<BoolExpr> toBool(Expression bool){
        try{
            BoolExpr bol = (BoolExpr) bool;
            return Results.makeResult(bol);
        } catch(Exception e){
            return Results.makeError(ErrorFactory.makeLogic("The expression " + bool + " is not boolean yet you put it next to a AND why would u do that u knew or something xD cringe", 2));
        }
    }

}

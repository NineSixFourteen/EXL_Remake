package dos.EXL.Parser.Lines;

import java.util.List;
import java.util.OptionalInt;
import java.util.stream.IntStream;

import org.javatuples.Quartet;

import dos.EXL.Parser.CodeBlockParser;
import dos.EXL.Parser.ExpressionParser;
import dos.EXL.Parser.LineParser;
import dos.EXL.Parser.Factorys.LineFactory;
import dos.EXL.Parser.Util.Grabber;
import dos.EXL.Parser.Util.Seperator;
import dos.EXL.Tokenizer.Types.Token;
import dos.EXL.Tokenizer.Types.TokenType;
import dos.EXL.Types.Line;
import dos.EXL.Types.Binary.Boolean.BoolExpr;
import dos.EXL.Types.Binary.Boolean.LThanExpr;
import dos.EXL.Types.Errors.ErrorFactory;
import dos.EXL.Types.Lines.CodeBlock;
import dos.EXL.Types.Lines.DeclarLine;
import dos.Util.Result;
import dos.Util.Results;

public class ForParser { //TODO 
    
    public static Result<Quartet<DeclarLine,BoolExpr,Line,CodeBlock>> parse(List<Token> tokens){//TODOOOO
        OptionalInt index = IntStream.range(0, tokens.size())
                .filter(i -> TokenType.LBrace.equals(tokens.get(i).getType()))
                .findFirst();
        if(index.isEmpty())           
            return Results.makeError(ErrorFactory.makeParser("Expected to find { symbol in for line", 2));
        if(index.getAsInt() == 1)
            return Results.makeError(ErrorFactory.makeParser("Expected an expression after for", 4));
        List<Token> stuff = tokens.subList(1, index.getAsInt());
        var parts = Seperator.splitOnAndKeepSemiColans(stuff);
        if(parts.size() != 3)
            return Results.makeError(ErrorFactory.makeParser("Expected 3 parts to if i.e. int i = 0; i<3; i++ but instead found " + parts.size() + " parts", 4));
        var declare = DeclareLineParser.parse(parts.get(0));
        if(declare.hasError())
            return Results.makeError(declare.getError());
        var bool = ExpressionParser.parse(parts.get(1).subList(0,parts.get(1).size() - 1));
        BoolExpr realbool;
        try{
            realbool = (BoolExpr) bool.getValue();
        } catch(Exception e){
            return Results.makeError(ErrorFactory.makeParser("Second part of For statement must be a boolean expr" + bool.getValue().makeString() + " is not boolean", 4));
        }
        var line = LineParser.getLine(parts.get(2));
        var bodyMaybe = Grabber.grabBracket(tokens, index.getAsInt());
        if(bodyMaybe.hasError())
            return Results.makeError(bodyMaybe.getError());
        var body = CodeBlockParser.getCodeBlock(bodyMaybe.getValue().getValue0());
        if(body.hasError())
            return Results.makeError(body.getError());
        DeclarLine linee = new DeclarLine(declare.getValue().getValue1(), declare.getValue().getValue0(),declare.getValue().getValue2());
        return Results.makeResult(new Quartet<>(linee, realbool, line.getValue(), body.getValue()));
    }

    public static Result<Line> getLine(List<Token> tokens){
        var forMaybe = parse(tokens);
        if(forMaybe.hasError()) 
            return Results.makeError(forMaybe.getError());
        var forParts = forMaybe.getValue();
        return Results.makeResult(LineFactory.forL(forParts.getValue0(),forParts.getValue1(),forParts.getValue2(),forParts.getValue3()));
    }
}

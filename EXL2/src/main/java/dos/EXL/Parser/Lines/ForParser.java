package dos.EXL.Parser.Lines;

import java.util.List;

import org.javatuples.Quartet;

import dos.EXL.Parser.Factorys.LineFactory;
import dos.EXL.Tokenizer.Types.Token;
import dos.EXL.Types.Line;
import dos.EXL.Types.Lines.CodeBlock;
import dos.EXL.Types.Lines.DeclarLine;
import dos.EXL.Types.Unary.Types.BooleanExpr;
import dos.Util.Result;
import dos.Util.Results;

public class ForParser { //TODO 
    
    public static Result<Quartet<DeclarLine,BooleanExpr,Line,CodeBlock>> parse(List<Token> tokens){//TODOOOO
        return null;
    }

    public static Result<Line> getLine(List<Token> tokens){
        var forMaybe = parse(tokens);
        if(forMaybe.hasError()) 
            return Results.makeError(forMaybe.getError());
        var forParts = forMaybe.getValue();
        return Results.makeResult(LineFactory.forL(forParts.getValue0(),forParts.getValue1(),forParts.getValue2(),forParts.getValue3()));
    }
}

package dos.EXL.Parser;

import java.util.List;

import dos.EXL.Parser.Builders.CodeBlockBuilder;
import dos.EXL.Parser.Util.Grabber;
import dos.EXL.Tokenizer.Types.Token;
import dos.EXL.Types.MyError;
import dos.EXL.Types.Lines.CodeBlock;
import dos.Util.Maybe;
import dos.Util.Result;
import dos.Util.Results;

public class CodeBlockParser {

    public static Result<CodeBlock> getCodeBlock(List<Token> tokens){
        CodeBlockBuilder cbb = new CodeBlockBuilder();
        int point = 0; 
        Maybe<MyError> lineRes;
        while(point < tokens.size()){
            var nextLine = Grabber.grabNextLine(tokens, point);
            if(nextLine.hasError())
                return Results.makeError(nextLine.getError());
            point = nextLine.getValue().getValue1();
            lineRes = addLine(nextLine.getValue().getValue0(), cbb);
            if(lineRes.hasValue())
                return Results.makeError(lineRes.getValue());
        }
        return Results.makeResult(cbb.build());
    }

    private static Maybe<MyError> addLine(List<Token> tokens, CodeBlockBuilder cbb) {
        var line = LineParser.getLine(tokens);
        if(line.hasError())
            return new Maybe<MyError>(line.getError());
        cbb.addLine(line.getValue());
        return new Maybe<>();
    }
    
}

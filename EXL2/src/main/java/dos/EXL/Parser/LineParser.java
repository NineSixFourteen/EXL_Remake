package dos.EXL.Parser;

import java.util.List;
import java.util.OptionalInt;
import java.util.stream.IntStream;

import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.javatuples.Triplet;
import org.objectweb.asm.ClassWriter;

import dos.EXL.Parser.Util.Grabber;
import dos.EXL.Parser.Util.TagGrabber;
import dos.EXL.Tokenizer.Types.Token;
import dos.EXL.Tokenizer.Types.TokenType;
import dos.EXL.Types.Expression;
import dos.EXL.Types.Line;
import dos.EXL.Types.Lines.CodeBlock;
import dos.EXL.Types.Lines.DeclarLine;
import dos.EXL.Types.Lines.Field;
import dos.Util.Result;

public class LineParser {
    
    public static Result<Field, Error> getField(List<Token> tokens){
        Result<Field,Error> res = new Result<>();
        var TagsMaybe = TagGrabber.getClassTags(tokens, 0);
        int point = TagsMaybe.getValue().getValue1();
        String type = tokens.get(point).getType().name().toLowerCase();
        if(tokens.get(point + 1).getType() != TokenType.Value){res.setError(new Error("Expected Field Name got " + tokens.get(point + 1)));return res;}
        String name = tokens.get(point + 1).getValue();
        var expression = ExpressionParser.parse(tokens.subList(point + 3, tokens.size()));
        if(expression.hasError()){res.setError(expression.getError());return res;}
        res.setValue(new Field(TagsMaybe.getValue().getValue0(), name,  expression.getValue(), type));
        ClassWriter cw = new ClassWriter(0);

        return res;
    }

    public static Result<Triplet<String, String, Expression>, Error> getDeclare(List<Token> tokens){
        Result<Triplet<String, String, Expression>, Error> res = new Result<>();
        String type = tokens.get(0).getType().name().toLowerCase();
        if(tokens.get(1).getType() != TokenType.Value){res.setError(new Error("Expected Function Name got " + tokens.get(1)));return res;}
        String name = tokens.get(1).getValue();
        var expression = ExpressionParser.parse(tokens.subList(3, tokens.size() - 1));
        if(expression.hasError()){res.setError(expression.getError());return res;}
        res.setValue(new Triplet<String,String,Expression>(type, name, expression.getValue()));
        return res;
    }

    public static Result<Quartet<DeclarLine,Expression,Line,CodeBlock>, Error> getFor(List<Token> tokens){
        Result<Quartet<DeclarLine,Expression,Line,CodeBlock>, Error> res = new Result<>();
        return null;
    }

    public static Result<Pair<Expression,CodeBlock>, Error> getIf(List<Token> tokens){
        Result<Pair<Expression,CodeBlock>, Error> res = new Result<>();
        OptionalInt index = IntStream.range(0, tokens.size())
                     .filter(i -> TokenType.LBrace.equals(tokens.get(i).getType()))
                     .findFirst();
        if(index.isEmpty()){res.setError(new Error("No Lbrace in If line" + tokens));return res;}
        var booleanMaybe = ExpressionParser.parse(tokens.subList(1, index.getAsInt()));
        if(booleanMaybe.hasError()){res.setError(booleanMaybe.getError());return res;} 
        var bodyMaybe = Grabber.grabBracket(tokens, index.getAsInt());
        if(bodyMaybe.hasError()){res.setError(bodyMaybe.getError());return res;}
        var body = CodeBlockParser.getCodeBlock(bodyMaybe.getValue().getValue0());
        if(body.hasError()){res.setError(body.getError());return res;}
        res.setValue(new Pair<Expression,CodeBlock>(booleanMaybe.getValue(), body.getValue()));
        return res;
    }

    public static Result<Pair<String,Expression>, Error> getVarOver(List<Token> tokens) {
        Result<Pair<String,Expression>, Error> res = new Result<>();
        if(tokens.get(0).getType() != TokenType.Value){res.setError(new Error("How tf did this happen "));return res;}
        String name = tokens.get(0).getValue();
        var exprMaybe = ExpressionParser.parse(tokens.subList(2, tokens.size() - 1));
        if(exprMaybe.hasError()){res.setError(exprMaybe.getError());return res;}
        res.setValue(new Pair<String,Expression>(name, exprMaybe.getValue()));
        return res;
    }
    


}

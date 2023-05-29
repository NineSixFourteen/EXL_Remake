package dos.EXL.Parser;

import java.util.List;
import java.util.OptionalInt;
import java.util.stream.IntStream;

import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.javatuples.Triplet;

import dos.EXL.Parser.Factorys.LineFactory;
import dos.EXL.Parser.Util.Grabber;
import dos.EXL.Parser.Util.TagGrabber;
import dos.EXL.Tokenizer.Types.Token;
import dos.EXL.Tokenizer.Types.TokenType;
import dos.EXL.Types.Expression;
import dos.EXL.Types.Line;
import dos.EXL.Types.Errors.ErrorFactory;
import dos.EXL.Types.Lines.CodeBlock;
import dos.EXL.Types.Lines.DeclarLine;
import dos.EXL.Types.Lines.Field;
import dos.Util.Result;
import dos.Util.Results;

//Todo redo file now that getLine() exists xD cringe it wasn't there before

public class LineParser {

    public static Result<Line> getLine(List<Token> tokens){
        switch(tokens.get(0).getType()){
            case Int:case Float:case Long:case Short:case Boolean:case String:case Char:
                var declareMaybe = LineParser.getDeclare(tokens);
                if(declareMaybe.hasError()) 
                    return Results.makeError(declareMaybe.getError());
                Triplet<String, String, Expression> declareParts = declareMaybe.getValue();
                return Results.makeResult(LineFactory.IninitVariable(declareParts.getValue0(), declareParts.getValue1(),declareParts.getValue2()));
            case Print:
                var printMaybe = ExpressionParser.parse(tokens.subList(1, tokens.size() - 1));
                if(printMaybe.hasError()) 
                    return Results.makeError(printMaybe.getError());
                return Results.makeResult(LineFactory.Print(printMaybe.getValue()));
            case Return:
                var returnMaybe = ExpressionParser.parse(tokens.subList(1, tokens.size() - 1));
                if(returnMaybe.hasError())
                    return Results.makeError(returnMaybe.getError());
                return Results.makeResult(LineFactory.returnL(returnMaybe.getValue()));
            case If:
                var ifMaybe = LineParser.getIf(tokens);
                if(ifMaybe.hasError()) 
                    return Results.makeError(ifMaybe.getError());
                Pair<Expression,CodeBlock> ifParts = ifMaybe.getValue();
                return Results.makeResult(LineFactory.ifL(ifParts.getValue0(),ifParts.getValue1()));
            case For:
                var forMaybe = LineParser.getFor(tokens);
                if(forMaybe.hasError()) 
                    return Results.makeError(forMaybe.getError());
                var forParts = forMaybe.getValue();
                return Results.makeResult(LineFactory.forL(forParts.getValue0(),forParts.getValue1(),forParts.getValue2(),forParts.getValue3()));
            case Switch:
                return Results.makeError(ErrorFactory.makeParser("Unknown line start Switch not implemented yet ..LineParser" + tokens.get(0),0));
            case ValueString:
            case Value:
                switch(tokens.get(1).getType()){
                    case Dot:
                    case LBracket:
                        var exprMaybe = ExpressionParser.parse(tokens.subList(0, tokens.size() - 1));
                        if(exprMaybe.hasError())
                            return Results.makeError(exprMaybe.getError());
                        return Results.makeResult(LineFactory.exprL(exprMaybe.getValue()));
                    case Equal:
                        var varOMaybe = LineParser.getVarOver(tokens); 
                        if(varOMaybe.hasError())
                            return Results.makeError(varOMaybe.getError());
                        var varOParts = varOMaybe.getValue();
                        return Results.makeResult(LineFactory.varO(varOParts.getValue0(),varOParts.getValue1()));
                    default:
                        return Results.makeError(ErrorFactory.makeParser("Unknown line" + tokens, 10));
                }
            case New:
                var exprMaybe = ExpressionParser.parse(tokens);
                if(exprMaybe.hasError())  
                    return Results.makeError(exprMaybe.getError());
                return Results.makeResult(LineFactory.exprL(exprMaybe.getValue()));
            default:
                return Results.makeError(ErrorFactory.makeParser("Unknown line start " + tokens.get(0),10));
        }
    }
    
    public static Result<Field> getField(List<Token> tokens){
        var TagsMaybe = TagGrabber.getClassTags(tokens, 0);
        int point = TagsMaybe.getValue().getValue1();
        String type = tokens.get(point).getType().name().toLowerCase();
        if(tokens.get(point + 1).getType() != TokenType.Value) 
            return Results.makeError(ErrorFactory.makeParser("Expected name of field instead found token " + tokens.get(point  +1),2));
        String name = tokens.get(point + 1).getValue();
        var expression = ExpressionParser.parse(tokens.subList(point + 3, tokens.size()));
        if(expression.hasError()) 
            return Results.makeError(expression.getError());
        return Results.makeResult(new Field(TagsMaybe.getValue().getValue0(), name,  expression.getValue(), type));
    }

    public static Result<Triplet<String, String, Expression>> getDeclare(List<Token> tokens){
        String type = tokens.get(0).getType().name().toLowerCase();
        if(tokens.get(1).getType() != TokenType.Value)
            return Results.makeError(ErrorFactory.makeParser("Expected name of new variable instead found token " + tokens.get(1),2));
        String name = tokens.get(1).getValue();
        var expression = ExpressionParser.parse(tokens.subList(3, tokens.size() - 1));
        if(expression.hasError())   
            return Results.makeError(expression.getError());
        return Results.makeResult(new Triplet<String,String,Expression>(type, name, expression.getValue()));
    }

    public static Result<Quartet<DeclarLine,Expression,Line,CodeBlock>> getFor(List<Token> tokens){//TODOOOO
        return null;
    }

    public static Result<Pair<Expression,CodeBlock>> getIf(List<Token> tokens){
        OptionalInt index = IntStream.range(0, tokens.size())
                     .filter(i -> TokenType.LBrace.equals(tokens.get(i).getType()))
                     .findFirst();
        if(index.isEmpty())           
            return Results.makeError(ErrorFactory.makeParser("Expected to find { symbol in if line", 2));
        var booleanMaybe = ExpressionParser.parse(tokens.subList(1, index.getAsInt()));
        if(booleanMaybe.hasError())
            return Results.makeError(booleanMaybe.getError());
        var bodyMaybe = Grabber.grabBracket(tokens, index.getAsInt());
        if(bodyMaybe.hasError())
            return Results.makeError(bodyMaybe.getError());
        var body = CodeBlockParser.getCodeBlock(bodyMaybe.getValue().getValue0());
        if(body.hasError())
            return Results.makeError(body.getError());
        return Results.makeResult(new Pair<Expression,CodeBlock>(booleanMaybe.getValue(), body.getValue()));
    }

    public static Result<Pair<String,Expression>> getVarOver(List<Token> tokens) {
        if(tokens.get(0).getType() != TokenType.Value)
            return Results.makeError(ErrorFactory.makeParser("Weird behaviour ..LineParser",0));
        String name = tokens.get(0).getValue();
        var exprMaybe = ExpressionParser.parse(tokens.subList(2, tokens.size() - 1));
        if(exprMaybe.hasError())
            return Results.makeError(exprMaybe.getError());
        return Results.makeResult(new Pair<String,Expression>(name, exprMaybe.getValue()));

    }
    


}

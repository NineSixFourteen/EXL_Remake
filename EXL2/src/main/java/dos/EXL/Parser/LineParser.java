package dos.EXL.Parser;

import java.util.List;
import dos.EXL.Parser.Lines.DeclareLineParser;
import dos.EXL.Parser.Lines.ForParser;
import dos.EXL.Parser.Lines.IfParser;
import dos.EXL.Parser.Lines.SELParser;
import dos.EXL.Parser.Lines.SwitchParser;
import dos.EXL.Parser.Lines.VarOverParser;
import dos.EXL.Tokenizer.Types.Token;
import dos.EXL.Types.Line;
import dos.EXL.Types.Errors.ErrorFactory;
import dos.Util.Result;
import dos.Util.Results;

public class LineParser {

    public static Result<Line> getLine(List<Token> tokens){
        switch(tokens.get(0).getType()){
            case Int:case Float:case Long:case Short:case Boolean:case String:case Char:
                return DeclareLineParser.getLine(tokens);
            case Print:
                return SELParser.getPrint(tokens);
            case Return:
                return SELParser.getReturn(tokens);
            case If:
                return IfParser.getLine(tokens);
            case For:
                return ForParser.getLine(tokens);
            case Switch:
                return SwitchParser.getLine(tokens);
            case ValueString:
            case Value:
                return valueCase(tokens);
            case New:
                return SELParser.getExprLine(tokens);
            default:
                return Results.makeError(ErrorFactory.makeParser("Unknown line start " + tokens.get(0),10));
        }
    }
    
    private static Result<Line> valueCase(List<Token> tokens){
        switch(tokens.get(1).getType()){
            case Dot:
            case LBracket:
                return SELParser.getExprLine(tokens);
            case Equal:
                return VarOverParser.getLine(tokens);
            default:
                return Results.makeError(ErrorFactory.makeParser("Unknown line" + tokens, 10));
        }
    }
}

package dos.Parser.Builders;

import dos.Types.Expression;
import dos.Types.Line;
import dos.Types.Lines.CodeBlock;
import dos.Types.Lines.DeclarLine;
import dos.Types.Lines.ForLine;
import dos.Types.Lines.IfLine;
import dos.Types.Lines.PrintLine;
import dos.Types.Lines.ReturnLine;
import dos.Types.Lines.WhileLine;

public class LineFactory {

    public static DeclarLine IninitVariable(String name, String type, Expression expr){
        return new DeclarLine(name, type, expr);
    }

    public static PrintLine Print(Expression expr){
        return new PrintLine(expr);
    }

    public static ReturnLine returnL(Expression expr){
        return new ReturnLine(expr);
    }

    public static IfLine ifL(Expression bool, CodeBlock body){
        return new IfLine(bool, body);
    }

    public static ForLine forL(DeclarLine dec, Expression bool, Line l, CodeBlock body){
        return new ForLine(dec, bool, l, body);
    }    

    public static WhileLine whileL(Expression bool, CodeBlock body){
        return new WhileLine(bool, body);
    }
}

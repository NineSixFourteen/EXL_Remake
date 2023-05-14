package dos.Parser.Factorys;

import dos.Types.Expression;
import dos.Types.Line;
import dos.Types.Lines.CodeBlock;
import dos.Types.Lines.DeclarLine;
import dos.Types.Lines.ExpressionLine;
import dos.Types.Lines.ForLine;
import dos.Types.Lines.IfLine;
import dos.Types.Lines.PrintLine;
import dos.Types.Lines.ReturnLine;
import dos.Types.Lines.VarOverwrite;
import dos.Types.Lines.WhileLine;

public class LineFactory {

    public static Line IninitVariable(String name, String type, Expression expr){
        return new DeclarLine(name, type, expr);
    }

    public static Line Print(Expression expr){
        return new PrintLine(expr);
    }

    public static Line returnL(Expression expr){
        return new ReturnLine(expr);
    }

    public static Line ifL(Expression bool, CodeBlock body){
        return new IfLine(bool, body);
    }

    public static Line forL(DeclarLine dec, Expression bool, Line l, CodeBlock body){
        return new ForLine(dec, bool, l, body);
    }    

    public static Line whileL(Expression bool, CodeBlock body){
        return new WhileLine(bool, body);
    }

    public static Line exprL(Expression expr){
        return new ExpressionLine(expr);
    }

    public static Line varO(String name, Expression newExpr){
        return new VarOverwrite(name, newExpr);
    }
}

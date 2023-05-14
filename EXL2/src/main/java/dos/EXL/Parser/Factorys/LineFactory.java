package dos.EXL.Parser.Factorys;

import dos.EXL.Types.Expression;
import dos.EXL.Types.Line;
import dos.EXL.Types.Lines.CodeBlock;
import dos.EXL.Types.Lines.DeclarLine;
import dos.EXL.Types.Lines.ExpressionLine;
import dos.EXL.Types.Lines.ForLine;
import dos.EXL.Types.Lines.IfLine;
import dos.EXL.Types.Lines.PrintLine;
import dos.EXL.Types.Lines.ReturnLine;
import dos.EXL.Types.Lines.VarOverwrite;
import dos.EXL.Types.Lines.WhileLine;

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

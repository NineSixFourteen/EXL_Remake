package dos.EXL.Parser.Factorys;

import java.util.List;

import dos.EXL.Types.Expression;
import dos.EXL.Types.Line;
import dos.EXL.Types.Binary.Boolean.BoolExpr;
import dos.EXL.Types.Lines.CodeBlock;
import dos.EXL.Types.Lines.DeclarLine;
import dos.EXL.Types.Lines.ElseIfLine;
import dos.EXL.Types.Lines.ElseLine;
import dos.EXL.Types.Lines.ExpressionLine;
import dos.EXL.Types.Lines.ForLine;
import dos.EXL.Types.Lines.IfLine;
import dos.EXL.Types.Lines.PrintLine;
import dos.EXL.Types.Lines.ReturnLine;
import dos.EXL.Types.Lines.VarOverwrite;
import dos.EXL.Types.Lines.WhileLine;

public class LineFactory {

    public static Line IninitVariable(String name,String type, Expression expr){
        return new DeclarLine(name, type, expr);
    }

    public static Line Print(Expression expr){
        return new PrintLine(expr);
    }

    public static Line returnL(Expression expr){
        return new ReturnLine(expr);
    }

    public static Line ifL(BoolExpr bool, CodeBlock body, List<Line> elses){
        return new IfLine(bool, body,elses);
    }

    public static Line forL(DeclarLine dec, BoolExpr bool, Line l, CodeBlock body){
        return new ForLine(dec, bool, l, body);
    }    

    public static Line whileL(BoolExpr bool, CodeBlock body){
        return new WhileLine(bool, body);
    }

    public static Line exprL(Expression expr){
        return new ExpressionLine(expr);
    }

    public static Line varO(String name, Expression newExpr){
        return new VarOverwrite(name, newExpr);
    }

    public static Line elseL(CodeBlock value) {
        return new ElseLine(value);
    }

    public static Line elseIfLine(BoolExpr expr, CodeBlock b){
        return new ElseIfLine(expr,b);
    }
}

package dos.EXL.Parser.Factorys.ExpressionFactorys;

import dos.EXL.Types.Expression;
import dos.EXL.Types.Unary.Types.BoolExpr;
import dos.EXL.Types.Unary.Types.CharExpr;
import dos.EXL.Types.Unary.Types.FloatExpr;
import dos.EXL.Types.Unary.Types.IntExpr;
import dos.EXL.Types.Unary.Types.StringExpr;
import dos.EXL.Types.Unary.Types.VarExpr;

public class ValueExpressions {

    public Expression intExpr(int c){
        return new IntExpr(c);
    }

    public Expression floatExpr(float f){
        return new FloatExpr(f);
    }

    public Expression charExpr(char c){
        return new CharExpr(c);
    }

    public Expression boolExpr(boolean b){
        return new BoolExpr(b);
    }

    public Expression stringExpr(String s){
        return new StringExpr(s);
    }

    public Expression varExpr(String name){
        return new VarExpr(name);
    }
    
}

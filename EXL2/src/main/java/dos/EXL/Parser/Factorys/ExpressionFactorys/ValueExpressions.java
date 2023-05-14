package dos.Parser.Factorys.ExpressionFactorys;

import dos.Types.Expression;
import dos.Types.Unary.Types.BoolExpr;
import dos.Types.Unary.Types.CharExpr;
import dos.Types.Unary.Types.FloatExpr;
import dos.Types.Unary.Types.IntExpr;
import dos.Types.Unary.Types.StringExpr;
import dos.Types.Unary.Types.VarExpr;

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

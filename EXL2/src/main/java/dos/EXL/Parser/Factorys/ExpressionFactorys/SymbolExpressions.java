package dos.EXL.Parser.Factorys.ExpressionFactorys;

import java.util.List;

import dos.EXL.Types.ArrayExpr;
import dos.EXL.Compiler.ASM.Util.Primitives;
import dos.EXL.Types.Expression;
import dos.EXL.Types.Unary.BracketExpr;
import dos.EXL.Types.Unary.ObjectDeclareExpr;

public class SymbolExpressions {
    
    public Expression BracExpr(Expression expr){
        return new BracketExpr(expr);
    }

    public Expression ArrayExpr(List<Expression> exprs){
        return new ArrayExpr(exprs);
    }

    public Expression objectExpr(String name, List<Expression> exprs){
        return new ObjectDeclareExpr(name, exprs);
    }


}
